package org.invoice.service.supplier;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.invoice.dto.SupplierDTO;
import org.invoice.dto.SupplierResponseDTO;
import org.invoice.expection.BusinessException;
import org.invoice.model.Supplier;
import org.invoice.model.SupplierStatus;
import org.invoice.repository.InvoiceItemRepository;
import org.invoice.repository.InvoiceRepository;
import org.invoice.repository.SupplierRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SupplierServiceImpl implements SupplierService {

    @Inject
    SupplierRepository supplierRepository;

    @Inject
    InvoiceRepository invoiceRepository;




    @Inject
    ObjectMapper objectMapper;

    @Override
    public List<SupplierResponseDTO> findAll(String filter) {
        System.out.println(filter);
        List<Supplier> suppliers = filter == null || filter.isEmpty() ?
                supplierRepository.listAll() :
                supplierRepository.search(filter);

        return suppliers.stream()
                .map(SupplierResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public SupplierResponseDTO findById(Long id) {
        Supplier supplier = supplierRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException("Fornecedor não encontrado"));
        return new SupplierResponseDTO(supplier);
    }

    @Override
    public List<SupplierResponseDTO> search(String term) {
        return supplierRepository.search(term).stream()
                .map(SupplierResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SupplierResponseDTO create(SupplierDTO supplierDTO) {
        validateCnpj(supplierDTO.cnpj);

        Supplier supplier =  objectMapper.convertValue(supplierDTO,Supplier.class);

        supplierRepository.persist(supplier);
        return new SupplierResponseDTO(supplier);
    }

    @Override
    @Transactional
    public SupplierResponseDTO update(Long id, SupplierDTO supplierDTO) {
        Supplier supplier = supplierRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException("Fornecedor não encontrado"));

        if (!supplier.cnpj.equals(supplierDTO.cnpj)) {
            validateCnpj(supplierDTO.cnpj);
            supplier.cnpj = supplierDTO.cnpj;
        }
        supplier.data_desativacao = supplierDTO.data_desativacao;
        supplier.telefone = supplierDTO.telefone;
        supplier.razao = supplierDTO.razao;
        supplier.email = supplierDTO.email;
        supplier.status = supplierDTO.status;
        supplier.codigo = supplierDTO.codigo;

        return objectMapper.convertValue(supplier,SupplierResponseDTO.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Supplier supplier = supplierRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException("Fornecedor não encontrado"));
        boolean hasInvoices = invoiceRepository.existsBySupplierId(id);
        if (hasInvoices) {
            throw new BusinessException("Não é possível excluir o fornecedor, pois ele está vinculado a uma ou mais faturas.");
        }else{
            supplierRepository.delete(supplier);
        }
    }

    private void validateCnpj(String cnpj) {
        if (supplierRepository.findByCnpj(cnpj).isPresent()) {
            throw new BusinessException("CNPJ já cadastrado");
        }
    }
}