package es.udc.tfg.app.util.conversors;

import es.udc.tfg.app.model.Note.Note;
import es.udc.tfg.app.model.Noteline.Noteline;
import es.udc.tfg.app.rest.dtos.NoteDto;
import es.udc.tfg.app.rest.dtos.NotelineDto;

import java.util.List;
import java.util.stream.Collectors;

public class NoteConversor {

    public static NoteDto toNoteDto(Note note) {
        return new NoteDto(
                note.getId(),
                note.getSubtotal(),
                note.getTaxes(),
                note.getTotal(),
                note.getComment(),
                CalendarConversor.calendarToString(note.getCreateDate()),
                note.getClient().getId(),
                note.getCreator().getId(),
                note.getInvoice() != null ? note.getInvoice().getId() : null,
                NotelineConversor.toNotelineDto(note.getNotelines())
        );
    }

    public static List<NoteDto> toNoteDto(List<Note> notes) {
        return notes.stream().map(NoteConversor::toNoteDto).collect(Collectors.toList());
    }
}
