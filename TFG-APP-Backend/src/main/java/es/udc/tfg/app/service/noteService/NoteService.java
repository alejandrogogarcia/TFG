package es.udc.tfg.app.service.noteService;

import es.udc.tfg.app.model.Note.Note;
import es.udc.tfg.app.model.Noteline.Noteline;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import es.udc.tfg.app.util.exceptions.InvalidProductStockException;
import es.udc.tfg.app.util.exceptions.InvoiceAttachedException;

import java.util.Calendar;
import java.util.List;

public interface NoteService {

    Note createNote(NoteData noteData, Long creatorId) throws InstanceNotFoundException;

    void modifyNote(Long noteId, NoteData noteData) throws InstanceNotFoundException, InvoiceAttachedException;

    void removeNote(Long noteId) throws InstanceNotFoundException, InvoiceAttachedException;

    Noteline addNoteline(NotelineData noteData, Long noteId) throws InstanceNotFoundException, InvalidProductStockException, InvoiceAttachedException;

    void modifyNoteline(Long notelineId, NotelineData notelineData) throws InstanceNotFoundException, InvoiceAttachedException, InvalidProductStockException;

    void removeNoteline(Long notelineId) throws InstanceNotFoundException, InvoiceAttachedException;

    Note findNoteById(Long noteId) throws InstanceNotFoundException;

    Noteline findNotelineById(Long notelineId) throws InstanceNotFoundException;

    Block<Note> findNotes(Long clientId, Calendar startDate, Calendar endDate, int page, int size);

}
