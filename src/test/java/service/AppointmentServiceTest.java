package service;

import exception.EntityNotFoundException;
import model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.AppointmentRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock AppointmentRepository appointmentRepository;
    @Mock ClientService clientService;
    @Mock ProfessionalService professionalService;
    @Mock OfferedServiceService offeredServiceService;


    @InjectMocks
    AppointmentService appointmentService;

    @Test
    void shouldCreateAppointment() {
        var client = new Client(1, "Iaacob", "Hambra", "iaacob@email.com");
        var professional = new Professional(1, "name1", "surname1", "email1", "speciality1");
        var offeredService = new OfferedService(1, "name1", 40);
        var dateTime = LocalDateTime.of(2026, 12, 25, 18, 30);

        var appointment = appointmentService.createAppointment(offeredService, professional, client, dateTime);

        assertEquals(offeredService, appointment.getOfferedService());
        assertEquals(professional, appointment.getProfessional());
        assertEquals(client, appointment.getClient());
        assertEquals(dateTime, appointment.getDateTime());
        verify(appointmentRepository).save(appointment);
    }

    @Test
    void shouldCreateAppointmentWithId_whenNoConflicts() {
        var client = new Client(1, "Iaacob", "Hambra", "iaacob@email.com");
        var professional = new Professional(1, "name1", "surname1", "email1", "speciality1");
        var offeredService = new OfferedService(1, "name1", 40);
        var dateTime = LocalDateTime.of(2030, 10, 10, 10, 10);

        when(clientService.findById(1)).thenReturn(client);
        when(professionalService.findById(1)).thenReturn(professional);
        when(offeredServiceService.findById(1)).thenReturn(offeredService);
        when(appointmentRepository.existsByProfessionalAndDateTime(professional, dateTime)).thenReturn(false);
        when(appointmentRepository.existsByClientAndDateTime(client, dateTime)).thenReturn(false);

        var appointment = appointmentService.createAppointmentWithId(1, 1, 1, dateTime);

        assertEquals(client, appointment.getClient());
        assertEquals(professional, appointment.getProfessional());
        assertEquals(offeredService, appointment.getOfferedService());
        verify(appointmentRepository).save(appointment);
    }

    @Test
    void shouldFindAll(){
        var client = new Client(1, "Iaacob", "Hambra", "iaacob@email.com");
        var professional = new Professional(1, "name1", "surname1", "email1", "speciality1");
        var offeredService = new OfferedService(1, "name1", 40);
        var dateTime = LocalDateTime.of(2030, 10, 10, 10, 10);
        var a1 = new Appointment(offeredService, professional, client, dateTime);
        var a2 = new Appointment(offeredService, professional, client, dateTime);

        when(appointmentRepository.findAll()).thenReturn(List.of(a1,a2));
        var appointments = appointmentService.findAll();

        assertEquals(2, appointments.size());
        assertTrue(appointments.contains(a1));
        assertTrue(appointments.contains(a2));

    }

    @Test
    void findById(){
        var client = new Client(1, "Iaacob", "Hambra", "iaacob@email.com");
        var professional = new Professional(1, "name1", "surname1", "email1", "speciality1");
        var offeredService = new OfferedService(1, "name1", 40);
        var dateTime = LocalDateTime.of(2030, 10, 10, 10, 10);
        var a1 = new Appointment(1,offeredService, professional, client, dateTime, AppointmentStatus.SCHEDULED);

        when(appointmentRepository.findById(1)).thenReturn(Optional.of(a1));
        var appointmentFound = appointmentService.findById(1);

        assertEquals(a1, appointmentFound);
    }

    @Test
    void shouldThrowWhenAppointmentDoesntExists(){
        when(appointmentRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> appointmentService.findById(999));

    }

}
