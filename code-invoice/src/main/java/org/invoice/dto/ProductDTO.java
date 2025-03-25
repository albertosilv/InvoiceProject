package org.invoice.dto;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.invoice.model.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductDTO {
    @NotBlank(message = "O código do produto é obrigatório")
    @Size(max = 50, message = "O código do produto não pode ter mais de 50 caracteres")
    public String codigo;

    @NotBlank(message = "A descrição do produto é obrigatória")
    @Size(max = 200, message = "A descrição do produto não pode ter mais de 200 caracteres")
    public String descricao;

    @NotNull(message = "A situação do produto é obrigatória")
    @Enumerated(EnumType.STRING)
    public ProductStatus situacao;
}