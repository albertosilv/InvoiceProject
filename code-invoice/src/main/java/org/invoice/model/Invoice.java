package org.invoice.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.List;


@Entity
public class Invoice extends PanacheEntity {
    public String number;
    public LocalDateTime issueDate;
    public String address;
    public double totalAmount;

    @ManyToOne
    public Supplier supplier;

    @OneToMany(mappedBy = "invoice")
    public List<InvoiceItem> items;
}
