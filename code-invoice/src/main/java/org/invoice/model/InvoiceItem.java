package org.invoice.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;


@Entity
public class InvoiceItem extends PanacheEntity {
    public double unitPrice;
    public int quantity;

    @ManyToOne
    public Product product;

    @ManyToOne
    public Invoice invoice;

    public double getTotalAmount() {
        return unitPrice * quantity;
    }
}