package org.invoice.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
public class Supplier extends PanacheEntity {

    @NotBlank(message = "O código do fornecedor é obrigatório")
    @Size(max = 50, message = "O código do fornecedor não pode ter mais de 50 caracteres")
    @Column(nullable = false, length = 50, unique = true)
    public String code;

    @NotBlank(message = "A razão social é obrigatória")
    @Size(max = 200, message = "A razão social não pode ter mais de 200 caracteres")
    @Column(nullable = false, length = 200)
    public String companyName;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "O e-mail deve ser válido")
    @Size(max = 100, message = "O e-mail não pode ter mais de 100 caracteres")
    @Column(nullable = false, length = 100)
    public String email;

    @NotBlank(message = "O telefone é obrigatório")
    @Size(max = 20, message = "O telefone não pode ter mais de 20 caracteres")
    @Column(nullable = false, length = 20)
    public String phone;

    @NotBlank(message = "O CNPJ é obrigatório")
    @Size(min = 14, max = 14, message = "O CNPJ deve ter exatamente 14 caracteres")
    @Column(nullable = false, length = 14, unique = true)
    public String cnpj;

    @NotNull(message = "O status do fornecedor é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public SupplierStatus status;

    @PastOrPresent(message = "A data de desativação deve ser no passado ou presente")
    @Column
    public LocalDate deactivationDate;
}