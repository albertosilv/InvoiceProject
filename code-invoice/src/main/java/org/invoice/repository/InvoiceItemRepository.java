package org.invoice.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.invoice.model.InvoiceItem;

import java.util.List;

@ApplicationScoped
public class InvoiceItemRepository implements PanacheRepository<InvoiceItem> {
    public List<InvoiceItem> listarPorProduto(Long produtoId) {
        return list("produto.id", produtoId);
    }

}
