package org.invoice.service;

import org.invoice.model.Product;
import org.invoice.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;

    public List<Product> listAll() {
        return productRepository.listAll();
    }

    @Transactional
    public Product create(Product product) {
        productRepository.persist(product);
        return product;
    }

    @Transactional
    public Product update(Long id, Product product) {
        Product existingProduct = productRepository.findById(id);
        if (existingProduct != null) {
            existingProduct.setCode(product.getCode());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setStatus(product.getStatus());
        }
        return existingProduct;
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}