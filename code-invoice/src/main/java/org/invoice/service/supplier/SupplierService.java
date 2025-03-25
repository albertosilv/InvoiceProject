package org.invoice.service.supplier;

import jakarta.transaction.Transactional;
import org.invoice.dto.SupplierDTO;
import org.invoice.dto.SupplierResponseDTO;

import java.util.List;

public interface SupplierService {
    List<SupplierResponseDTO> findAll(String filter);
    SupplierResponseDTO findById(Long id);

    List<SupplierResponseDTO> search(String term);

    SupplierResponseDTO create(SupplierDTO supplierDTO);
    SupplierResponseDTO update(Long id, SupplierDTO supplierDTO);
    void delete(Long id);
}
