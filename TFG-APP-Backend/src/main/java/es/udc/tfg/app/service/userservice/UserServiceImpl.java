package es.udc.tfg.app.service.userservice;

import es.udc.tfg.app.model.user.User;
import es.udc.tfg.app.model.user.UserDao;
import es.udc.tfg.app.util.conversors.CalendarConversor;
import es.udc.tfg.app.util.conversors.LanguageConversor;
import es.udc.tfg.app.util.conversors.RoleConversor;
import es.udc.tfg.app.util.encrypt.PasswordEncrypter;
import es.udc.tfg.app.util.enums.Languages;
import es.udc.tfg.app.util.enums.UserRole;
import es.udc.tfg.app.util.exceptions.*;
import es.udc.tfg.app.util.validator.ValidatorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Transactional
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    public User registerUser(RegisterData registerData) throws InputValidationException, DuplicateInstanceException {

        ValidatorProperties.validateDni(registerData.getDni());
        ValidatorProperties.validateEmail(registerData.getEmail());

        try {
            try {
                userDao.findByDni(registerData.getDni());
                throw new DuplicateInstanceException(registerData.getDni(), User.class.getName());
            } catch (InstanceNotFoundException e) {
                userDao.findByEmail(registerData.getEmail());
                throw new DuplicateInstanceException(registerData.getEmail(), User.class.getName());
            }
        }catch (InstanceNotFoundException e) {

            ValidatorProperties.validatePassword(registerData.getPassword());
            String encryptedPassword = PasswordEncrypter.crypt(registerData.getPassword());
            String firstName = registerData.getFirstname();
            ValidatorProperties.validateString(firstName);
            String lastName = registerData.getLastName();
            ValidatorProperties.validateString(lastName);
            Calendar birthDate = CalendarConversor.stringToCalendar(registerData.getBirthDate());
            ValidatorProperties.validateCalendarPastDate(birthDate);
            Languages language = LanguageConversor.stringToLanguage(registerData.getLanguage());
            UserRole role = RoleConversor.stringToRole(registerData.getRole());
            User user = new User(firstName, lastName, registerData.getDni(), encryptedPassword,
                    registerData.getEmail(), birthDate, language, role, registerData.getImage(), false);
            userDao.save(user);
            return user;
        }
    }

    @Override
    public User loginUser(LoginData loginData) throws InstanceNotFoundException, IncorrectPasswordException {
        User user = userDao.findByDni(loginData.getDni());
        String encryptedPassword = user.getEncryptedPassword();
        if (!PasswordEncrypter.isCorrectPassword(loginData.getPassword(), encryptedPassword)) {
            throw new IncorrectPasswordException(user.getDni());
        }
        return user;
    }

    @Override
    public void changeUserPassword(Long userId, String oldPassword, String newPassword) throws InstanceNotFoundException, IncorrectPasswordException, InputValidationException {
        User user = userDao.find(userId);
        String encryptedPassword = user.getEncryptedPassword();
        if (!PasswordEncrypter.isCorrectPassword(encryptedPassword, oldPassword)) {
            throw new IncorrectPasswordException(user.getEmail());
        }
        ValidatorProperties.validatePassword(newPassword);
        user.setEncryptedPassword(PasswordEncrypter.crypt(newPassword));

    }

    @Override
    public void updateUser(Long userId, UserData userData) throws InstanceNotFoundException, InputValidationException, DuplicateInstanceException {

    }

    @Override
    public void changeUserState(Long userId) throws InstanceNotFoundException {
        User user = userDao.find(userId);
        user.setActive(!user.isActive());
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
