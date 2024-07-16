package es.udc.tfg.app.model.user;

import es.udc.tfg.app.model.genericDao.GenericDao;
import es.udc.tfg.app.util.enums.UserRole;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;

import java.util.List;

public interface UserDao extends GenericDao<User, Long>{

    public List<User> findByFirstName(String firstName);

    public List<User> findByLastName(String lastName);

    public User findByDni(String dni) throws InstanceNotFoundException;

    public User findByEmail(String email)throws InstanceNotFoundException;

    public List<User> findByUserRole(UserRole role);

    public List<User> findAll();

}
