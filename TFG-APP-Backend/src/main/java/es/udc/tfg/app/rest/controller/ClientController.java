package es.udc.tfg.app.rest.controller;

import es.udc.tfg.app.model.Client.Client;
import es.udc.tfg.app.rest.dtos.ClientDto;
import es.udc.tfg.app.service.Block;
import es.udc.tfg.app.service.clientService.ClientData;
import es.udc.tfg.app.service.clientService.ClientService;
import es.udc.tfg.app.util.conversors.ClientConversor;
import es.udc.tfg.app.util.exceptions.DuplicateInstanceException;
import es.udc.tfg.app.util.exceptions.InputValidationException;
import es.udc.tfg.app.util.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientData clientData, @RequestAttribute Long userId) throws InputValidationException, DuplicateInstanceException, InstanceNotFoundException {


        Client client = clientService.createClient(userId, clientData);
        System.out.println("ID " +client.getId());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/").build().toUri();

        return ResponseEntity.created(location).body(ClientConversor.toClientDto(client));
    }

    @PutMapping("/{id}/update")
    public void updateClient(@PathVariable Long id, @RequestBody ClientData clientData) throws InputValidationException, DuplicateInstanceException, InstanceNotFoundException {
        clientService.updateClient(id, clientData);
    }

    @GetMapping("/{id}")
    public ClientDto getClient(@PathVariable Long id) throws InstanceNotFoundException {
        return ClientConversor.toClientDto(clientService.findClientById(id));
    }

    @GetMapping("/find")
    public Block<ClientDto> findClients(@RequestParam (required = false) String keywords,
                                     @RequestParam (defaultValue = "0") int page) {
        Block<Client> clientBlock = clientService.findClientByKeywords(keywords != null ? keywords.trim() : null, page, 5);
        return new Block<>(ClientConversor.toClientDto(clientBlock.getItems()), clientBlock.getExistMoreItems());
    }
}
