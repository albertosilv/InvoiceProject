package org.invoice.dto;

import org.invoice.model.Product;

public class ProductResponseDTO {
    public Long id;
    public String codigo;
    public String descricao;
    public String situacao;

    public ProductResponseDTO(Product product) {
        this.id = product.id;
        this.codigo = product.codigo;
        this.descricao = product.descricao;
        this.situacao = product.situacao.getDescription();
    }
}