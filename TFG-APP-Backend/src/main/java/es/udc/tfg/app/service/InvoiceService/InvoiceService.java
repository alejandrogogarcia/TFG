package es.udc.tfg.app.service.InvoiceService;

import es.udc.tfg.app.model.Invoice.Invoice;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;

import java.util.Calendar;
import java.util.List;

public interface InvoiceService {

    Invoice createInvoice(List<Long> notes, Long creatorId) throws InstanceNotFoundException;

    Invoice findInvoiceById(Long invoiceId) throws InstanceNotFoundException;

    Block<Invoice> findInvoices(Long clientId, Calendar startDate, Calendar endDate, int page, int size);
}
