package es.udc.tfg.app.service.userService;

import es.udc.tfg.app.model.user.TokenGenerator;
import es.udc.tfg.app.model.user.User;
import es.udc.tfg.app.model.user.UserDao;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.service.emailService.EmailService;
import es.udc.tfg.app.util.conversors.BooleanConversor;
import es.udc.tfg.app.util.conversors.CalendarConversor;
import es.udc.tfg.app.util.conversors.LanguageConversor;
import es.udc.tfg.app.util.conversors.RoleConversor;
import es.udc.tfg.app.util.enums.Languages;
import es.udc.tfg.app.util.enums.UserRole;
import es.udc.tfg.app.util.exceptions.*;
import es.udc.tfg.app.util.validator.ValidatorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Slice;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.UUID;

import static es.udc.tfg.app.util.conversors.DataConversor.saveBase64ToFile;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Value("${app.user.path.final}")
    private String userMediaPath;

    @Value("${app.user.path.temp}")
    private String userMediaTempPath;

    @Override
    public User registerUser(RegisterData registerData) throws InputValidationException, DuplicateInstanceException, MessagingException, IOException {

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

            String unique = UUID.randomUUID().toString();

            String imageBase64 = registerData.getImage();
            String imageTempPath = userMediaTempPath + unique + ".png";
            if (imageBase64 != null && !imageBase64.isBlank()) {
                saveBase64ToFile(imageBase64, imageTempPath);
            }
            User user = new User(firstName, lastName, registerData.getDni(), null,
                    registerData.getEmail(), birthDate, language, role, null, false);
            emailService.sendPasswordResetEmail(user);
            userDao.save(user);

            Long userId = user.getId();

            if (Files.exists(Path.of(imageTempPath))) {
                String photoFileName = "user-" + userId + "-photo.png";
                Path finalImagePath = Path.of(userMediaPath, photoFileName);
                Files.move(Path.of(imageTempPath), finalImagePath, StandardCopyOption.REPLACE_EXISTING);
                user.setImage(photoFileName);
            }
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
        if (user.getToken() == null || !token.equals(user.getToken()) || user.getExpiryDate() == null || user.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Invalid or expired token");
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
        emailService.sendPasswordResetEmail(user);
        user.setActive(false);
        user.setToken(token);
        user.setExpiryDate(LocalDateTime.now().plusDays(3));
    }

    @Override
    public void updateUser(Long userId, UserData userData, Long authenticatedUserId) throws InstanceNotFoundException, InputValidationException, DuplicateInstanceException, PermissionException, IOException {
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
        if (!userId.equals(authenticatedUserId)){
            UserRole userRole = RoleConversor.stringToRole(userData.getRole());
            boolean isActive = BooleanConversor.stringToBoolean(userData.getIsActive());
            user.setRole(userRole);
            user.setActive(isActive);
        }
        user.setDni(userData.getDni());
        user.setEmail(userData.getEmail());
        user.setFirstName(userData.getFirstName());
        user.setLastName(userData.getLastName());
        user.setBirthDate(birthDate);
        user.setLanguage(language);
        String unique = UUID.randomUUID().toString();
        String imageBase64 = userData.getImage();
        if (imageBase64 != null && !imageBase64.isBlank()) {
            String imageTempPath = userMediaTempPath + unique + ".png";
            saveBase64ToFile(imageBase64, imageTempPath);
            String photoFileName = "user-" + userId + "-photo.png";
            Path finalImagePath = Path.of(userMediaPath, photoFileName);
            Files.move(Path.of(imageTempPath), finalImagePath, StandardCopyOption.REPLACE_EXISTING);
            user.setImage(photoFileName);
        }else {
            user.setImage(null);
        }
    }

    @Override
    public User findUserById(Long userId) throws InstanceNotFoundException {
        return userDao.find(userId);
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