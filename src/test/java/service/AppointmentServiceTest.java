package service;

import org.junit.jupiter.api.Test;
import repository.AppointmentRepository;
import repository.ClientRepository;
import repository.OfferedServiceRepository;
import repository.ProfessionalRepository;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

public class AppointmentServiceTest {

    @Test
    void shouldCreateAppointmentAndWithId() {
        var clientRepository = new ClientRepository();
        var clientService = new ClientService(clientRepository);
        var offeredServiceRepository = new OfferedServiceRepository();
        var offeredServiceService = new OfferedServiceService(offeredServiceRepository);
        var professionalRepository = new ProfessionalRepository();
        var professionalService = new ProfessionalService(professionalRepository);
        var appointmentRepository = new AppointmentRepository();
        var appointmentService = new AppointmentService(appointmentRepository, clientService,
                professionalService, offeredServiceService);

        var client1 = clientService.register("Iaacob", "Hambra", "iaacob@email.com");
        var offeredService1 = offeredServiceService.register("name1", 40);
        var professional1 = professionalService.register("name1", "surname1", "email1",
                "speciality1");

        var datetime1 = LocalDateTime.of(2026, 12, 25, 18, 30);
        var datetime2 = LocalDateTime.of(2030,10,10,10,10);

        var appointment1 = appointmentService.createAppointment(
                offeredService1,
                professional1,
                client1,
                LocalDateTime.of(2026, 12, 25, 18, 30));

        assertEquals(1, appointment1.getId());
        assertEquals(offeredService1, appointment1.getOfferedService());
        assertEquals(professional1, appointment1.getProfessional());
        assertEquals(client1, appointment1.getClient());
        assertEquals(datetime1, appointment1.getDateTime());

        var appointment2 = appointmentService.createAppointmentWithId(1,1,1,datetime2);
        assertEquals(2, appointment2.getId());
        assertEquals(offeredService1, appointment2.getOfferedService());
        assertEquals(professional1, appointment2.getProfessional());
        assertEquals(client1, appointment2.getClient());
        assertEquals(datetime2, appointment2.getDateTime());

    }

}
