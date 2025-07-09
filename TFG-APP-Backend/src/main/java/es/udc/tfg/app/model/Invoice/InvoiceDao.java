package es.udc.tfg.app.model.Invoice;

import es.udc.tfg.app.model.genericDao.GenericDao;
import org.springframework.data.domain.Slice;
import java.util.Calendar;

public interface InvoiceDao extends GenericDao<Invoice, Long> {

    Slice<Invoice> findByClientIdAndDateRange(Long clientId, Calendar startDate, Calendar endDate, int page, int size);
}

