package org.invoice.repository;

import org.invoice.model.Supplier;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SupplierRepository implements PanacheRepository<Supplier> {

    public Optional<Supplier> findByCnpj(String cnpj) {
        return find("cnpj", cnpj.replaceAll("[^0-9]", "")).firstResultOptional();
    }

    public List<Supplier> search(String term) {
        if (term == null || term.trim().isEmpty()) {
            return listAll();
        }

        return list("razao like ?1 or cnpj like ?1 or email like ?1 or codigo like ?1 or telefone like ?1", "%" + term + "%");

}
    private Long tryParseId(String term) {
        try {
            return Long.parseLong(term);
        } catch (NumberFormatException e) {
            return -1L;
        }
    }
}