package org.invoice.dto;

import jakarta.validation.constraints.*;

public class InvoiceItemDTO {

    @NotNull(message = "Produto é obrigatório")
    public Long produtoId;

    @NotNull(message = "Valor unitário é obrigatório")
    @Positive(message = "Valor unitário deve ser positivo")
    public Double valorUnitario;

    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser positiva")
    public Integer quantidade;
}
