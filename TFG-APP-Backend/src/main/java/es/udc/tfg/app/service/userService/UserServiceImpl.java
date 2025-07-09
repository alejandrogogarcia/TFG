package es.udc.tfg.app.service.userService;

import es.udc.tfg.app.model.user.TokenGenerator;
import es.udc.tfg.app.model.user.User;
import es.udc.tfg.app.model.user.UserDao;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.util.conversors.BooleanConversor;
import es.udc.tfg.app.util.conversors.CalendarConversor;
import es.udc.tfg.app.util.conversors.LanguageConversor;
import es.udc.tfg.app.util.conversors.RoleConversor;
import es.udc.tfg.app.util.enums.Languages;
import es.udc.tfg.app.util.enums.UserRole;
import es.udc.tfg.app.util.exceptions.*;
import es.udc.tfg.app.util.validator.ValidatorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetEmail(String to, String token) throws MessagingException {
        String resetUrl = "http://localhost:3000/resetPass?token=" + token;

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject("Establece tu contraseña");
        helper.setText("<p>Haz clic en el siguiente enlace para establecer tu contraseña:</p>"
                + "<a href='" + resetUrl + "'>Restablecer contraseña</a>", true);

        mailSender.send(message);
    }

    @Override
    public User registerUser(RegisterData registerData) throws InputValidationException, DuplicateInstanceException, MessagingException {

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
            String firstName = registerData.getFirstName();
            ValidatorProperties.validateString(firstName);
            String lastName = registerData.getLastName();
            ValidatorProperties.validateString(lastName);
            Calendar birthDate = CalendarConversor.stringToCalendar(registerData.getBirthDate());
            ValidatorProperties.validateCalendarPastDate(birthDate);
            Languages language = LanguageConversor.stringToLanguage(registerData.getLanguage());
            UserRole role = RoleConversor.stringToRole(registerData.getRole());
            User user = new User(firstName, lastName, registerData.getDni(), null,
                    registerData.getEmail(), birthDate, language, role, registerData.getImage(), false);
            sendPasswordResetEmail(user.getEmail(), user.getToken());
            userDao.save(user);
            return user;
        }
    }

    @Override
    public User loginUser(LoginData loginData) throws IncorrectLoginException, DisabledUserException {
        User user = null;
        try {
            user = userDao.findByDni(loginData.getDni());
        }catch (InstanceNotFoundException e) {
            throw new IncorrectLoginException(loginData.getDni(), loginData.getPassword());
        }
        if (!user.isActive()){
            throw new DisabledUserException(user.getId());
        }
        if (!passwordEncoder.matches(loginData.getPassword(), user.getEncryptedPassword()))
            throw new IncorrectLoginException(loginData.getDni(), loginData.getPassword());

        return user;
    }

    @Override
    public void changeUserPassword(Long userId, String oldPassword, String newPassword) throws InstanceNotFoundException, IncorrectPasswordException, InputValidationException {
        User user = userDao.find(userId);
        String encryptedPassword = user.getEncryptedPassword();
        if (!passwordEncoder.matches(oldPassword, encryptedPassword)) {
            throw new IncorrectPasswordException();
        }
        ValidatorProperties.validatePassword(newPassword);
        user.setEncryptedPassword(passwordEncoder.encode(newPassword));

    }

    @Override
    public void setUserPassword(String dni, String token, String password) throws InstanceNotFoundException, IllegalArgumentException  {

        User user = userDao.findByDni(dni);
        System.out.println(token);
        if (user.getToken() == null || !token.equals(user.getToken()) || user.getExpiryDate() == null || user.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Token inválido o ha expirado");
        }
        user.setActive(true);
        user.setEncryptedPassword(passwordEncoder.encode(password));
        user.setToken(null);
        user.setExpiryDate(null);
    }

    @Override
    public void resetUserPassword(Long id) throws InstanceNotFoundException, IllegalArgumentException, MessagingException {

        User user = userDao.find(id);
        String token =  TokenGenerator.generateToken();
        sendPasswordResetEmail(user.getEmail(), token);
        user.setActive(false);
        user.setToken(token);
        user.setExpiryDate(LocalDateTime.now().plusDays(3));
    }

    @Override
    public void updateUser(Long userId, UserData userData, Long authenticatedUserId) throws InstanceNotFoundException, InputValidationException, DuplicateInstanceException, PermissionException{
        if (!userId.equals(authenticatedUserId) && !userDao.find(authenticatedUserId).getRole().equals(UserRole.ADMIN)) {
            throw new PermissionException();
        }
        User user = userDao.find(userId);

        ValidatorProperties.validateDni(userData.getDni());

        if (!user.getDni().toLowerCase().equals(userData.getDni().toLowerCase())) {
            try {
                userDao.findByDni(userData.getDni());
                throw new DuplicateInstanceException(userData.getDni(), User.class.getName());
            } catch (InstanceNotFoundException e) {
            }
        }

        ValidatorProperties.validateEmail(userData.getEmail());

        if (!user.getEmail().toLowerCase().equals(userData.getEmail().toLowerCase())) {
            try {
                userDao.findByEmail(userData.getEmail());
                throw new DuplicateInstanceException(userData.getEmail(), User.class.getName());
            } catch (InstanceNotFoundException e) {
            }
        }

        ValidatorProperties.validateString(userData.getFirstName());
        ValidatorProperties.validateString(userData.getLastName());
        Calendar birthDate = CalendarConversor.stringToCalendar(userData.getBirthDate());
        ValidatorProperties.validateCalendarPastDate(birthDate);
        Languages language = LanguageConversor.stringToLanguage(userData.getLanguage());
        UserRole userRole = RoleConversor.stringToRole(userData.getRole());
        boolean isActive = BooleanConversor.stringToBoolean(userData.getIsActive());

        user.setDni(userData.getDni());
        user.setEmail(userData.getEmail());
        user.setFirstName(userData.getFirstName());
        user.setLastName(userData.getLastName());
        user.setBirthDate(birthDate);
        user.setLanguage(language);
        user.setRole(userRole);
        user.setActive(isActive);
        if (userData.getImage()!= null){
            user.setImage(userData.getImage());
        }
    }

    @Override
    public void changeUserState(Long userId) throws InstanceNotFoundException {
        User user = userDao.find(userId);
        user.setActive(!user.isActive());
    }

    @Override
    public void updateUserRole(Long userId, String role) throws InstanceNotFoundException, InputValidationException {
        User user = userDao.find(userId);
        UserRole enumRole = null;

        try {
            enumRole = UserRole.valueOf(role);
        }catch (IllegalArgumentException e){
            throw new InputValidationException(role, "Role should be UserRole type");
        }

        if (enumRole != user.getRole()){
            user.setRole(enumRole);
        }
    }

    @Override
    public User findUserById(Long userId) throws InstanceNotFoundException {
        return userDao.find(userId);
    }

    @Override
    public User findUserByEmail(String email) throws InstanceNotFoundException, InputValidationException {
        ValidatorProperties.validateEmail(email);
        return userDao.findByEmail(email); }

    @Override
    public User findUserByDni(String dni) throws InstanceNotFoundException, InputValidationException {

        ValidatorProperties.validateDni(dni);
        return userDao.findByDni(dni);
    }

    @Override
    public Block<User> findUsersByKeywords(String keywords, String role, int page, int size) throws InputValidationException {

        UserRole roleEnum = null;
        if (role != null) {
            try {
                roleEnum = UserRole.valueOf(role);
            } catch (IllegalArgumentException e) {
                throw new InputValidationException(role, "Role should be UserRole type");
            }
        }
        Slice<User> slice = userDao.findByKeywords(keywords, roleEnum, page, size);

        return new Block<>(slice.getContent(), slice.hasNext());
    }

}
