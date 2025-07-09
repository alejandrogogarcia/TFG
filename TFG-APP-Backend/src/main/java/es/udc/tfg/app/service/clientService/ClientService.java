package es.udc.tfg.app.service.clientService;

import es.udc.tfg.app.model.Client.Client;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.util.exceptions.DuplicateInstanceException;
import es.udc.tfg.app.util.exceptions.InputValidationException;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;

public interface ClientService {

    public Client createClient(Long creatorId, ClientData clientData) throws InputValidationException, DuplicateInstanceException, InstanceNotFoundException;

    public void updateClient(Long id, ClientData clientData) throws InstanceNotFoundException, InputValidationException, DuplicateInstanceException;

    public Client findClientById(Long id) throws InstanceNotFoundException;

    public Block<Client> findClientByKeywords(String keywords, int page, int size);
}
