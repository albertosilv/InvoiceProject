package org.invoice.service.product;

import org.invoice.dto.ProductDTO;
import org.invoice.dto.ProductResponseDTO;
import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> findAll(String filter);
    ProductResponseDTO findById(Long id);
    ProductResponseDTO create(ProductDTO productDTO);
    ProductResponseDTO update(Long id, ProductDTO productDTO);
    void delete(Long id);
}