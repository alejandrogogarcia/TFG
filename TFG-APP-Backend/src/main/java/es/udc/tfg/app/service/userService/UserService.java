package es.udc.tfg.app.service.userService;

import es.udc.tfg.app.model.user.User;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.util.exceptions.*;
import jakarta.mail.MessagingException;

import java.util.List;

public interface UserService {

    public User registerUser(RegisterData registerData)
            throws InputValidationException, DuplicateInstanceException, MessagingException;

    public User loginUser(LoginData loginData) throws IncorrectLoginException, DisabledUserException;

    public void changeUserPassword(Long userId, String oldPassword, String newPassword)
            throws InstanceNotFoundException, IncorrectPasswordException, InputValidationException;

    public void setUserPassword(String dni, String token, String password) throws InstanceNotFoundException, IllegalArgumentException;

    void resetUserPassword(Long id) throws InstanceNotFoundException, IllegalArgumentException, MessagingException;

    public void updateUser(Long userId, UserData userData, Long authenticatedUserId)
            throws InstanceNotFoundException, InputValidationException, DuplicateInstanceException, PermissionException;

    public void changeUserState(Long userId) throws InstanceNotFoundException;

    public void updateUserRole(Long userId, String role) throws InstanceNotFoundException, InputValidationException;

    public User findUserById(Long userId) throws InstanceNotFoundException;

    public User findUserByEmail(String email) throws InstanceNotFoundException, InputValidationException;

    public User findUserByDni(String dni) throws InstanceNotFoundException, InputValidationException;

    public Block<User> findUsersByKeywords(String keywords, String role, int page, int size) throws InputValidationException;

}
