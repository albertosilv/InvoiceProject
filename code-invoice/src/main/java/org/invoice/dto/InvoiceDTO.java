package org.invoice.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;
import org.invoice.model.InvoiceItem;

import java.time.LocalDateTime;
import java.util.List;


public class InvoiceDTO {

    @NotBlank(message = "O número da nota fiscal é obrigatório")
    @Size(max = 50, message = "O número da nota não pode exceder 50 caracteres")
    public String numeroNota;

    @NotNull(message = "A data de emissão é obrigatória")
    @PastOrPresent(message = "A data de emissão não pode ser futura")
    public LocalDateTime dataEmissao;

    @NotNull(message = "O fornecedor é obrigatório")
    @Positive(message = "O ID do fornecedor deve ser positivo")
    public Long fornecedorId;

    @NotBlank(message = "O endereço é obrigatório")
    @Size(max = 200, message = "O endereço não pode exceder 200 caracteres")
    public String endereco;

    @NotNull(message = "A nota fiscal deve conter pelo menos um item")
    @Size(min = 1, message = "Deve haver pelo menos um item na nota fiscal")
    @OneToMany(mappedBy = "notaFiscal", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<InvoiceItem> itens;
}
