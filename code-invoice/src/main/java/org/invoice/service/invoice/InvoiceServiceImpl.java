package org.invoice.service.invoice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.invoice.dto.*;
import org.invoice.expection.BusinessException;
import org.invoice.model.*;
import org.invoice.repository.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class InvoiceServiceImpl implements InvoiceService {

    @Inject
    InvoiceRepository invoiceRepository;

    @Inject
    SupplierRepository supplierRepository;

    @Inject
    ProductRepository productRepository;

    @Inject
    InvoiceItemRepository invoiceItemRepository;

    @Inject
    ObjectMapper objectMapper;

    @Override
    @Transactional
    public InvoiceResponseDTO create(InvoiceDTO notaFiscalDTO) {
        Invoice invoice = objectMapper.convertValue(notaFiscalDTO, Invoice.class);
        Supplier supplier = supplierRepository.findById(notaFiscalDTO.fornecedor_id);
        if (supplier == null) {
            throw new RuntimeException("Fornecedor não encontrado com ID: " + notaFiscalDTO.fornecedor_id);
        }

        invoice.fornecedor = supplier;
        invoice.calcularValorTotal();
        invoiceRepository.persist(invoice);

        return objectMapper.convertValue(invoice, InvoiceResponseDTO.class);
    }

    @Override
    public InvoiceResponseDTO findById(Long id) {
        Invoice notaFiscal = invoiceRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException("Nota fiscal não encontrada com ID: " + id));
        return objectMapper.convertValue(notaFiscal,InvoiceResponseDTO.class);
    }

    @Override
    public List<InvoiceResponseDTO> findAll(String filtro) {
        List<Invoice> notas = (filtro == null || filtro.isEmpty()) ?
                invoiceRepository.listAll() :
                invoiceRepository.list("numeroNota like ?1 or fornecedor.razaoSocial like ?2",
                        "%" + filtro + "%", "%" + filtro + "%");

        return notas.stream()
                .map(InvoiceResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InvoiceResponseDTO update(Long id, InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException("Nota fiscal não encontrada"));

        invoice.dataEmissao = invoiceDTO.dataEmissao;
        invoice.numeroNota = invoiceDTO.numeroNota;
        invoice.endereco = invoiceDTO.endereco;

        Supplier fornecedor = supplierRepository.findById(invoiceDTO.fornecedor_id);
        if (fornecedor == null) {
            throw new RuntimeException("Fornecedor não encontrado com ID: " + invoiceDTO.fornecedor_id);
        }

        invoice.fornecedor = fornecedor;

        invoice.calcularValorTotal();

        return  objectMapper.convertValue(invoice,InvoiceResponseDTO.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Invoice notaFiscal = invoiceRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException("Nota fiscal não encontrada"));

        invoiceRepository.delete(notaFiscal);
    }


    @Override
    @Transactional
    public InvoiceItemResponseDTO createItem(Long invoiceId, InvoiceItemDTO itemDTO) {
        Invoice invoice = invoiceRepository.findByIdOptional(invoiceId)
                .orElseThrow(() -> new BusinessException("Nota fiscal não encontrada"));

        InvoiceItem item = addItem(itemDTO, invoice);
        item.calcularTotal();
        invoice.itens.add(item);
        invoice.calcularValorTotal();

        return new InvoiceItemResponseDTO(item);
    }

    @Override
    @Transactional
    public InvoiceItemResponseDTO updateItem(Long notaFiscalId, Long itemId, InvoiceItemDTO itemDTO) {
        InvoiceItem item = invoiceItemRepository.findByIdOptional(itemId)
                .orElseThrow(() -> new BusinessException("Item não encontrado"));

        if (!item.notaFiscal.id.equals(notaFiscalId)) {
            throw new BusinessException("Item não pertence à nota fiscal informada");
        }

        itemUpdate(itemDTO, item);
        item.notaFiscal.calcularValorTotal();

        return new InvoiceItemResponseDTO(item);
    }

    @Override
    @Transactional
    public void removeItem(Long invoice, Long itemId) {
        InvoiceItem item = invoiceItemRepository.findByIdOptional(itemId)
                .orElseThrow(() -> new BusinessException("Item não encontrado"));

        if (!item.notaFiscal.id.equals(invoice)) {
            throw new BusinessException("Item não pertence à nota fiscal informada");
        }

        item.notaFiscal.itens.remove(item);
        item.notaFiscal.calcularValorTotal();
        invoiceItemRepository.delete(item);
    }


    private InvoiceItem addItem(InvoiceItemDTO itemDTO, Invoice invoice) {
        Product product = productRepository.findById(itemDTO.produto_id);
        if (product == null) {
            throw new BusinessException("Produto não encontrado");
        }

        if (product.situacao != ProductStatus.ACTIVE) {
            throw new BusinessException("Produto deve estar ativo");
        }

        InvoiceItem item = new InvoiceItem();
        item.notaFiscal = invoice;
        item.produto = product;
        item.valorUnitario = itemDTO.valorUnitario;
        item.quantidade = itemDTO.quantidade;

        item.calcularTotal();

        return item;
    }

    private void itemUpdate(InvoiceItemDTO itemDTO, InvoiceItem item) {

        item.produto = productRepository.findByIdOptional(itemDTO.produto_id)
                .orElseThrow(() -> new BusinessException("Produto não encontrado"));
        item.valorUnitario = itemDTO.valorUnitario;
        item.quantidade = itemDTO.quantidade;
        item.calcularTotal();
    }
}