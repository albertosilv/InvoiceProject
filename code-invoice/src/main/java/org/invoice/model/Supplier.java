package org.invoice.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Supplier extends PanacheEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public String code;
    public String companyName;
    public String email;
    public String phone;
    public String cnpj;

    @Enumerated(EnumType.STRING)
    public SupplierStatus status;

    public LocalDate deactivationDate;
}
