package org.invoice.service.product;

import jakarta.transaction.Transactional;
import org.invoice.model.Product;
import java.util.List;

public interface ProductService {

    List<Product> list(String filter);

    Product getById(Long id);

    Product create(Product produto);

    Product update(Long id, Product produto);
    void delete(Long id);
}
