package es.udc.tfg.app.rest.dtos;

import java.util.Calendar;
import java.util.List;

public class NoteDto {

    private Long id;
    private Float subtotal;
    private Float taxes;
    private Float total;
    private String comment;
    private String createDate;
    private Long clientId;
    private Long creatorId;
    private Long invoiceId;
    private List<NotelineDto> notelines;

    public NoteDto() {
    }

    public NoteDto(Long id, Float subtotal, Float taxes, Float total, String comment,
                   String createDate, Long clientId, Long creatorId, Long invoiceId, List<NotelineDto> notelines) {
        this.id = id;
        this.subtotal = subtotal;
        this.taxes = taxes;
        this.total = total;
        this.comment = comment;
        this.createDate = createDate;
        this.clientId = clientId;
        this.creatorId = creatorId;
        this.invoiceId = invoiceId;
        this.notelines = notelines;
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

    public String getComment() {
        return comment;
    }

    public String getCreateDate() {
        return createDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public List<NotelineDto> getNotelines() {
        return notelines;
    }
}
