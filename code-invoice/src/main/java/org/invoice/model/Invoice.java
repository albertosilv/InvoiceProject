package org.invoice.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "notas_fiscais")
public class Invoice extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank(message = "O número da nota fiscal é obrigatório")
    @Size(max = 50, message = "O número da nota não pode exceder 50 caracteres")
    public String numeroNota;

    @NotNull(message = "A data de emissão é obrigatória")
    @PastOrPresent(message = "A data de emissão não pode ser futura")
    public LocalDateTime dataEmissao;

    @NotNull(message = "O fornecedor é obrigatório")
    @ManyToOne
    @JoinColumn(name = "fornecedor_id", nullable = false)
    public Supplier fornecedor;

    @NotBlank(message = "O endereço é obrigatório")
    @Size(max = 200, message = "O endereço não pode exceder 200 caracteres")
    public String endereco;

    @NotNull(message = "A nota fiscal deve conter pelo menos um item")
    @Size(min = 1, message = "Deve haver pelo menos um item na nota fiscal")
    @OneToMany(mappedBy = "notaFiscal", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<InvoiceItem> itens;

    @Column(name = "valor_total", nullable = false)
    public Double valorTotal;

    public void calcularValorTotal() {
        if (itens == null || itens.isEmpty()) {
            this.valorTotal = 0.0;
        } else {
            this.valorTotal = itens.stream()
                    .mapToDouble(InvoiceItem::getValorTotal)
                    .sum();
        }
    }
}