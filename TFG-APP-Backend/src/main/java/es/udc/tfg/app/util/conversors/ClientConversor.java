package es.udc.tfg.app.util.conversors;

import es.udc.tfg.app.model.Client.Client;
import es.udc.tfg.app.model.Product.Product;
import es.udc.tfg.app.rest.dtos.ClientDto;
import es.udc.tfg.app.rest.dtos.ProductDto;

import java.util.List;
import java.util.stream.Collectors;

public class ClientConversor {

    public static ClientDto toClientDto(Client client) {
        return new ClientDto(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getDni(),
                client.getAddress(),
                client.getCity(),
                client.getPostCode(),
                client.getEmail(),
                client.getPhoneNumber(),
                CalendarConversor.calendarToString(client.getCreateDate()),
                client.getCreator().getId());
    }

    public static List<ClientDto> toClientDto(List<Client> clients) {
        return clients.stream().map(ClientConversor::toClientDto).collect(Collectors.toList());
    }
}