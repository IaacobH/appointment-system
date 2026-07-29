package service;

import exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import repository.OfferedServiceRepository;

import static org.junit.jupiter.api.Assertions.*;

public class OfferedServiceServiceTest {
    @Test
    void shouldRegisterOfferedService(){
        var offeredServiceRepository = new OfferedServiceRepository();
        var offeredServiceService = new OfferedServiceService(offeredServiceRepository);

        var offeredService1 = offeredServiceService.register(
                "name1",
                40
        );
        var offeredService2 = offeredServiceService.register(
                "name2",
                20
        );

        assertEquals("name1", offeredService1.getServiceName());
        assertEquals(40, offeredService1.getPrice());
        assertEquals("name2", offeredService2.getServiceName());
        assertEquals(20, offeredService2.getPrice());

        assertEquals(1, offeredService1.getId());
        assertEquals(2, offeredService2.getId());

    }


    @Test
    void shouldFindOfferedServiceById(){
        var offeredServiceRepository = new OfferedServiceRepository();
        var offeredServiceService = new OfferedServiceService(offeredServiceRepository);

        var offeredServiceRegistered = offeredServiceService.register(
                "name1",
                20
        );

        var offeredServiceFound = offeredServiceService.findById(1);
        assertEquals(offeredServiceRegistered, offeredServiceFound);
    }

    @Test
    void shouldFindAllOfferedServices(){
        var offeredServiceRepository = new OfferedServiceRepository();
        var offeredServiceService = new OfferedServiceService(offeredServiceRepository);

        var offeredService1 = offeredServiceService.register(
                "name1",
                40
        );
        var offeredService2 = offeredServiceService.register(
                "name2",
                20
        );

        assertTrue(offeredServiceService.findAll().contains(offeredService1));
        assertTrue(offeredServiceService.findAll().contains(offeredService2));
        assertEquals(2, offeredServiceService.findAll().size());

    }

    @Test
    void shouldThrowWhenOfferedServiceDoesNotExist() {
        var offeredServiceRepository = new OfferedServiceRepository();
        var offeredServiceService = new OfferedServiceService(offeredServiceRepository);

        assertThrows(
                EntityNotFoundException.class,
                () -> offeredServiceService.findById(999)
        );
    }


}
