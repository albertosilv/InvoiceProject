package org.invoice.model;

public enum SupplierStatus {
    ACTIVE("Ativo"),
    TERMINATED("Baixado"),
    SUSPENDED("Suspenso");

    private final String description;

    SupplierStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static SupplierStatus fromString(String text) {
        for (SupplierStatus status : SupplierStatus.values()) {
            if (status.name().equalsIgnoreCase(text) ||
                    status.description.equalsIgnoreCase(text)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + text);
    }
}
