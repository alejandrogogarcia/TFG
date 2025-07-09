package es.udc.tfg.app.service.noteService;

import java.util.Calendar;

public class NoteData {

    private String comment;
    private Long clientId;

    public NoteData() {
    }

    public NoteData(String comment, Long clientId) {
        this.comment = comment;
        this.clientId = clientId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
