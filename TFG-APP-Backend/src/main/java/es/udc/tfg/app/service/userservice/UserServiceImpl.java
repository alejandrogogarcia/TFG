package es.udc.tfg.app.service.userservice;

import es.udc.tfg.app.model.user.User;
import es.udc.tfg.app.util.exceptions.*;

import java.util.List;

public class UserServiceImpl implements UserService{
    @Override
    public User registerUser(RegisterData registerData) throws InputValidationException, DuplicateInstanceException {
        return null;
    }

    @Override
    public User loginUser(LoginData loginData) throws InstanceNotFoundException, IncorrectPasswordException {
        return null;
    }

    @Override
    public void changeUserPassword(Long userId, String oldPassword, String newPassword) throws InstanceNotFoundException, IncorrectPasswordException, InputValidationException {

    }

    @Override
    public void updateUser(Long userId, UserData userData) throws InstanceNotFoundException, InputValidationException, DuplicateInstanceException {

    }

    @Override
    public void updateUserState(Long userId, boolean state) throws InstanceNotFoundException {

    }

    @Override
    public void updateUserRole(Long userId, String role) throws InstanceNotFoundException, InputValidationException {

    }

    @Override
    public User findUserById(Long userId) throws InstanceNotFoundException {
        return null;
    }

    @Override
    public User findUserByEmail(String email) throws InstanceNotFoundException, InputValidationException {
        return null;
    }

    @Override
    public User findUserByDni(String dni) throws InstanceNotFoundException, InputValidationException {
        return null;
    }

    @Override
    public List<User> findUsersByFirstName(String firstName) {
        return List.of();
    }

    @Override
    public List<User> findUsersByLastName(String lastName) {
        return List.of();
    }

    @Override
    public List<User> findUsersByUserRole(String role) throws InputValidationException {
        return List.of();
    }

    @Override
    public List<User> findAllUsers() {
        return List.of();
    }
}
