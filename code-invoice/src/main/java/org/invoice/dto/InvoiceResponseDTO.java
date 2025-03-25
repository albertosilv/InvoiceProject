package org.invoice.dto;

import org.invoice.model.Invoice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class InvoiceResponseDTO {

    public Long id;
    public String numeroNota;
    public String dataEmissao;
    public SupplierResponseDTO fornecedor;
    public String endereco;
    public Double valorTotal;
    public List<InvoiceItemResponseDTO> itens;

    public InvoiceResponseDTO(Invoice notaFiscal) {
        this.id = notaFiscal.id;
        this.numeroNota = notaFiscal.numeroNota;
        this.dataEmissao = notaFiscal.dataEmissao.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.fornecedor = new SupplierResponseDTO(notaFiscal.fornecedor);
        this.endereco = notaFiscal.endereco;
        this.valorTotal = notaFiscal.valorTotal;
        this.itens = notaFiscal.itens.stream()
                .map(InvoiceItemResponseDTO::new)
                .collect(Collectors.toList());
    }
}
