package org.invoice.service.supplier;

import org.invoice.dto.SupplierDTO;
import org.invoice.dto.SupplierResponseDTO;
import org.invoice.expection.BusinessException;
import org.invoice.model.Supplier;
import org.invoice.model.SupplierStatus;
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

    @Override
    public List<SupplierResponseDTO> findAll(String filter) {
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

        Supplier supplier = new Supplier();
        mapDtoToEntity(supplierDTO, supplier);

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
        }

        mapDtoToEntity(supplierDTO, supplier);
        return new SupplierResponseDTO(supplier);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Supplier supplier = supplierRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException("Fornecedor não encontrado"));

        if (supplierRepository.hasMovement(id)) {
            throw new BusinessException("Não é possível excluir fornecedor com movimentação");
        }

        supplierRepository.delete(supplier);
    }

    @Override
    @Transactional
    public void changeStatus(Long id, String status) {
        Supplier supplier = supplierRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException("Fornecedor não encontrado"));

        SupplierStatus newStatus = SupplierStatus.valueOf(status.toUpperCase());
        supplier.status = newStatus;

        if (newStatus == SupplierStatus.TERMINATED) {
            supplier.data_desativacao = LocalDate.now();
        } else {
            supplier.data_desativacao = null;
        }
    }

    private void validateCnpj(String cnpj) {
        if (supplierRepository.findByCnpj(cnpj).isPresent()) {
            throw new BusinessException("CNPJ já cadastrado");
        }
    }

    private void mapDtoToEntity(SupplierDTO dto, Supplier entity) {
        entity.razao = dto.razao;
        entity.cnpj = dto.cnpj.replaceAll("[^0-9]", "");
        entity.email = dto.email;
        entity.telefone = dto.telefone;
        entity.status = dto.status;
        entity.data_desativacao = dto.data_desativacao;
    }
}