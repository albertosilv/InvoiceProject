package org.invoice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import org.invoice.model.SupplierStatus;
import java.time.LocalDate;

public class SupplierDTO {
    @NotBlank(message = "O código do fornecedor é obrigatório")
    @Size(max = 50, message = "O código do fornecedor não pode ter mais de 50 caracteres")
    public String codigo;

    @NotBlank(message = "A razão social é obrigatória")
    @Size(max = 200, message = "A razão social não pode ter mais de 200 caracteres")
    public String razao;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "O e-mail deve ser válido")
    @Size(max = 100, message = "O e-mail não pode ter mais de 100 caracteres")
    public String email;

    @NotBlank(message = "O telefone é obrigatório")
    @Size(max = 20, message = "O telefone não pode ter mais de 20 caracteres")
    public String telefone;

    @NotBlank(message = "O CNPJ é obrigatório")
    @Size(min = 14, max = 14, message = "O CNPJ deve ter exatamente 14 caracteres")
    public String cnpj;

    @NotNull(message = "O status do fornecedor é obrigatório")
    @Enumerated(EnumType.STRING)
    public SupplierStatus status;

    @PastOrPresent(message = "A data de desativação deve ser no passado ou presente")
    public LocalDate data_desativacao;

}