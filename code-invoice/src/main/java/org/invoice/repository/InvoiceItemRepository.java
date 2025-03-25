package org.invoice.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.invoice.model.Invoice;
import org.invoice.model.InvoiceItem;

@ApplicationScoped
public class InvoiceItemRepository implements PanacheRepository<InvoiceItem> {
}
