package es.udc.tfg.app.model.user;

import es.udc.tfg.app.model.genericDao.GenericDao;
import es.udc.tfg.app.util.enums.UserRole;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface UserDao extends GenericDao<User, Long>{

    public User findByDni(String dni) throws InstanceNotFoundException;

    public User findByEmail(String email)throws InstanceNotFoundException;

    public Slice<User> findByKeywords(String keywords, UserRole role, int page, int size);

}
