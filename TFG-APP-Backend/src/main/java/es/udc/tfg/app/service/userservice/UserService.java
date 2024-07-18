package es.udc.tfg.app.service.userservice;

import es.udc.tfg.app.model.user.User;
import es.udc.tfg.app.util.exceptions.*;

import java.util.List;

public interface UserService {

    public User registerUser(RegisterData registerData)
            throws InputValidationException, DuplicateInstanceException;

    public User loginUser(LoginData loginData) throws InstanceNotFoundException, IncorrectPasswordException;

    public void changeUserPassword(Long userId, String oldPassword, String newPassword)
            throws InstanceNotFoundException, IncorrectPasswordException, InputValidationException;

    public void updateUser(Long userId, UserData userData)
            throws InstanceNotFoundException, InputValidationException, DuplicateInstanceException;

    public void updateUserState(Long userId, boolean state) throws InstanceNotFoundException;

    public void updateUserRole(Long userId, String role) throws InstanceNotFoundException, InputValidationException;

    public User findUserById(Long userId) throws InstanceNotFoundException;

    public User findUserByEmail(String email) throws InstanceNotFoundException, InputValidationException;

    public User findUserByDni(String dni) throws InstanceNotFoundException, InputValidationException;

    public List<User> findUsersByFirstName(String firstName);

    public List<User> findUsersByLastName(String lastName);

    public List<User> findUsersByUserRole(String role) throws InputValidationException;

    public List<User> findAllUsers();
}
