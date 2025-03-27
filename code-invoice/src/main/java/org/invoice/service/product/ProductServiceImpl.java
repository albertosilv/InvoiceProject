package org.invoice.service.product;

import org.invoice.dto.ProductDTO;
import org.invoice.dto.ProductResponseDTO;
import org.invoice.expection.BusinessException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.invoice.model.Product;
import org.invoice.repository.InvoiceItemRepository;
import org.invoice.repository.ProductRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProductServiceImpl implements ProductService {

    @Inject
    ProductRepository productRepository;

    @Inject
    InvoiceItemRepository itemInvoiceRepository;


    @Override
    public List<ProductResponseDTO> findAll(String filter) {
        List<Product> products = productRepository.search(filter);
        return products.stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException("Produto não encontrado"));
        return new ProductResponseDTO(product);
    }

    @Override
    @Transactional
    public ProductResponseDTO create(ProductDTO productDTO) {
        Product product = new Product();
        product.descricao = productDTO.descricao;
        product.situacao = productDTO.situacao;
        product.codigo=productDTO.codigo;

        productRepository.persist(product);
        return new ProductResponseDTO(product);
    }

    @Override
    @Transactional
    public ProductResponseDTO update(Long id, ProductDTO productDTO) {
        Product product = productRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException("Produto não encontrado"));

        product.descricao = productDTO.descricao;
        product.situacao = productDTO.situacao;

        return new ProductResponseDTO(product);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findByIdOptional(id)
                .orElseThrow(() -> new BusinessException("Produto não encontrado"));

        if (itemInvoiceRepository.existsByProductId(id)) {
            throw new BusinessException("Não é possível excluir o produto, pois ele está vinculado a uma ou mais notas fiscais.");
        }

        productRepository.delete(product);
    }

}