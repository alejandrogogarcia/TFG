package es.udc.tfg.app.service.noteService;

public class NotelineData {

    private Long productId;
    private Integer amount;
    private Integer discount;
    private String comment;

    public NotelineData() {
    }

    public NotelineData(Long productId, Float price, Integer amount, Integer discount, Float taxes, String comment) {
        this.productId = productId;
        this.amount = amount;
        this.discount = discount;
        this.comment = comment;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
