package org.invoice.model;

public enum ProductStatus {
    ACTIVE("Ativo"),
    INACTIVE("Inativo");

    private final String description;

    ProductStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    // Método para converter de String para enum
    public static ProductStatus fromString(String text) {
        for (ProductStatus status : ProductStatus.values()) {
            if (status.name().equalsIgnoreCase(text) ||
                    status.description.equalsIgnoreCase(text)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + text);
    }
}