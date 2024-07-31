package es.udc.tfg.app.userService;

import es.udc.tfg.app.model.user.User;
import es.udc.tfg.app.service.userservice.LoginData;
import es.udc.tfg.app.service.userservice.RegisterData;
import es.udc.tfg.app.service.userservice.UserData;
import es.udc.tfg.app.service.userservice.UserService;

import es.udc.tfg.app.util.conversors.CalendarConversor;
import es.udc.tfg.app.util.encrypt.PasswordEncrypter;
import es.udc.tfg.app.util.enums.UserRole;
import es.udc.tfg.app.util.exceptions.DuplicateInstanceException;
import es.udc.tfg.app.util.exceptions.IncorrectPasswordException;
import es.udc.tfg.app.util.exceptions.InputValidationException;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    UserService userService;

    private final String VALID_FIRST_NAME = "firstName1";
    private final String VALID_FIRST_NAME_2 = "firstName2";
    private final String VALID_LAST_NAME = "lastName1";
    private final String VALID_LAST_NAME_2 = "lastName2";
    private final String VALID_DNI = "12345678A";
    private final String VALID_DNI_2 = "12345679A";
    private final String VALID_EMAIL = "email123@udc.es";
    private final String VALID_EMAIL_2 = "email1234@udc.es";
    private final String VALID_PASSWORD = "password";
    private final String VALID_NEW_PASSWORD = "newpassword";
    private final String VALID_BIRTH_DATE = "01/01/1990";
    private final String VALID_BIRTH_DATE_2 = "06/09/1994";
    private final String VALID_LANGUAGE = "ESP";
    private final String VALID_LANGUAGE_2 = "GAL";
    private final String VALID_USER_TYPE = "EMPLOYEE";
    private final String VALID_USER_TYPE_2 = "CLERK";
    private final String VALID_USER_IMAGE = "asfasdgasdg";

    private final String INVALID_DNI = "123456789";
    private final String INVALID_EMAIL = "email123udc.es";
    private final String INVALID_BIRTH_DATE = "01/01/2030";
    private final String INVALID_LANGUAGE = "FRA";
    private final String INVALID_USER_TYPE = "TEST";
    private final String INVALID_PASSWORD = "pass";


    private RegisterData getValidRegisterData() {
        return new RegisterData(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_DNI, VALID_EMAIL, VALID_PASSWORD,
                VALID_BIRTH_DATE, VALID_LANGUAGE, VALID_USER_TYPE, VALID_USER_IMAGE);
    }

    private RegisterData getValidRegisterData2() {
        return new RegisterData(VALID_FIRST_NAME_2, VALID_LAST_NAME_2, VALID_DNI_2, VALID_EMAIL_2, VALID_PASSWORD,
                VALID_BIRTH_DATE_2, VALID_LANGUAGE_2, VALID_USER_TYPE_2, VALID_USER_IMAGE);
    }

    private UserData getValidUserData() {
        return new UserData(VALID_FIRST_NAME_2, VALID_LAST_NAME_2, VALID_DNI_2, VALID_EMAIL_2, VALID_BIRTH_DATE_2,
                VALID_LANGUAGE_2, VALID_USER_IMAGE);
    }

    @Test
    public void testRegisterAndFindByIdValidUser() throws InstanceNotFoundException, InputValidationException, DuplicateInstanceException {

        User user = userService.registerUser(getValidRegisterData());
        User userFound = userService.findUserById(user.getId());

        assertEquals(user.getFirstName(), userFound.getFirstName());
        assertEquals(user.getLastName(), userFound.getLastName());
        assertEquals(user.getLastName(), userFound.getLastName());
        assertEquals(user.getDni(), userFound.getDni());
        assertEquals(user.getLanguage(), userFound.getLanguage());
        assertEquals(user.getEncryptedPassword(), userFound.getEncryptedPassword());
        assertEquals(user.getEmail(), userFound.getEmail());
    }

    @Test(expected = DuplicateInstanceException.class)
    public void testRegisterDuplicateDni() throws InputValidationException, DuplicateInstanceException {

        RegisterData registerData1 = new RegisterData(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_DNI, VALID_EMAIL,
                VALID_PASSWORD, VALID_BIRTH_DATE, VALID_LANGUAGE, VALID_USER_TYPE, VALID_USER_IMAGE);
        RegisterData registerData2 = new RegisterData(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_DNI, VALID_EMAIL_2,
                VALID_PASSWORD, VALID_BIRTH_DATE, VALID_LANGUAGE, VALID_USER_TYPE, VALID_USER_IMAGE);

        userService.registerUser(registerData1);
        userService.registerUser(registerData2);

    }

    @Test(expected = DuplicateInstanceException.class)
    public void testRegisterDuplicateEmail() throws InputValidationException,
            DuplicateInstanceException {

        RegisterData registerData1 = new RegisterData(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_DNI, VALID_EMAIL,
                VALID_PASSWORD, VALID_BIRTH_DATE, VALID_LANGUAGE, VALID_USER_TYPE, VALID_USER_IMAGE);
        RegisterData registerData2 = new RegisterData(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_DNI_2, VALID_EMAIL,
                VALID_PASSWORD, VALID_BIRTH_DATE, VALID_LANGUAGE, VALID_USER_TYPE, VALID_USER_IMAGE);

        userService.registerUser(registerData1);
        userService.registerUser(registerData2);

    }

    @Test(expected = InputValidationException.class)
    public void testRegisterInvalidDni()
            throws InputValidationException, DuplicateInstanceException {

        RegisterData registerData = new RegisterData(VALID_FIRST_NAME, VALID_LAST_NAME, INVALID_DNI, VALID_EMAIL,
                VALID_PASSWORD, VALID_BIRTH_DATE, VALID_LANGUAGE, VALID_USER_TYPE, VALID_USER_IMAGE);

        userService.registerUser(registerData);

    }

    @Test(expected = InputValidationException.class)
    public void testRegisterInvalidEmail() throws InputValidationException,
            DuplicateInstanceException {

        RegisterData registerData = new RegisterData(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_DNI, INVALID_EMAIL,
                VALID_PASSWORD, VALID_BIRTH_DATE, VALID_LANGUAGE, VALID_USER_TYPE, VALID_USER_IMAGE);

        userService.registerUser(registerData);

    }

    @Test(expected = InputValidationException.class)
    public void testRegisterInvalidFirstName() throws InputValidationException,
            DuplicateInstanceException {

        RegisterData registerData = new RegisterData("", VALID_LAST_NAME, VALID_DNI, VALID_EMAIL, VALID_PASSWORD,
                VALID_BIRTH_DATE, VALID_LANGUAGE, VALID_USER_TYPE, VALID_USER_IMAGE);

        userService.registerUser(registerData);

    }

    @Test(expected = InputValidationException.class)
    public void testRegisterInvalidLastName() throws InputValidationException,
            DuplicateInstanceException {

        RegisterData registerData = new RegisterData(VALID_FIRST_NAME, "", VALID_DNI, VALID_EMAIL, VALID_PASSWORD,
                VALID_BIRTH_DATE, VALID_LANGUAGE, VALID_USER_TYPE, VALID_USER_IMAGE);

        userService.registerUser(registerData);

    }

    @Test(expected = InputValidationException.class)
    public void testRegisterInvalidBirthDate() throws InputValidationException,
            DuplicateInstanceException {

        RegisterData registerData = new RegisterData(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_DNI, VALID_EMAIL,
                VALID_PASSWORD, INVALID_BIRTH_DATE, VALID_LANGUAGE, VALID_USER_TYPE, VALID_USER_IMAGE);

        userService.registerUser(registerData);

    }

    @Test(expected = InputValidationException.class)
    public void testRegisterInvalidLanguage() throws InputValidationException,
            DuplicateInstanceException {

        RegisterData registerData = new RegisterData(VALID_FIRST_NAME, VALID_LAST_NAME, VALID_DNI, VALID_EMAIL,
                VALID_PASSWORD, VALID_BIRTH_DATE, INVALID_LANGUAGE, VALID_USER_TYPE, VALID_USER_IMAGE);

        userService.registerUser(registerData);

    }

    @Test
    public void testLoginValidData() throws InputValidationException, InstanceNotFoundException,
            DuplicateInstanceException, IncorrectPasswordException {

        User user = userService.registerUser(getValidRegisterData());
        LoginData loginData = new LoginData(VALID_DNI, VALID_PASSWORD);
        User loggedUser = userService.loginUser(loginData);

        assertEquals(user.getId(), loggedUser.getId());
    }

    @Test(expected = InstanceNotFoundException.class)
    public void testLoginNonExistsDni() throws InstanceNotFoundException, IncorrectPasswordException {

        LoginData loginData = new LoginData(VALID_DNI, VALID_PASSWORD);
        userService.loginUser(loginData);

    }

    @Test(expected = IncorrectPasswordException.class)
    public void testLoginIncorrectPass() throws InstanceNotFoundException, IncorrectPasswordException,
            InputValidationException, DuplicateInstanceException {

        userService.registerUser(getValidRegisterData());
        LoginData loginData = new LoginData(VALID_DNI, VALID_NEW_PASSWORD);
        userService.loginUser(loginData);

    }

    @Test
    public void testChangePassword() throws InputValidationException, DuplicateInstanceException,
            InstanceNotFoundException, IncorrectPasswordException {

        User user = userService.registerUser(getValidRegisterData());
        userService.changeUserPassword(user.getId(), VALID_PASSWORD, VALID_NEW_PASSWORD);

        assertTrue(PasswordEncrypter.isCorrectPassword(VALID_NEW_PASSWORD, user.getEncryptedPassword()));
    }

    @Test(expected = InstanceNotFoundException.class)
    public void testChangePasswordNonExistenUser()
            throws InstanceNotFoundException, IncorrectPasswordException, InputValidationException {

        userService.changeUserPassword((long) 1, VALID_PASSWORD, VALID_NEW_PASSWORD);

    }

    @Test(expected = IncorrectPasswordException.class)
    public void testChangeIncorrectPassword() throws InstanceNotFoundException, IncorrectPasswordException,
            InputValidationException, DuplicateInstanceException {

        User user = userService.registerUser(getValidRegisterData());
        userService.changeUserPassword(user.getId(), VALID_NEW_PASSWORD, VALID_PASSWORD);

    }

    @Test(expected = InputValidationException.class)
    public void testChangeInvalidPassword() throws InstanceNotFoundException, IncorrectPasswordException,
            InputValidationException, DuplicateInstanceException {

        User user = userService.registerUser(getValidRegisterData());
        userService.changeUserPassword(user.getId(), VALID_PASSWORD, INVALID_PASSWORD);

    }

    @Test
    public void testUpdateUserState() throws InstanceNotFoundException, IncorrectPasswordException,
            InputValidationException, DuplicateInstanceException {

        User user = userService.registerUser(getValidRegisterData());

        assertFalse(user.isActive());
        userService.changeUserState(user.getId());
        assertTrue(user.isActive());

    }

    @Test(expected = InstanceNotFoundException.class)
    public void testUpdateNonExistenUserState() throws InstanceNotFoundException {

        userService.changeUserState((long) 1);

    }

    @Test
    public void testUpdateUserRole() throws InstanceNotFoundException, IncorrectPasswordException,
            InputValidationException, DuplicateInstanceException {

        User user = userService.registerUser(getValidRegisterData());

        userService.updateUserRole(user.getId(), UserRole.CLERK.toString());
        assertEquals(user.getRole().toString(), "CLERK");

    }

    @Test(expected = InputValidationException.class)
    public void testUpdateNonExistenRoleUserRole() throws InstanceNotFoundException, IncorrectPasswordException,
            InputValidationException, DuplicateInstanceException {

        User user = userService.registerUser(getValidRegisterData());

        userService.updateUserRole(user.getId(), "CLEANER");

    }

    @Test(expected = InstanceNotFoundException.class)
    public void testUpdateNonExistenUserUserRole() throws InstanceNotFoundException, InputValidationException {

        userService.updateUserRole((long) 1, "ADMIN");

    }

    @Test
    public void testFindUserByEmail() throws InstanceNotFoundException, IncorrectPasswordException,
            InputValidationException, DuplicateInstanceException {

        User user = userService.registerUser(getValidRegisterData());

        userService.findUserByEmail(VALID_EMAIL);
        assertEquals(user.getEmail(), VALID_EMAIL);

    }

    @Test(expected = InstanceNotFoundException.class)
    public void testFindUserByEmailNonExistenEmail() throws InstanceNotFoundException, InputValidationException {

        userService.findUserByEmail(VALID_EMAIL);

    }

    @Test(expected = InputValidationException.class)
    public void testFindUserByEmailInvalidEmail() throws InstanceNotFoundException, InputValidationException {

        userService.findUserByEmail(INVALID_EMAIL);

    }

    @Test
    public void testFindUserByDni() throws InstanceNotFoundException, IncorrectPasswordException,
            InputValidationException, DuplicateInstanceException {

        User user = userService.registerUser(getValidRegisterData());

        userService.findUserByDni(VALID_DNI);
        assertEquals(user.getDni(), VALID_DNI);

    }

    @Test(expected = InstanceNotFoundException.class)
    public void testFindUserByDniNonExistenDni() throws InstanceNotFoundException, InputValidationException {

        userService.findUserByDni(VALID_DNI);

    }

    @Test(expected = InputValidationException.class)
    public void testFindUserByDniInvalidDni() throws InstanceNotFoundException, InputValidationException {

        userService.findUserByDni(INVALID_DNI);

    }

    @Test
    public void testFindUsersByFirstName() throws InstanceNotFoundException, IncorrectPasswordException,
            InputValidationException, DuplicateInstanceException {

        User user1 = userService.registerUser(getValidRegisterData());
        User user2 = userService.registerUser(getValidRegisterData2());

        List<User> listUser = userService.findUsersByFirstName("2");
        assertEquals(listUser.size(), 1);
        assertEquals(listUser.get(0).getFirstName(), user2.getFirstName());

        listUser = userService.findUsersByFirstName("first");
        assertEquals(listUser.size(), 2);
        assertEquals(listUser.get(0).getFirstName(), user1.getFirstName());
        assertEquals(listUser.get(1).getFirstName(), user2.getFirstName());
    }


    @Test
    public void testFindUsersByLastName() throws InstanceNotFoundException, IncorrectPasswordException,
            InputValidationException, DuplicateInstanceException {

        User user1 = userService.registerUser(getValidRegisterData());
        User user2 = userService.registerUser(getValidRegisterData2());

        List<User> listUser = userService.findUsersByLastName("2");
        assertEquals(listUser.size(), 1);
        assertEquals(listUser.get(0).getLastName(), user2.getLastName());

        listUser = userService.findUsersByLastName("last");
        assertEquals(listUser.size(), 2);
        assertEquals(listUser.get(0).getLastName(), user1.getLastName());
        assertEquals(listUser.get(1).getLastName(), user2.getLastName());
    }

    @Test
    public void testFindUsersByUserRole() throws InstanceNotFoundException, IncorrectPasswordException,
            InputValidationException, DuplicateInstanceException {

        User user1 = userService.registerUser(getValidRegisterData());
        User user2 = userService.registerUser(getValidRegisterData2());

        List<User> listUser = userService.findUsersByUserRole("CLERK");
        assertEquals(listUser.size(), 1);
        assertEquals(listUser.get(0).getRole().toString(), user2.getRole().toString());

        listUser = userService.findUsersByUserRole("EMPLOYEE");
        assertEquals(listUser.size(), 1);
        assertEquals(listUser.get(0).getRole().toString(), user1.getRole().toString());
    }

    @Test(expected = InputValidationException.class)
    public void testFindUsersByUserRoleNonExisten() throws InputValidationException {

        userService.findUsersByUserRole("CLEANER");

    }

    @Test
    public void testUpdateUser()
            throws InputValidationException, DuplicateInstanceException, InstanceNotFoundException {

        User user = userService.registerUser(getValidRegisterData());

        UserData userData = getValidUserData();

        userService.updateUser(user.getId(), userData);

        assertEquals(user.getFirstName(), userData.getFirstName());
        assertEquals(user.getLastName(), userData.getLastName());
        assertEquals(user.getDni(), userData.getDni());
        assertEquals(user.getEmail(), userData.getEmail());
        assertEquals(user.getBirthDate(), CalendarConversor.stringToCalendar(userData.getBirthDate()));
        assertEquals(user.getLanguage().toString(), userData.getLanguage().toString());
        assertEquals(user.getImage(), userData.getImage());

    }
    @Test(expected = InstanceNotFoundException.class)
    public void testUpdateNonExistenUser()
            throws InputValidationException, InstanceNotFoundException, DuplicateInstanceException {

        userService.updateUser((long) 1, null);

    }

    @Test(expected = InputValidationException.class)
    public void testUpdateInvalidDni() throws InputValidationException, InstanceNotFoundException,
            DuplicateInstanceException {

        User user1 = userService.registerUser(getValidRegisterData());
        userService.registerUser(getValidRegisterData2());

        UserData userData = getValidUserData();

        userData.setDni(INVALID_DNI);

        userService.updateUser(user1.getId(), userData);

    }

    @Test(expected = DuplicateInstanceException.class)
    public void testUpdateDuplicateDni() throws InputValidationException, InstanceNotFoundException,
            DuplicateInstanceException {

        User user = userService.registerUser(getValidRegisterData());
        userService.registerUser(getValidRegisterData2());

        UserData userData = getValidUserData();

        userData.setDni(VALID_DNI_2);

        userService.updateUser(user.getId(), userData);

    }

    @Test(expected = InputValidationException.class)
    public void testUpdateInvalidEmail() throws InputValidationException, InstanceNotFoundException,
            DuplicateInstanceException {

        User user = userService.registerUser(getValidRegisterData());

        UserData userData = getValidUserData();

        userData.setEmail(INVALID_EMAIL);

        userService.updateUser(user.getId(), userData);

    }

    @Test(expected = DuplicateInstanceException.class)
    public void testUpdateDuplicateEmail() throws InputValidationException, InstanceNotFoundException,
            DuplicateInstanceException {

        User user = userService.registerUser(getValidRegisterData());
        userService.registerUser(getValidRegisterData2());

        UserData userData = getValidUserData();

        userData.setEmail(INVALID_EMAIL);

        userService.updateUser(user.getId(), userData);

    }

    @Test(expected = InputValidationException.class)
    public void testUpdateInvalidFirstName() throws InputValidationException, InstanceNotFoundException,
            DuplicateInstanceException {

        User user = userService.registerUser(getValidRegisterData());

        UserData userData = getValidUserData();

        userData.setFirstName(null);

        userService.updateUser(user.getId(), userData);

    }

    @Test(expected = InputValidationException.class)
    public void testUpdateInvalidLastName() throws InputValidationException, InstanceNotFoundException,
            DuplicateInstanceException {

        User user = userService.registerUser(getValidRegisterData());

        UserData userData = getValidUserData();

        userData.setLastName(null);

        userService.updateUser(user.getId(), userData);

    }

    @Test(expected = InputValidationException.class)
    public void testUpdateInvalidLanguage() throws InputValidationException, InstanceNotFoundException,
            DuplicateInstanceException {

        User user = userService.registerUser(getValidRegisterData());

        UserData userData = getValidUserData();

        userData.setLanguage(INVALID_LANGUAGE);

        userService.updateUser(user.getId(), userData);

    }

    @Test(expected = InputValidationException.class)
    public void testUpdateInvalidBirthDate() throws InputValidationException, InstanceNotFoundException,
            DuplicateInstanceException {

        User user = userService.registerUser(getValidRegisterData());

        UserData userData = getValidUserData();

        userData.setBirthDate(INVALID_BIRTH_DATE);

        userService.updateUser(user.getId(), userData);

    }

    @Test
    public void testFindAllUsers() throws InstanceNotFoundException, IncorrectPasswordException,
            InputValidationException, DuplicateInstanceException {

        User user1 = userService.registerUser(getValidRegisterData());

        List<User> listUser = userService.findAllUsers();
        assertEquals(listUser.size(), 1);
        assertEquals(listUser.get(0).getId(), user1.getId());

        User user2 = userService.registerUser(getValidRegisterData2());

        listUser = userService.findAllUsers();
        assertEquals(listUser.size(), 2);
        assertEquals(listUser.get(0).getId(), user1.getId());
        assertEquals(listUser.get(1).getId(), user2.getId());
    }

}

