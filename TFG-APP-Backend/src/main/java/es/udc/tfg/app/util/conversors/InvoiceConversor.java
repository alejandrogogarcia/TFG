package es.udc.tfg.app.util.conversors;

import es.udc.tfg.app.model.Invoice.Invoice;
import es.udc.tfg.app.rest.dtos.InvoiceDto;

import java.util.List;
import java.util.stream.Collectors;

public class InvoiceConversor {

    public static InvoiceDto toInvoiceDto(Invoice invoice) {
        return new InvoiceDto(
                invoice.getId(),
                invoice.getSubtotal(),
                invoice.getTaxes(),
                invoice.getTotal(),
                invoice.getCreateDate(),
                invoice.getClient().getId(),
                invoice.getCreator().getId(),
                NoteConversor.toNoteDto(invoice.getNotes())
        );
    }

    public static List<InvoiceDto> toInvoiceDto(List<Invoice> invoices) {
        return invoices.stream().map(InvoiceConversor::toInvoiceDto).collect(Collectors.toList());
    }
}
