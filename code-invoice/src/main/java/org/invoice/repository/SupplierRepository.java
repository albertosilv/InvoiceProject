package org.invoice.repository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.invoice.model.Supplier;

@ApplicationScoped
public class SupplierRepository implements PanacheRepository<Supplier> {
}
