package es.udc.tfg.app.service.userService;

import es.udc.tfg.app.model.user.User;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.util.exceptions.*;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;

public interface UserService {

    public User registerUser(RegisterData registerData)
            throws InputValidationException, DuplicateInstanceException, MessagingException, IOException;

    public User loginUser(LoginData loginData) throws IncorrectLoginException, DisabledUserException;

    public void changeUserPassword(Long userId, String oldPassword, String newPassword)
            throws InstanceNotFoundException, IncorrectPasswordException, InputValidationException;

    public void setUserPassword(String dni, String token, String password) throws InstanceNotFoundException, IllegalArgumentException;

    void resetUserPassword(Long id) throws InstanceNotFoundException, IllegalArgumentException, MessagingException;

    public void updateUser(Long userId, UserData userData, Long authenticatedUserId)
            throws InstanceNotFoundException, InputValidationException, DuplicateInstanceException, PermissionException, IOException;

    public User findUserById(Long userId) throws InstanceNotFoundException;

    public Block<User> findUsersByKeywords(String keywords, String role, int page, int size) throws InputValidationException;

}
