package es.udc.tfg.app.userService;

import es.udc.tfg.app.model.user.User;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.service.userService.LoginData;
import es.udc.tfg.app.service.userService.RegisterData;
import es.udc.tfg.app.service.userService.UserData;
import es.udc.tfg.app.service.userService.UserService;

import es.udc.tfg.app.util.exceptions.*;
import jakarta.mail.MessagingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final String VALID_FIRST_NAME = "Pepe";
    private final String VALID_FIRST_NAME_2 = "María";
    private final String VALID_FIRST_NAME_3 = "Carlos";

    private final String VALID_LAST_NAME = "Pérez";
    private final String VALID_LAST_NAME_2 = "López";
    private final String VALID_LAST_NAME_3 = "García";

    private final String VALID_DNI = "12345678A";
    private final String VALID_DNI_2 = "87654321B";
    private final String VALID_DNI_3 = "45678912C";

    private final String VALID_EMAIL = "pepe@example.com";
    private final String VALID_EMAIL_2 = "maria@example.com";
    private final String VALID_EMAIL_3 = "carlos@example.com";

    private final String VALID_PASSWORD = "passwordValido1!";
    private final String VALID_PASSWORD_2 = "passwordValido2!";
    private final String VALID_PASSWORD_3 = "passwordValido3!";

    private final String VALID_BIRTH_DATE = "01/01/1990";
    private final String VALID_BIRTH_DATE_2 = "06/09/1994";
    private final String VALID_BIRTH_DATE_3 = "15/03/1988";

    private final String VALID_LANGUAGE = "ESP";
    private final String VALID_LANGUAGE_2 = "GAL";
    private final String VALID_LANGUAGE_3 = "ENG";

    private final String VALID_ROLE_EMPLOYEE = "EMPLOYEE";
    private final String VALID_ROLE_CLERK = "CLERK";
    private final String VALID_ROLE_ADMIN = "ADMIN";

    private final String VALID_IMAGE = "";

    private final String INVALID_DNI = "123456789";
    private final String INVALID_EMAIL = "emailSinArroba.com";
    private final String INVALID_BIRTH_DATE = "01/01/2030";
    private final String INVALID_LANGUAGE = "FRA";
    private final String INVALID_USER_ROLE = "TEST";
    private final String INVALID_PASSWORD = "123";

    private RegisterData getValidRegisterData1() {
        return new RegisterData(
                VALID_FIRST_NAME,
                VALID_LAST_NAME,
                VALID_DNI,
                VALID_EMAIL,
                VALID_BIRTH_DATE,
                VALID_LANGUAGE,
                VALID_ROLE_EMPLOYEE,
                VALID_IMAGE
        );
    }

    private RegisterData getValidRegisterData2() {
        return new RegisterData(
                VALID_FIRST_NAME_2,
                VALID_LAST_NAME_2,
                VALID_DNI_2,
                VALID_EMAIL_2,
                VALID_BIRTH_DATE_2,
                VALID_LANGUAGE_2,
                VALID_ROLE_CLERK,
                VALID_IMAGE
        );
    }

    private RegisterData getValidRegisterData3() {
        return new RegisterData(
                VALID_FIRST_NAME_3,
                VALID_LAST_NAME_3,
                VALID_DNI_3,
                VALID_EMAIL_3,
                VALID_BIRTH_DATE_3,
                VALID_LANGUAGE_3,
                VALID_ROLE_ADMIN,
                VALID_IMAGE
        );
    }

    private User getActivatedEMPLOYEE() throws Exception{
        // Rexistramos un usuario novo
        User user = userService.registerUser(getValidRegisterData1());

        // Establecemos un contrasinal, eliminase o token e activase o usuasrio.
        userService.setUserPassword(user.getDni(),user.getToken(),VALID_PASSWORD);
        return user;
    }

    private User getActivatedCLERK() throws Exception{
        // Rexistramos un usuario novo
        User user = userService.registerUser(getValidRegisterData2());

        // Establecemos un contrasinal, eliminase o token e activase o usuasrio.
        userService.setUserPassword(user.getDni(),user.getToken(),VALID_PASSWORD_2);
        return user;
    }

    private User getActivatedADMIN() throws Exception{
        // Rexistramos un usuario novo
        User user = userService.registerUser(getValidRegisterData3());

        // Establecemos un contrasinal, eliminase o token e activase o usuasrio.
        userService.setUserPassword(user.getDni(),user.getToken(),VALID_PASSWORD_3);
        return user;
    }

    @Test
    public void testRegisterAndFindByIdValidUser() throws InstanceNotFoundException, InputValidationException, DuplicateInstanceException, MessagingException, IOException {


        User user = userService.registerUser(getValidRegisterData1());
        User userFound = userService.findUserById(user.getId());

        // Comprobacións dos datos básicos
        assertEquals(user.getFirstName(), userFound.getFirstName());
        assertEquals(user.getLastName(), userFound.getLastName());
        assertEquals(user.getDni(), userFound.getDni());
        assertEquals(user.getLanguage(), userFound.getLanguage());
        assertEquals(user.getEmail(), userFound.getEmail());
        assertEquals(user.getRole(), userFound.getRole());

        // Comrpobación estado inicial esperado
        assertFalse(userFound.isActive());
        assertNotNull(userFound.getToken());
        assertNotNull(userFound.getExpiryDate());
        assertNull(userFound.getEncryptedPassword());
    }

    @Test(expected = DuplicateInstanceException.class)
    public void testRegisterDuplicateDni() throws InputValidationException, DuplicateInstanceException, MessagingException, IOException {

        RegisterData registerData2 = new RegisterData(
                VALID_FIRST_NAME_2,
                VALID_LAST_NAME_2,
                VALID_DNI,
                VALID_EMAIL_2,
                VALID_BIRTH_DATE_2,
                VALID_LANGUAGE_2,
                VALID_ROLE_CLERK,
                VALID_IMAGE
        );
        userService.registerUser(getValidRegisterData1());
        userService.registerUser(registerData2);
    }

    @Test(expected = DuplicateInstanceException.class)
    public void testRegisterDuplicateEmail() throws InputValidationException, DuplicateInstanceException, MessagingException, IOException {

        RegisterData registerData2 = new RegisterData(
                VALID_FIRST_NAME_2,
                VALID_LAST_NAME_2,
                VALID_DNI_2,
                VALID_EMAIL,
                VALID_BIRTH_DATE_2,
                VALID_LANGUAGE_2,
                VALID_ROLE_CLERK,
                VALID_IMAGE
        );

        userService.registerUser(getValidRegisterData1());
        userService.registerUser(registerData2);
    }

    @Test(expected = InputValidationException.class)
    public void testRegisterInvalidDni() throws Exception {
        RegisterData invalidData = new RegisterData(
                VALID_FIRST_NAME,
                VALID_LAST_NAME,
                INVALID_DNI,
                VALID_EMAIL,
                VALID_BIRTH_DATE,
                VALID_LANGUAGE,
                VALID_ROLE_EMPLOYEE,
                VALID_IMAGE
        );
        userService.registerUser(invalidData);
    }

    @Test(expected = InputValidationException.class)
    public void testRegisterInvalidEmail() throws Exception {
        RegisterData invalidData = new RegisterData(
                VALID_FIRST_NAME,
                VALID_LAST_NAME,
                VALID_DNI,
                INVALID_EMAIL,
                VALID_BIRTH_DATE,
                VALID_LANGUAGE,
                VALID_ROLE_EMPLOYEE,
                VALID_IMAGE
        );
        userService.registerUser(invalidData);
    }

    @Test(expected = InputValidationException.class)
    public void testRegisterInvalidLanguage() throws Exception {
        RegisterData invalidData = new RegisterData(
                VALID_FIRST_NAME,
                VALID_LAST_NAME,
                VALID_DNI,
                VALID_EMAIL,
                VALID_BIRTH_DATE,
                INVALID_LANGUAGE,
                VALID_ROLE_EMPLOYEE,
                VALID_IMAGE
        );
        userService.registerUser(invalidData);
    }

    @Test(expected = InputValidationException.class)
    public void testRegisterInvalidRole() throws Exception {
        RegisterData invalidData = new RegisterData(
                VALID_FIRST_NAME,
                VALID_LAST_NAME,
                VALID_DNI,
                VALID_EMAIL,
                VALID_BIRTH_DATE,
                VALID_LANGUAGE,
                INVALID_USER_ROLE,
                VALID_IMAGE
        );
        userService.registerUser(invalidData);
    }

    @Test(expected = InputValidationException.class)
    public void testRegisterFutureBirthDate() throws Exception {
        RegisterData invalidData = new RegisterData(
                VALID_FIRST_NAME,
                VALID_LAST_NAME,
                VALID_DNI,
                VALID_EMAIL,
                INVALID_BIRTH_DATE,
                VALID_LANGUAGE,
                VALID_ROLE_EMPLOYEE,
                VALID_IMAGE
        );
        userService.registerUser(invalidData);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetUserPasswordWithInvalidToken() throws Exception {
        // Rexistramos un usuario novo
        User user = userService.registerUser(getValidRegisterData1());

        // Comprobamos que inicialmente está inactivo e ten token
        assertFalse(user.isActive());
        assertNotNull(user.getToken());

        // Intento de activación cun token incorrecto
        userService.setUserPassword(user.getDni(), "invalid-token", VALID_PASSWORD);
    }

    @Test(expected = InstanceNotFoundException.class)
    public void testSetNonExistenUserPass() throws Exception {
        // Intento con usuario que non existe
        userService.setUserPassword(VALID_DNI, null, null);
    }

    @Test
    public void testActivateUserPassAndLoginCorrect() throws Exception {
        // Rexistramos un usuario novo
        User user = getActivatedEMPLOYEE();

        // Facemos o login do usuario creado comprobando tamen si se estableceu ben o contrasinal
        LoginData loginData = new LoginData(VALID_DNI, VALID_PASSWORD);
        User result = userService.loginUser(loginData);

        assertEquals(user, result);
    }

    @Test(expected = IncorrectLoginException.class)
    public void testLoginNonExistUser() throws Exception {

        // Facemos o login dun usuario que non existe
        LoginData loginData = new LoginData(VALID_DNI, VALID_PASSWORD);
        userService.loginUser(loginData);
    }

    @Test(expected = DisabledUserException.class)
    public void testLoginInactiveUser() throws Exception {
        // Rexistramos un usuario novo
        User user = userService.registerUser(getValidRegisterData1());

        // Facemos o login do usuario creado que está ainda inactivo
        LoginData loginData = new LoginData(VALID_DNI, VALID_PASSWORD);
        userService.loginUser(loginData);
    }

    @Test(expected = IncorrectLoginException.class)
    public void testLoginIncorrectPass() throws Exception {
        // Rexistramos un usuario novo
        User user = getActivatedEMPLOYEE();

        // Facemos o login do usuario creado con un contrasinal erroneo
        LoginData loginData = new LoginData(VALID_DNI, VALID_PASSWORD_2);
        userService.loginUser(loginData);
    }


    @Test
    public void testChangePassword() throws Exception {

        // Rexistramos un usuario novo
        User user = getActivatedEMPLOYEE();

        userService.changeUserPassword(user.getId(), VALID_PASSWORD, VALID_PASSWORD_2);

        assertTrue(passwordEncoder.matches(VALID_PASSWORD_2, user.getEncryptedPassword()));    }

    @Test(expected = InstanceNotFoundException.class)
    public void testChangePasswordNonExistUser()
            throws InstanceNotFoundException, IncorrectPasswordException, InputValidationException {

        userService.changeUserPassword((long) 345223453, VALID_PASSWORD, VALID_PASSWORD);

    }

    @Test(expected = IncorrectPasswordException.class)
    public void testChangeIncorrectPassword() throws Exception{

        // Rexistramos un usuario novo
        User user = getActivatedEMPLOYEE();

        // Probamos un contrasial actual incorrecto
        userService.changeUserPassword(user.getId(), VALID_PASSWORD_2, VALID_PASSWORD_2);

    }

    @Test(expected = InputValidationException.class)
    public void testChangeInvalidPassword() throws Exception {

        // Rexistramos un usuario novo
        User user = getActivatedEMPLOYEE();

        // Probamos un contrasial novo invalido
        userService.changeUserPassword(user.getId(), VALID_PASSWORD, INVALID_PASSWORD);
    }

    @Test
    public void testFindUsersByKeywords () throws Exception {

        // Rexistramos dous usuarios novos
        User user = getActivatedEMPLOYEE();
        User user2 = getActivatedCLERK();

        Block<User> result = userService.findUsersByKeywords(user.getFirstName(), user.getRole().toString(), 0, 5);
        assertEquals(1, result.getItems().size());
        assertFalse(result.getExistMoreItems());

        result = userService.findUsersByKeywords("e", null, 0, 5);
        assertEquals(2, result.getItems().size());
        assertFalse(result.getExistMoreItems());

        result = userService.findUsersByKeywords("A", VALID_ROLE_ADMIN, 0, 5);
        assertEquals(0, result.getItems().size());
        assertFalse(result.getExistMoreItems());
    }

    @Test(expected = InputValidationException.class)
    public void testFindUsersByKeywordsInvalidRole () throws Exception {
        userService.findUsersByKeywords("A", INVALID_USER_ROLE, 0, 5);
    }

    @Test
    public void testUpdateUserByAdmin() throws Exception {
        User user = getActivatedEMPLOYEE();

        // Usuario admin autenticado
        User admin = getActivatedADMIN();

        UserData userData = new UserData(
                VALID_FIRST_NAME_2,
                VALID_LAST_NAME_2,
                VALID_DNI_2,
                VALID_EMAIL_2,
                VALID_BIRTH_DATE_2,
                VALID_LANGUAGE_2,
                VALID_IMAGE,
                VALID_ROLE_CLERK,
                "false"
        );

        userService.updateUser(user.getId(), userData, admin.getId());

        // Se actualizan todos los datos, incluido rol y estado
        assertEquals(VALID_ROLE_CLERK, user.getRole().toString());
        assertFalse(user.isActive());
    }
    @Test
    public void testUpdateUserSelfUpdate() throws Exception {
        // Registramos y activamos un usuario
        User user = getActivatedEMPLOYEE();

        UserData userData = new UserData(
                VALID_FIRST_NAME_2,
                VALID_LAST_NAME_2,
                VALID_DNI_2,
                VALID_EMAIL_2,
                VALID_BIRTH_DATE_2,
                VALID_LANGUAGE_2,
                VALID_IMAGE,
                VALID_ROLE_ADMIN, // aunque intente cambiar a ADMIN
                "false"           // aunque intente desactivarse
        );

        userService.updateUser(user.getId(), userData, user.getId());

        // Se actualizan los datos personales
        assertEquals(VALID_FIRST_NAME_2, user.getFirstName());
        assertEquals(VALID_LAST_NAME_2, user.getLastName());
        assertEquals(VALID_DNI_2, user.getDni());
        assertEquals(VALID_EMAIL_2, user.getEmail());

        // Pero el rol y estado no cambian
        assertEquals(VALID_ROLE_EMPLOYEE, user.getRole().toString());
        assertTrue(user.isActive());
    }

    @Test(expected = PermissionException.class)
    public void testUpdateUserPermissionDenied() throws Exception {
        User user1 = getActivatedEMPLOYEE();
        User user2 = getActivatedCLERK();

        UserData userData = new UserData(
                VALID_FIRST_NAME_2,
                VALID_LAST_NAME_2,
                VALID_DNI_2,
                VALID_EMAIL_2,
                VALID_BIRTH_DATE_2,
                VALID_LANGUAGE_2,
                VALID_IMAGE,
                VALID_ROLE_EMPLOYEE,
                "true"
        );
        userService.updateUser(user1.getId(), userData, user2.getId());
    }

    @Test(expected = DuplicateInstanceException.class)
    public void testUpdateUserDuplicateDni() throws Exception {
        User user = getActivatedEMPLOYEE();
        getActivatedCLERK();

        UserData userData = new UserData(
                VALID_FIRST_NAME,
                VALID_LAST_NAME,
                VALID_DNI_2,
                VALID_EMAIL,
                VALID_BIRTH_DATE,
                VALID_LANGUAGE,
                VALID_IMAGE,
                VALID_ROLE_EMPLOYEE,
                "true"
        );
        userService.updateUser(user.getId(), userData, user.getId());
    }

    @Test(expected = DuplicateInstanceException.class)
    public void testUpdateUserDuplicateEmail() throws Exception {
        User user = getActivatedEMPLOYEE();
        getActivatedCLERK();

        UserData userData = new UserData(
                VALID_FIRST_NAME,
                VALID_LAST_NAME,
                VALID_DNI,
                VALID_EMAIL_2,
                VALID_BIRTH_DATE,
                VALID_LANGUAGE,
                VALID_IMAGE,
                VALID_ROLE_EMPLOYEE,
                "true"
        );
        userService.updateUser(user.getId(), userData, user.getId());
    }

    @Test(expected = InstanceNotFoundException.class)
    public void testUpdateNonExistUser() throws Exception {
        User user = getActivatedADMIN();
        userService.updateUser(user.getId() + 1, null, user.getId());

    }

    @Test(expected = InstanceNotFoundException.class)
    public void testUpdateNonExistUserChanger() throws Exception {
        User user = getActivatedEMPLOYEE();
        userService.updateUser(user.getId(), null, user.getId() + 1);
    }
}