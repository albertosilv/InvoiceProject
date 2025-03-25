package org.invoice.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class ProductPostPutDTO {

    @NotBlank(message = "O código do produto é obrigatório")
    @Size(max = 50, message = "O código do produto não pode ter mais de 50 caracteres")
    private String code;

    @NotBlank(message = "A descrição do produto é obrigatória")
    @Size(max = 200, message = "A descrição do produto não pode ter mais de 200 caracteres")
    private String description;

    @NotNull(message = "O preço do produto é obrigatório")
    @Positive(message = "O preço do produto deve ser positivo")
    private Double price;

    @NotNull(message = "A quantidade em estoque é obrigatória")
    @PositiveOrZero(message = "A quantidade em estoque deve ser positiva ou zero")
    private Integer stockQuantity;
}
