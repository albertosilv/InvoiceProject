package org.invoice.service.invoice;

import org.invoice.dto.InvoiceDTO;
import org.invoice.dto.InvoiceItemDTO;
import org.invoice.dto.InvoiceItemResponseDTO;
import org.invoice.dto.InvoiceResponseDTO;

import java.util.List;

public interface InvoiceService {
    InvoiceResponseDTO create(InvoiceDTO notaFiscalDTO);
    InvoiceResponseDTO findById(Long id);
    List<InvoiceResponseDTO> findAll(String filtro);
    InvoiceResponseDTO update(Long id, InvoiceDTO notaFiscalDTO);
    void delete(Long id);
    // Métodos para itens
    InvoiceItemResponseDTO createItem(Long notaFiscalId, InvoiceItemDTO itemDTO);
    InvoiceItemResponseDTO updateItem(Long notaFiscalId, Long itemId, InvoiceItemDTO itemDTO);
    void removeItem(Long notaFiscalId, Long itemId);
}
