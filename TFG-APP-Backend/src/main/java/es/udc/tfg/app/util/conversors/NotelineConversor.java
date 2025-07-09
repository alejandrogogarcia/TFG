package es.udc.tfg.app.util.conversors;
import es.udc.tfg.app.model.Noteline.Noteline;
import es.udc.tfg.app.rest.dtos.NotelineDto;

import java.util.List;
import java.util.stream.Collectors;

public class NotelineConversor {

    public static NotelineDto toNotelineDto(Noteline noteline) {
        return new NotelineDto(
                noteline.getId(),
                noteline.getNote().getId(),
                noteline.getProduct().getId(),
                noteline.getProduct().getReference(),
                noteline.getProduct().getName(),
                noteline.getPrice(),
                noteline.getAmount(),
                noteline.getDiscount(),
                noteline.getTaxes(),
                noteline.getComment()
        );
    }

    public static List<NotelineDto> toNotelineDto(List<Noteline> notelines) {
        return notelines.stream().map(NotelineConversor::toNotelineDto).collect(Collectors.toList());
    }
}