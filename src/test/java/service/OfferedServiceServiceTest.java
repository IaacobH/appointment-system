package service;

import exception.EntityNotFoundException;
import model.OfferedService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.OfferedServiceRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class OfferedServiceServiceTest {

    @Mock
    OfferedServiceRepository offeredServiceRepository;

    @InjectMocks
    OfferedServiceService offeredServiceService;

    @Test
    void shouldRegisterOfferedService(){
        when(offeredServiceRepository.save(any(OfferedService.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var offeredService1 = offeredServiceService.register(
                "name1",
                40
        );

        assertEquals("name1", offeredService1.getServiceName());
        assertEquals(40, offeredService1.getPrice());

        verify(offeredServiceRepository).save(offeredService1);
    }


    @Test
    void shouldFindOfferedServiceById(){

        var offeredService1 = new OfferedService(
                1,
                "name1",
                20
        );
        when(offeredServiceRepository.findById(1)).thenReturn(Optional.of(offeredService1));

        var offeredServiceFound = offeredServiceService.findById(1);
        assertEquals(offeredService1, offeredServiceFound);
    }

    @Test
    void shouldFindAllOfferedServices(){
        var o1 = new OfferedService(
                1,
                "name1",
                40
        );
        var o2 = new OfferedService(
                2,
                "name2",
                20
        );
        when(offeredServiceRepository.findAll()).thenReturn(List.of(o1,o2));

        List<OfferedService> offeredServices = offeredServiceService.findAll();

        assertTrue(offeredServices.contains(o1));
        assertTrue(offeredServices.contains(o2));
        assertEquals(2, offeredServices.size());
    }

    @Test
    void shouldThrowWhenOfferedServiceDoesNotExist() {
        when(offeredServiceRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> offeredServiceService.findById(999)
        );
    }


}
