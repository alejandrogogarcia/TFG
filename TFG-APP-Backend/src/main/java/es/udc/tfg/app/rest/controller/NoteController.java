package es.udc.tfg.app.rest.controller;

import es.udc.tfg.app.model.Note.Note;
import es.udc.tfg.app.model.Noteline.Noteline;
import es.udc.tfg.app.rest.dtos.NoteDto;
import es.udc.tfg.app.rest.dtos.NotelineDto;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.service.noteService.NoteData;
import es.udc.tfg.app.service.noteService.NoteService;
import es.udc.tfg.app.service.noteService.NotelineData;
import es.udc.tfg.app.util.conversors.CalendarConversor;
import es.udc.tfg.app.util.conversors.NoteConversor;
import es.udc.tfg.app.util.conversors.NotelineConversor;
import es.udc.tfg.app.util.exceptions.InputValidationException;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import es.udc.tfg.app.util.exceptions.InvalidProductStockException;
import es.udc.tfg.app.util.exceptions.InvoiceAttachedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Calendar;

@RestController
@RequestMapping("/")
public class NoteController {

    @Autowired
    private NoteService noteService;


    @PostMapping("/note/create")
    public ResponseEntity<NoteDto> createNote(@RequestBody NoteData noteData, @RequestAttribute Long userId) throws InstanceNotFoundException {
        Note note = noteService.createNote(noteData, userId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(note.getId()).toUri();
        return ResponseEntity.created(location).body(NoteConversor.toNoteDto(note));
    }

    @PutMapping("/note/{id}/update")
    public void updateNote(@PathVariable Long id, @RequestBody NoteData noteData) throws InstanceNotFoundException, InvoiceAttachedException {
        noteService.modifyNote(id, noteData);
    }

    @DeleteMapping("/note/{id}/remove")
    public void removeNote(@PathVariable Long id) throws InstanceNotFoundException, InvoiceAttachedException {
        noteService.removeNote(id);
    }

    @PostMapping("/note/{id}/noteline/add")
    public ResponseEntity<NotelineDto> addNoteline(@PathVariable Long id, @RequestBody NotelineData notelineData) throws InstanceNotFoundException, InvalidProductStockException, InvoiceAttachedException {
        Noteline noteline = noteService.addNoteline(notelineData, id);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").build().toUri();
        return ResponseEntity.created(location).body(NotelineConversor.toNotelineDto(noteline));
    }

    @PutMapping("/noteline/{id}/update")
    public void updateNoteline(@PathVariable Long id, @RequestBody NotelineData notelineData) throws InstanceNotFoundException, InvoiceAttachedException, InvalidProductStockException {
        noteService.modifyNoteline(id, notelineData);
    }

    @DeleteMapping("/noteline/{id}/remove")
    public void removeNoteline(@PathVariable Long id) throws InstanceNotFoundException, InvoiceAttachedException {
        noteService.removeNoteline(id);
    }

    @GetMapping("/note/{id}")
    public NoteDto getNote(@PathVariable Long id) throws InstanceNotFoundException {
        return NoteConversor.toNoteDto(noteService.findNoteById(id));
    }

    @GetMapping("/noteline/{id}")
    public NotelineDto getNoteline(@PathVariable Long id) throws InstanceNotFoundException {
        return NotelineConversor.toNotelineDto(noteService.findNotelineById(id));
    }

    @GetMapping("/note/find")
    public Block<NoteDto> findNotes(@RequestParam(required = false) Long clientId,
                                    @RequestParam(required = false) String startDate,
                                    @RequestParam(required = false) String endDate,
                                    @RequestParam(defaultValue = "0") int page) throws InputValidationException {
        Calendar startDateCalendar = null;
        Calendar endDateCalendar = null;
        if (startDate != null) {
            startDateCalendar = CalendarConversor.stringToCalendar(startDate);
        }
        if (startDate != null) {
            endDateCalendar = CalendarConversor.stringToCalendar(endDate);
        }

        Block<Note> notesBlock = noteService.findNotes(clientId, startDateCalendar , endDateCalendar, page, 5);
        return new Block<>(NoteConversor.toNoteDto(notesBlock.getItems()), notesBlock.getExistMoreItems());
    }

}

