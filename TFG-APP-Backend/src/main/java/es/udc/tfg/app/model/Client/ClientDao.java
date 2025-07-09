package es.udc.tfg.app.model.Client;

import es.udc.tfg.app.model.genericDao.GenericDao;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import org.springframework.data.domain.Slice;

public interface ClientDao extends GenericDao<Client, Long> {

    public Client findByDni (String dni) throws InstanceNotFoundException;

    public Slice<Client> findByKeywords(String keywords, int page, int size);

}
