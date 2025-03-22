package org.invoice.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
public class Product extends PanacheEntity {
    public String codigo;
    public String descricao;

    @Enumerated(EnumType.STRING)
    public ProductStatus situacao;
}
