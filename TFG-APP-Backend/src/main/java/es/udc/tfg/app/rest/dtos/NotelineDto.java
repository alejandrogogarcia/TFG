package es.udc.tfg.app.rest.dtos;

public class NotelineDto {

    private Long notelineId;
    private Long noteId;
    private Long productId;
    private String productRef;
    private String productName;
    private Float price;
    private Integer amount;
    private Integer discount;
    private Float taxes;
    private String comment;

    public NotelineDto() {
    }

    public NotelineDto(Long notelineId, Long noteId, Long productId, String productRef, String productName, Float price, Integer amount,
                       Integer discount, Float taxes, String comment) {
        this.notelineId = notelineId;
        this.noteId = noteId;
        this.productId = productId;
        this.productRef = productRef;
        this.productName = productName;
        this.price = price;
        this.amount = amount;
        this.discount = discount;
        this.taxes = taxes;
        this.comment = comment;
    }

    public Long getNotelineId() {
        return notelineId;
    }

    public Long getNoteId() {
        return noteId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductRef() {
        return productRef;
    }

    public void setProductRef(String productRef) {
        this.productRef = productRef;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getDiscount() {
        return discount;
    }

    public Float getTaxes() {
        return taxes;
    }

    public String getComment() {
        return comment;
    }
}