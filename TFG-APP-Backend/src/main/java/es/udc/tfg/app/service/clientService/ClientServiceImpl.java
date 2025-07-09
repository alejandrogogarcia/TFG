package es.udc.tfg.app.service.clientService;

import es.udc.tfg.app.model.Client.Client;
import es.udc.tfg.app.model.Client.ClientDao;
import es.udc.tfg.app.model.user.User;
import es.udc.tfg.app.model.user.UserDao;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.util.exceptions.DuplicateInstanceException;
import es.udc.tfg.app.util.exceptions.InputValidationException;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import es.udc.tfg.app.util.validator.ValidatorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService{

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Client createClient(Long creatorId, ClientData clientData)
            throws InputValidationException, DuplicateInstanceException, InstanceNotFoundException {

        String dni = clientData.getDni().trim().toLowerCase();
        ValidatorProperties.validateDni(dni);

        try {
            clientDao.findByDni(dni);
            throw new DuplicateInstanceException(dni, Client.class.getName());
        } catch (InstanceNotFoundException e) {}

        User creator = userDao.find(creatorId);
        String firstName = clientData.getFirstName();
        ValidatorProperties.validateString(firstName);
        String lastName = clientData.getLastName();
        ValidatorProperties.validateString(lastName);
        String email = clientData.getEmail();
        ValidatorProperties.validateEmail(email);

        Client client = new Client(firstName, lastName, dni, clientData.getAddress(),
                clientData.getCity(), clientData.getPostCode(), email, clientData.getPhoneNumber(), creator);
        clientDao.save(client);
        return client;
    }


    @Override
    public void updateClient(Long id, ClientData clientData) throws InstanceNotFoundException, InputValidationException, DuplicateInstanceException {
        Client client = clientDao.find(id);
        String dni = clientData.getDni().trim().toLowerCase();
        ValidatorProperties.validateDni(dni);

        if (!client.getDni().trim().toLowerCase().equals(dni)) {
            try {
                clientDao.findByDni(dni);
                throw new DuplicateInstanceException(dni, Client.class.getName());
            } catch (InstanceNotFoundException e) {
            }
        }
        String firstName = clientData.getFirstName();
        ValidatorProperties.validateString(firstName);
        String lastName = clientData.getLastName();
        ValidatorProperties.validateString(lastName);
        String email = clientData.getEmail();
        ValidatorProperties.validateEmail(email);

        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setDni(dni);
        client.setAddress(clientData.getAddress());
        client.setCity(clientData.getCity());
        client.setPostCode(clientData.getPostCode());
        client.setPhoneNumber(clientData.getPhoneNumber());
        client.setEmail(email);
    }

    @Override
    public Client findClientById(Long id) throws InstanceNotFoundException {
        return clientDao.find(id);
    }

    @Override
    public Block<Client> findClientByKeywords(String keywords, int page, int size) {

        Slice<Client> slice = clientDao.findByKeywords(keywords, page, size);
        return new Block<>(slice.getContent(), slice.hasNext());
    }
}
