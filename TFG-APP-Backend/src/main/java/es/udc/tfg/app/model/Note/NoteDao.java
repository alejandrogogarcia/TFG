package es.udc.tfg.app.model.Note;

import es.udc.tfg.app.model.genericDao.GenericDao;
import org.springframework.data.domain.Slice;

import java.util.Calendar;
import java.util.List;

public interface NoteDao extends GenericDao<Note, Long> {


    Slice<Note> findByClientIdAndDateRange(Long clientId, Calendar startDate, Calendar endDate, int page, int size);

    List<Note> findByInvoiceId(Long invoiceId);
}
