package es.udc.tfg.app.model.Client;


import es.udc.tfg.app.model.genericDao.GenericDaoImpl;
import es.udc.tfg.app.model.user.User;
import es.udc.tfg.app.util.Slice.Tokens;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import jakarta.persistence.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ClientDaoImpl extends GenericDaoImpl<Client, Long> implements ClientDao {

    @Override
    public Client findByDni(String dni) throws InstanceNotFoundException {

        Client client;

        try {
            String normalizedDni = dni.trim().toLowerCase();
            Query query = em.createQuery(
                    "SELECT c FROM Client c WHERE LOWER(TRIM(c.dni)) = :dni"
            ).setParameter("dni", normalizedDni);
            client = (Client) query.getSingleResult();
        } catch (Exception e) {
            throw new InstanceNotFoundException(dni, Client.class.getName());
        }
        return client;
    }

    @Override
    public Slice<Client> findByKeywords(String keywords, int page, int size) {

        String[] tokens = Tokens.getTokens(keywords);
        StringBuilder queryString = new StringBuilder("SELECT c FROM Client c ");
        if (tokens.length > 0) {
            queryString.append("WHERE ");
            for (int i = 0; i < tokens.length; i++) {
                if (i != 0) {
                    queryString.append(" AND ");
                }
                queryString.append("(")
                        .append("LOWER(c.firstName) LIKE :token").append(i)
                        .append(" OR LOWER(c.lastName) LIKE :token").append(i)
                        .append(" OR LOWER(c.dni) LIKE :token").append(i)
                        .append(" OR LOWER(c.city) LIKE :token").append(i)
                        .append(")");
            }
        }
        Query query = em.createQuery(queryString.toString())
                .setFirstResult(page * size)
                .setMaxResults(size + 1);
        for (int i = 0; i < tokens.length; i++) {
            query.setParameter("token" + i, "%" + tokens[i].toLowerCase() + "%");
        }
        List<Client> clients = query.getResultList();
        boolean hasNext = clients.size() == (size + 1);
        if (hasNext) {
            clients.remove(clients.size() - 1);
        }
        return new SliceImpl<>(clients, PageRequest.of(page, size), hasNext);
    }
}
