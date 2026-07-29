package service;

import exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import repository.ProfessionalRepository;
import static org.junit.jupiter.api.Assertions.*;


public class ProfessionalServiceTest {

    @Test
    void shouldRegisterProfessional(){
        var professionalRepository = new ProfessionalRepository();
        var professionalService = new ProfessionalService(professionalRepository);

        var professional1 = professionalService.register(
                "name1",
                "surname1",
                "email1",
                "speciality1"
        );
        var professional2 = professionalService.register(
                "name2",
                "surname2",
                "email2",
                "speciality2"
        );

        assertEquals("name1", professional1.getName());
        assertEquals("surname1", professional1.getLastname());
        assertEquals("email1", professional1.getEmail());
        assertEquals("speciality1", professional1.getSpeciality());

        assertEquals("name2", professional2.getName());


        assertEquals(1, professional1.getId());
        assertEquals(2, professional2.getId());

    }


    @Test
    void shouldFindProfessionalById(){
        var professionalRepository = new ProfessionalRepository();
        var professionalService = new ProfessionalService(professionalRepository);

        var professionalRegistered = professionalService.register(
                "name1",
                "surname1",
                "email1",
                "speciality1"
        );

        var profesionalFound = professionalService.findById(1);
        assertEquals(professionalRegistered, profesionalFound);
    }

    @Test
    void shouldFindAllProfessionals(){
        var professionalRepository = new ProfessionalRepository();
        var professionalService = new ProfessionalService(professionalRepository);

        var professional1 = professionalService.register(
                "name1",
                "surname1",
                "email1",
                "speciality1"
        );
        var professional2 = professionalService.register(
                "name2",
                "surname2",
                "email2",
                "speciality2"
        );

        assertTrue(professionalService.findAll().contains(professional1));
        assertTrue(professionalService.findAll().contains(professional2));
        assertEquals(2, professionalService.findAll().size());

    }

    @Test
    void shouldThrowWhenProfessionalDoesNotExist() {
        var professionalRepository = new ProfessionalRepository();
        var professionalService = new ProfessionalService(professionalRepository);

        assertThrows(
                EntityNotFoundException.class,
                () -> professionalService.findById(999)
        );
    }


}
