package es.udc.tfg.app.model.Invoice;

import es.udc.tfg.app.model.genericDao.GenericDaoImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.Calendar;
import java.util.List;

@Repository
@Transactional
public class InvoiceDaoImpl extends GenericDaoImpl<Invoice, Long> implements InvoiceDao {

    @Override
    public Slice<Invoice> findByClientIdAndDateRange(Long clientId, Calendar startDate, Calendar endDate, int page, int size) {
        String queryString = "SELECT i FROM Invoice i";
        boolean hasCondition = false;

        if (clientId != null || startDate != null || endDate != null) {
            queryString += " WHERE";

            if (clientId != null) {
                queryString += " i.client.id = :clientId";
                hasCondition = true;
            }

            if (startDate != null) {
                queryString += (hasCondition ? " AND" : "") + " i.createDate >= :startDate";
                hasCondition = true;
            }

            if (endDate != null) {
                queryString += (hasCondition ? " AND" : "") + " i.createDate <= :endDate";
            }
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

        List<Invoice> invoices = query.getResultList();
        boolean hasNext = invoices.size() == (size + 1);

        if (hasNext) {
            invoices.remove(invoices.size() - 1);
        }

        return new SliceImpl<>(invoices, PageRequest.of(page, size), hasNext);
    }
}
