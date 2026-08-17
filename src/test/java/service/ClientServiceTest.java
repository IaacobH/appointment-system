package service;

import exception.EntityNotFoundException;
import model.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.ClientRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientService clientService;

    @Test
    void shouldRegisterClient() {

        Client firstClient = clientService.register(
                "Iaacob",
                "Hambra",
                "iaacob@email.com"
        );

        assertEquals("Iaacob", firstClient.getName());
        assertEquals("Hambra", firstClient.getLastname());
        assertEquals("iaacob@email.com", firstClient.getEmail());

        //verifica que al registrar se llamo al metodo save pasandole por parametro el cliente
        verify(clientRepository).save(firstClient);
    }

    @Test
    void shouldFindAll(){

        Client c1 = new Client(1, "Iaacob", "Hambra", "iaacob@email.com");
        Client c2 = new Client(2, "Carlos", "Varela", "carlos@email.com");
        when(clientRepository.findAll()).thenReturn(List.of(c1, c2));

        List<Client> result = clientService.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(c1));
        assertTrue(result.contains(c2));
    }

    @Test
    void shouldFindClientById(){

        Client fakeClient = new Client(1, "Iaacob", "Hambra", "iaacobh@gmail.com");
        when(clientRepository.findById(1)).thenReturn(Optional.of(fakeClient));

        Client result = clientService.findById(1);
        assertEquals("Iaacob", result.getName());
    }

    @Test
    void shouldThrowWhenClientDoesNotExist() {
        when(clientRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(
                EntityNotFoundException.class,
                () -> clientService.findById(999)
        );
    }
}
