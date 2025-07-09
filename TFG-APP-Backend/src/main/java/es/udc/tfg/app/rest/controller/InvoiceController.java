package es.udc.tfg.app.rest.controller;

import es.udc.tfg.app.model.Invoice.Invoice;
import es.udc.tfg.app.rest.dtos.InvoiceDto;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.service.InvoiceService.InvoiceService;
import es.udc.tfg.app.util.conversors.CalendarConversor;
import es.udc.tfg.app.util.conversors.InvoiceConversor;
import es.udc.tfg.app.util.exceptions.InputValidationException;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/create")
    public ResponseEntity<InvoiceDto> createInvoice(@RequestBody List<Long> notes, @RequestAttribute Long userId) throws InstanceNotFoundException {
        Invoice invoice = invoiceService.createInvoice(notes, userId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(invoice.getId()).toUri();
        return ResponseEntity.created(location).body(InvoiceConversor.toInvoiceDto(invoice));
    }

    @GetMapping("/{id}")
    public InvoiceDto getInvoice(@PathVariable Long id) throws InstanceNotFoundException {
        return InvoiceConversor.toInvoiceDto(invoiceService.findInvoiceById(id));
    }

    @GetMapping("/find")
    public Block<InvoiceDto> findInvoices(@RequestParam(required = false) Long clientId,
                                          @RequestParam(required = false) String startDate,
                                          @RequestParam(required = false) String endDate,
                                          @RequestParam(defaultValue = "0") int page) throws InputValidationException {
        Calendar startDateCalendar = null;
        Calendar endDateCalendar = null;
        if (startDate != null) {
            startDateCalendar = CalendarConversor.stringToCalendar(startDate);
        }
        if (endDate != null) {
            endDateCalendar = CalendarConversor.stringToCalendar(endDate);
        }
        Block<Invoice> invoiceBlock = invoiceService.findInvoices(clientId, startDateCalendar, endDateCalendar, page, 5);
        return new Block<>(InvoiceConversor.toInvoiceDto(invoiceBlock.getItems()), invoiceBlock.getExistMoreItems());
    }
}