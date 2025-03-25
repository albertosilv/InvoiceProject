package org.invoice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.invoice.dto.*;
import org.invoice.expection.BusinessException;
import org.invoice.model.*;
import org.invoice.repository.*;
import org.invoice.service.invoice.InvoiceService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class InvoiceServiceImpl implements InvoiceService {

    @Inject
    InvoiceRepository notaFiscalRepository;

    @Inject
    SupplierRepository fornecedorRepository;

    @Inject
    ProductRepository produtoRepository;

    @Inject
    InvoiceItemRepository itemNotaFiscalRepository;

    @Inject
    ObjectMapper objectMapper;

    @Override
    @Transactional
    public InvoiceResponseDTO create(InvoiceDTO notaFiscalDTO) {
        Invoice notaFiscal = objectMapper.convertValue(notaFiscalDTO,Invoice.class);

        notaFiscalRepository.persist(notaFiscal);
        return objectMapper.convertValue(notaFiscal,InvoiceResponseDTO.class);
    }

    @Override
    public InvoiceResponseDTO findById(Long id) {
        Invoice notaFiscal = notaFiscalRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException("Nota fiscal não encontrada com ID: " + id));
        return objectMapper.convertValue(notaFiscal,InvoiceResponseDTO.class);
    }

    @Override
    public List<InvoiceResponseDTO> findAll(String filtro) {
        List<Invoice> notas = (filtro == null || filtro.isEmpty()) ?
                notaFiscalRepository.listAll() :
                notaFiscalRepository.list("numeroNota like ?1 or fornecedor.razaoSocial like ?2",
                        "%" + filtro + "%", "%" + filtro + "%");

        return notas.stream()
                .map(InvoiceResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InvoiceResponseDTO update(Long id, InvoiceDTO notaFiscalDTO) {
        Invoice notaFiscal = notaFiscalRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException("Nota fiscal não encontrada"));

        notaFiscal.dataEmissao = notaFiscalDTO.dataEmissao;

        return  objectMapper.convertValue(notaFiscal,InvoiceResponseDTO.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Invoice notaFiscal = notaFiscalRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException("Nota fiscal não encontrada"));

        notaFiscalRepository.delete(notaFiscal);
    }


    @Override
    @Transactional
    public InvoiceItemResponseDTO createItem(Long notaFiscalId, InvoiceItemDTO itemDTO) {
        Invoice notaFiscal = notaFiscalRepository.findByIdOptional(notaFiscalId)
                .orElseThrow(() -> new BusinessException("Nota fiscal não encontrada"));

        InvoiceItem item = addItem(itemDTO, notaFiscal);
        notaFiscal.itens.add(item);
        notaFiscal.calcularValorTotal();

        return new InvoiceItemResponseDTO(item);
    }

    @Override
    @Transactional
    public InvoiceItemResponseDTO updateItem(Long notaFiscalId, Long itemId, InvoiceItemDTO itemDTO) {
        InvoiceItem item = itemNotaFiscalRepository.findByIdOptional(itemId)
                .orElseThrow(() -> new BusinessException("Item não encontrado"));

        if (!item.notaFiscal.id.equals(notaFiscalId)) {
            throw new BusinessException("Item não pertence à nota fiscal informada");
        }

        updateItem(itemDTO, item);
        item.notaFiscal.calcularValorTotal();

        return new InvoiceItemResponseDTO(item);
    }

    @Override
    @Transactional
    public void removeItem(Long notaFiscalId, Long itemId) {
        InvoiceItem item = itemNotaFiscalRepository.findByIdOptional(itemId)
                .orElseThrow(() -> new BusinessException("Item não encontrado"));

        if (!item.notaFiscal.id.equals(notaFiscalId)) {
            throw new BusinessException("Item não pertence à nota fiscal informada");
        }

        item.notaFiscal.itens.remove(item);
        item.notaFiscal.calcularValorTotal();
        itemNotaFiscalRepository.delete(item);
    }


    private InvoiceItem addItem(InvoiceItemDTO itemDTO, Invoice notaFiscal) {
        Product produto = produtoRepository.findById(itemDTO.produtoId);
        if (produto == null) {
            throw new BusinessException("Produto não encontrado");
        }

        if (produto.situacao != ProductStatus.ACTIVE) {
            throw new BusinessException("Produto deve estar ativo");
        }

        InvoiceItem item = new InvoiceItem();
        item.notaFiscal = notaFiscal;
        item.produto = produto;
        item.valorUnitario = itemDTO.valorUnitario;
        item.quantidade = itemDTO.quantidade;

        item.calcularTotal();

        return item;
    }

    private void updateItem(InvoiceItemDTO itemDTO, InvoiceItem item) {

        item.produto = produtoRepository.findByIdOptional(itemDTO.produtoId)
                .orElseThrow(() -> new BusinessException("Produto não encontrado"));
        item.valorUnitario = itemDTO.valorUnitario;
        item.quantidade = itemDTO.quantidade;
        item.calcularTotal();
    }
}