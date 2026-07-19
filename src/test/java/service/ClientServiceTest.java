package service;

import exception.EntityNotFoundException;
import model.Client;
import org.junit.jupiter.api.Test;
import repository.ClientRepository;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceTest {

    @Test
    void shouldRegisterClient() {
        ClientRepository clientRepository = new ClientRepository();
        ClientService clientService = new ClientService(clientRepository);

        Client firstClient = clientService.register(
                "Iaacob",
                "Hambra",
                "iaacob@email.com"
        );

        Client secondClient = clientService.register(
                "Carlos",
                "Varela",
                "carlos@email.com"
        );

        //prueba que sean iguales los datos
        assertEquals("Iaacob", firstClient.getName());
        assertEquals("Hambra", firstClient.getLastname());
        assertEquals("iaacob@email.com", firstClient.getEmail());

        //id asignado correctamente
        assertEquals(1, firstClient.getId());
        assertEquals(2, secondClient.getId());

    }

    @Test
    void shouldFindAll(){
        ClientRepository repository = new ClientRepository();
        ClientService service = new ClientService(repository);

        Client firstClient = service.register(
                "Iaacob",
                "Hambra",
                "iaacob@email.com"
        );

        Client secondClient = service.register(
                "Carlos",
                "Varela",
                "carlos@email.com"
        );

        assertTrue(repository.findAll().contains(firstClient));
        assertTrue(repository.findAll().contains(secondClient));
        assertEquals(2, repository.findAll().size());
    }

    @Test
    void shouldFindClientById(){
        ClientRepository repository = new ClientRepository();
        ClientService service = new ClientService(repository);

        Client registeredClient = service.register(
                "Iaacob",
                "Hambra",
                "iaacob@email.com"
        );
        Client foundClient = service.findById(registeredClient.getId());

        assertEquals(registeredClient, foundClient);
    }

    @Test
    void shouldThrowWhenClientDoesNotExist() {
        ClientRepository repository = new ClientRepository();
        ClientService service = new ClientService(repository);

        assertThrows(
                EntityNotFoundException.class,
                () -> service.findById(999)
        );
    }
}
