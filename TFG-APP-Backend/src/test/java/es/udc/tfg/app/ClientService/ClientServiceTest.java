package es.udc.tfg.app.ClientService;

import es.udc.tfg.app.model.Client.Client;
import es.udc.tfg.app.model.user.User;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.service.clientService.ClientData;
import es.udc.tfg.app.service.clientService.ClientService;
import es.udc.tfg.app.service.userService.RegisterData;
import es.udc.tfg.app.service.userService.UserService;
import es.udc.tfg.app.util.exceptions.DuplicateInstanceException;
import es.udc.tfg.app.util.exceptions.InputValidationException;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserService userService;

    // Datos válidos
    private final String VALID_FIRST_NAME = "Ana";
    private final String VALID_FIRST_NAME_2 = "Jose";
    private final String VALID_LAST_NAME = "García";
    private final String VALID_LAST_NAME_2 = "Dominguez";
    private final String VALID_DNI = "11111111A";
    private final String VALID_DNI_2 = "22222222B";
    private final String VALID_ADDRESS = "Rúa Principal 123";
    private final String VALID_CITY = "Lugo";
    private final Long VALID_POST_CODE = 27001L;
    private final String VALID_EMAIL = "ana@example.com";
    private final String VALID_EMAIL_2 = "maria@example.com";
    private final Long VALID_PHONE = 600111222L;
    private final Long VALID_PHONE_2 = 700111222L;

    // Datos inválidos
    private final String INVALID_DNI = "123456789";
    private final String INVALID_EMAIL = "emailIncorrecto.com";

    private ClientData getValidClientData1() {
        return new ClientData(
                VALID_FIRST_NAME,
                VALID_LAST_NAME,
                VALID_DNI,
                VALID_ADDRESS,
                VALID_CITY,
                VALID_POST_CODE,
                VALID_EMAIL,
                VALID_PHONE
        );
    }

    private ClientData getValidClientData2() {
        return new ClientData(
                VALID_FIRST_NAME_2,
                VALID_LAST_NAME_2,
                VALID_DNI_2,
                VALID_ADDRESS,
                VALID_CITY,
                VALID_POST_CODE,
                VALID_EMAIL_2,
                VALID_PHONE_2
        );
    }

    private User getCreatorUser() throws Exception {
        RegisterData registerData = new RegisterData(
                "Pepe",
                "Pérez",
                "12345678A",
                "pepe@example.com",
                "01/01/1990",
                "ESP",
                "ADMIN",
                ""
        );
        User user = userService.registerUser(registerData);
        userService.setUserPassword(user.getDni(), user.getToken(), "passwordValido1!");
        return user;
    }

    @Test
    public void testCreateAndFindClientById() throws Exception {
        User creator = getCreatorUser();
        Client client = clientService.createClient(creator.getId(), getValidClientData1());

        Client found = clientService.findClientById(client.getId());

        assertEquals(VALID_FIRST_NAME, found.getFirstName());
        assertEquals(VALID_LAST_NAME, found.getLastName());
        assertEquals(VALID_DNI.toLowerCase(), found.getDni());
        assertEquals(VALID_EMAIL, found.getEmail());
        assertEquals(creator.getId(), found.getCreator().getId());
        assertNotNull(found.getCreateDate());
    }

    @Test(expected = DuplicateInstanceException.class)
    public void testCreateClientDuplicateDni() throws Exception {
        User creator = getCreatorUser();
        clientService.createClient(creator.getId(), getValidClientData1());
        clientService.createClient(creator.getId(), getValidClientData1());
    }

    @Test(expected = InputValidationException.class)
    public void testCreateClientInvalidDni() throws Exception {
        User creator = getCreatorUser();
        ClientData invalidData = new ClientData(
                VALID_FIRST_NAME,
                VALID_LAST_NAME,
                INVALID_DNI,
                VALID_ADDRESS,
                VALID_CITY,
                VALID_POST_CODE,
                VALID_EMAIL,
                VALID_PHONE
        );
        clientService.createClient(creator.getId(), invalidData);
    }

    @Test(expected = InputValidationException.class)
    public void testCreateClientInvalidEmail() throws Exception {
        User creator = getCreatorUser();
        ClientData invalidData = new ClientData(
                VALID_FIRST_NAME,
                VALID_LAST_NAME,
                VALID_DNI,
                VALID_ADDRESS,
                VALID_CITY,
                VALID_POST_CODE,
                INVALID_EMAIL,
                VALID_PHONE
        );
        clientService.createClient(creator.getId(), invalidData);
    }

    @Test
    public void testUpdateClient() throws Exception {
        User creator = getCreatorUser();
        Client client = clientService.createClient(creator.getId(), getValidClientData1());

        clientService.updateClient(client.getId(), getValidClientData2());

        assertEquals(VALID_FIRST_NAME_2, client.getFirstName());
        assertEquals(VALID_LAST_NAME_2, client.getLastName());
        assertEquals(VALID_DNI_2.toLowerCase(), client.getDni());
        assertEquals(VALID_EMAIL_2, client.getEmail());
    }

    @Test(expected = InstanceNotFoundException.class)
    public void testUpdateClientNotFound() throws Exception {
        clientService.updateClient(1L, getValidClientData1());
    }

    @Test(expected = DuplicateInstanceException.class)
    public void testUpdateClientDuplicateDni() throws Exception {
        User creator = getCreatorUser();
        Client client1 = clientService.createClient(creator.getId(), getValidClientData1());
        Client client2 = clientService.createClient(creator.getId(), getValidClientData2());

        ClientData duplicateData = new ClientData(
                client2.getFirstName(),
                client2.getLastName(),
                client1.getDni(),
                client2.getAddress(),
                client2.getCity(),
                client2.getPostCode(),
                client2.getEmail(),
                client2.getPhoneNumber()
        );
        clientService.updateClient(client2.getId(), duplicateData);
    }

    @Test
    public void testFindClientByKeywords() throws Exception {
        User creator = getCreatorUser();
        clientService.createClient(creator.getId(), getValidClientData1());
        clientService.createClient(creator.getId(), getValidClientData2());

        Block<Client> block = clientService.findClientByKeywords("Jos", 0, 10);

        assertEquals(1, block.getItems().size());
        assertEquals(VALID_FIRST_NAME_2, block.getItems().get(0).getFirstName());

        block = clientService.findClientByKeywords("", 0, 10);

        assertEquals(2, block.getItems().size());
    }

}
