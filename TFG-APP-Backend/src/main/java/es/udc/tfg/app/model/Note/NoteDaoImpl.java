package es.udc.tfg.app.model.Note;

import es.udc.tfg.app.model.genericDao.GenericDaoImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.Query;

import java.util.Calendar;
import java.util.List;

@Repository
@Transactional
public class NoteDaoImpl extends GenericDaoImpl<Note, Long> implements NoteDao {

    @Override
    public Slice<Note> findByClientIdAndDateRange(Long clientId, Calendar startDate, Calendar endDate, int page, int size) {
        String queryString = "SELECT n FROM Note n WHERE n.invoice.id IS NULL";

        if (clientId != null) {
            queryString += " AND n.client.id = :clientId";
        }
        if (startDate != null) {
            queryString += " AND n.createDate >= :startDate";
        }
        if (endDate != null) {
            queryString += " AND n.createDate <= :endDate";
        }

        Query query = this.em.createQuery(queryString)
                .setFirstResult(page * size)
                .setMaxResults(size + 1);

        if (clientId != null) {
            query.setParameter("clientId", clientId);
        }
        if (startDate != null) {
            query.setParameter("startDate", startDate);
        }
        if (endDate != null) {
            query.setParameter("endDate", endDate);
        }

        List<Note> notes = query.getResultList();
        boolean hasNext = notes.size() == (size + 1);

        if (hasNext) {
            notes.remove(notes.size() - 1);
        }

        return new SliceImpl<>(notes, PageRequest.of(page, size), hasNext);
    }

    @Override
    public List<Note> findByInvoiceId(Long invoiceId) {
        Query query = this.em.createQuery("SELECT n FROM Note n WHERE n.invoice.id = :invoiceId")
                .setParameter("invoiceId", invoiceId);

        return query.getResultList();
    }
}
