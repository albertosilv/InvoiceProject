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

    public boolean hasMovement(Long supplierId) {
        return count("supplier.id = ?1", supplierId) > 0;
    }

    public List<Supplier> search(String term) {
        if (term == null || term.isEmpty()) {
            return listAll();
        }

        Long id = tryParseId(term);
        String cleanTerm = term.replaceAll("[^0-9a-zA-Z]", "");

        return list("id = ?1 " +
                        "or lower(unaccent(corporateName)) like lower(unaccent(?2)) " +
                        "or lower(email) like lower(?3) " +
                        "or replace(phone, ' ', '') like ?4 " +
                        "or cnpj like ?5",
                id,
                "%" + cleanTerm + "%",
                "%" + cleanTerm + "%",
                "%" + cleanTerm + "%",
                "%" + cleanTerm + "%");
    }

    private Long tryParseId(String term) {
        try {
            return Long.parseLong(term);
        } catch (NumberFormatException e) {
            return -1L;
        }
    }
}