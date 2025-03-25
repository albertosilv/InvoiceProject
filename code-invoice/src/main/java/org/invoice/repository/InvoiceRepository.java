package org.invoice.repository;
import org.invoice.model.Invoice;
import org.invoice.model.Product;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class InvoiceRepository implements PanacheRepository<Invoice> {
    public List<Invoice> pesquisar(String termo) {
        return list("numeroNota like ?1 or fornecedor.razaoSocial like ?2",
                "%" + termo + "%", "%" + termo + "%");
    }
}
