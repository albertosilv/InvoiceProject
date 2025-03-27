package org.invoice.dto;

import org.invoice.model.Supplier;
import org.invoice.model.SupplierStatus;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SupplierResponseDTO {
    public Long id;
    public String codigo;
    public String razao;
    public String cnpj;
    public String email;
    public String telefone;
    public String status;
    public String data_desativacao;

    public SupplierResponseDTO() {
    }


    public SupplierResponseDTO(Supplier supplier) {
        this.id = supplier.id;
        this.codigo=supplier.codigo;
        this.razao = supplier.razao;
        this.cnpj = supplier.getFormattedCnpj();
        this.email = supplier.email;
        this.telefone = supplier.telefone;
        this.status = supplier.status.getDescription();
        this.data_desativacao = supplier.data_desativacao != null ?
                supplier.data_desativacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null;
    }
}