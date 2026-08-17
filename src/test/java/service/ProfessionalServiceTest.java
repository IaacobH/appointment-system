package service;

import exception.EntityNotFoundException;
import model.Professional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.ProfessionalRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfessionalServiceTest {

    @Mock
    ProfessionalRepository professionalRepository;

    @InjectMocks
    ProfessionalService professionalService;

    @Test
    void shouldRegisterProfessional(){

        var professional1 = professionalService.register("name1", "surname1", "email1", "speciality1");

        assertEquals("name1", professional1.getName());
        assertEquals("surname1", professional1.getLastname());
        assertEquals("email1", professional1.getEmail());
        assertEquals("speciality1", professional1.getSpeciality());

        verify(professionalRepository).save(professional1);

    }


    @Test
    void shouldFindProfessionalById(){

        var fakeProfessional = new Professional(1, "name1", "surname1", "email1", "speciality1");
        when(professionalRepository.findById(1)).thenReturn(Optional.of(fakeProfessional));

        var professionalFound = professionalService.findById(1);
        assertEquals(fakeProfessional, professionalFound);
    }

    @Test
    void shouldFindAllProfessionals(){

        var p1 = new Professional(1, "name1", "surname1", "email1", "speciality1");
        var p2 = new Professional(2,"name2", "surname2", "email2", "speciality2");
        when(professionalRepository.findAll()).thenReturn(List.of(p1,p2));

        List<Professional> result = professionalService.findAll();

        assertTrue(result.contains(p1));
        assertTrue(result.contains(p2));
        assertEquals(2, result.size());

    }

    @Test
    void shouldThrowWhenProfessionalDoesNotExist() {
        when(professionalRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(
                EntityNotFoundException.class,
                () -> professionalService.findById(999)
        );
    }


}
