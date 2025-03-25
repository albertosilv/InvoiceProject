package org.invoice.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class InvoiceItem extends PanacheEntity {

    @NotNull(message = "O preço unitário é obrigatório")
    @Positive(message = "O preço unitário deve ser positivo")
    @Column(nullable = false)
    public double unitPrice;

    @NotNull(message = "A quantidade é obrigatória")
    @Positive(message = "A quantidade deve ser positiva")
    @Column(nullable = false)
    public int quantity;

    @NotNull(message = "O produto é obrigatório")
    @ManyToOne(optional = false)
    public Product product;

    @NotNull(message = "A fatura é obrigatória")
    @ManyToOne(optional = false)
    public Invoice invoice;

    public double getTotalAmount() {
        return unitPrice * quantity;
    }
}