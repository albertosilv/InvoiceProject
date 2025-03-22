package org.invoice.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
public class Product extends PanacheEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String codigo;
    public String descricao;

    @Enumerated(EnumType.STRING)
    public ProductStatus situacao;
}
