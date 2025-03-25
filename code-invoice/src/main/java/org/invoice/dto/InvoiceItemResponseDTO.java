package org.invoice.dto;

import org.invoice.model.InvoiceItem;

public class InvoiceItemResponseDTO {

    public Long id;
    public ProductResponseDTO produto;
    public Double valorUnitario;
    public Integer quantidade;
    public Double valorTotal;

    public InvoiceItemResponseDTO(InvoiceItem item) {
        this.id = item.id;
        this.produto = new ProductResponseDTO(item.produto);
        this.valorUnitario = item.valorUnitario;
        this.quantidade = item.quantidade;
        this.valorTotal = item.valorTotal;
    }
}