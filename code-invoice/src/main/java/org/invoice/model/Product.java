package org.invoice.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Product extends PanacheEntity {

    @NotBlank(message = "O código do produto é obrigatório")
    @Size(max = 50, message = "O código do produto não pode ter mais de 50 caracteres")
    @Column(nullable = false, length = 50, unique = true)
    public String codigo;

    @NotBlank(message = "A descrição do produto é obrigatória")
    @Size(max = 200, message = "A descrição do produto não pode ter mais de 200 caracteres")
    @Column(nullable = false, length = 200)
    public String descricao;

    @NotNull(message = "A situação do produto é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public ProductStatus situacao;
}