package es.udc.tfg.app.rest.dtos;

import java.util.Calendar;
import java.util.List;

public class InvoiceDto {

    private Long id;
    private Float subtotal;
    private Float taxes;
    private Float total;
    private Calendar createDate;
    private Long clientId;
    private Long creatorId;
    private List<NoteDto> notes;

    public InvoiceDto() {
    }

    public InvoiceDto(Long id, Float subtotal, Float taxes, Float total, Calendar createDate, Long clientId, Long creatorId, List<NoteDto> notes) {
        this.id = id;
        this.subtotal = subtotal;
        this.taxes = taxes;
        this.total = total;
        this.createDate = createDate;
        this.clientId = clientId;
        this.creatorId = creatorId;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public Float getSubtotal() {
        return subtotal;
    }

    public Float getTaxes() {
        return taxes;
    }

    public Float getTotal() {
        return total;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public List<NoteDto> getNotes() {
        return notes;
    }
}