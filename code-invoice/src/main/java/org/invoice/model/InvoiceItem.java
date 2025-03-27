package org.invoice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.invoice.model.Product;

@Entity
@Table(name = "itens_nota_fiscal")
public class InvoiceItem extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "nota_fiscal_id", nullable = false)
    @JsonIgnore
    public Invoice notaFiscal;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    public Product produto;

    @Column(name = "valor_unitario", nullable = false)
    public Double valorUnitario;

    @Column(nullable = false)
    public Integer quantidade;

    @Column(name = "valor_total", nullable = false)
    public Double valorTotal;

    public Double getValorTotal() {
        return valorUnitario * quantidade;
    }

    @PrePersist
    @PreUpdate
    public void calcularTotal() {
        this.valorTotal = getValorTotal();
    }
}