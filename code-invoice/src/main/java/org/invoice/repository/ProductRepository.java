package org.invoice.repository;
import org.invoice.model.Product;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {
    public boolean hasMovement(Long productId) {
        return count("product.id = ?1", productId) > 0;
    }
}
