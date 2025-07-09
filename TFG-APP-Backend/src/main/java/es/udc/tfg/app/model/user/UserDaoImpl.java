package es.udc.tfg.app.model.user;

import es.udc.tfg.app.model.genericDao.GenericDaoImpl;
import es.udc.tfg.app.util.enums.UserRole;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.Query;


import java.util.List;

@Repository
@Transactional
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

    private String[] getTokens(String keywords) {

        if (keywords == null || keywords.length() == 0) {
            return new String[0];
        } else {
            return keywords.split("\\s");
        }
    }

    @Override
    public User findByDni(String dni) throws InstanceNotFoundException {

        User user = null;
        try {
            Query query = this.em.createQuery("SELECT u FROM User u WHERE u.dni = :dni").setParameter("dni", dni);
            user = (User) query.getSingleResult();
        } catch (Exception e) {
            throw new InstanceNotFoundException(dni, User.class.getName());
        }
        return user;
    }

    @Override
    public User findByEmail(String email) throws InstanceNotFoundException {

        User user = null;
        try {
            Query query = this.em.createQuery("SELECT u FROM User u WHERE u.email = :email").setParameter("email",
                    email);
            user = (User) query.getSingleResult();
        } catch (Exception e) {
            throw new InstanceNotFoundException(email, User.class.getName());
        }
        return user;
    }

    @Override
    public Slice<User> findByKeywords(String keywords, UserRole role, int page, int size) {

        String[] tokens = getTokens(keywords);
        String queryString = "SELECT u FROM User u ";

        if (tokens.length > 0 || role != null) {

            queryString += "WHERE ";

            if (role != null) {
                System.out.println(role.toString());
                queryString += "u.role = :role";
            }

            if (tokens.length > 0) {
                for (int i = 0; i < tokens.length; i++) {
                    if (role != null || i!= 0) {
                        queryString += " AND ";
                    }
                    queryString += "(LOWER(u.firstName) LIKE :token" + i +
                            " OR LOWER(u.lastName) LIKE :token" + i +
                            " OR LOWER(u.dni) LIKE :token" + i +")";
                }
            }
        }
        Query query = this.em.createQuery(queryString)
                .setFirstResult(page*size)
                .setMaxResults(size+1);

        if (role != null) {
            query.setParameter("role", role);
        }

        if (tokens.length != 0) {
            for (int i = 0; i<tokens.length; i++) {
                query.setParameter("token" + i, '%' + tokens[i] + '%');
            }
        }

        List<User> users = query.getResultList();
        boolean hasNext = users.size() == (size+1);

        if (hasNext) {
            users.remove(users.size()-1);
        }
        return new SliceImpl<>(users, PageRequest.of(page, size), hasNext);

    }
}
