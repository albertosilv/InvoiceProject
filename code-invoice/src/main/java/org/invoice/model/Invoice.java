package org.invoice.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Invoice extends PanacheEntity {

    @NotBlank(message = "O número da fatura é obrigatório")
    @Size(max = 50, message = "O número da fatura não pode ter mais de 50 caracteres")
    @Column(nullable = false, length = 50)
    public String number;

    @NotNull(message = "A data de emissão é obrigatória")
    @PastOrPresent(message = "A data de emissão deve ser no passado ou presente")
    @Column(nullable = false)
    public LocalDateTime issueDate;

    @NotBlank(message = "O endereço é obrigatório")
    @Size(max = 200, message = "O endereço não pode ter mais de 200 caracteres")
    @Column(nullable = false, length = 200)
    public String address;

    @NotNull(message = "O valor total é obrigatório")
    @PositiveOrZero(message = "O valor total deve ser positivo ou zero")
    @Column(nullable = false)
    public double totalAmount;

    @NotNull(message = "O fornecedor é obrigatório")
    @ManyToOne(optional = false)
    public Supplier supplier;

    @NotEmpty(message = "A fatura deve ter pelo menos um item")
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<InvoiceItem> items;
}