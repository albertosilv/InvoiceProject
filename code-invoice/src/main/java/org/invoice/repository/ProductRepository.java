package org.invoice.repository;

import org.invoice.model.Product;
import org.invoice.model.ProductStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

    public boolean hasMovement(Long productId) {
        return count("product.id = ?1", productId) > 0;
    }

    public List<Product> search(String term) {
        if (term == null || term.isEmpty()) {
            return listAll();
        }

        return list("description like ?1", "%" + term + "%");
    }

    public List<Product> findActiveProducts() {
        return list("status = ?1", ProductStatus.ACTIVE);
    }
}