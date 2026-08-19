package service;

import exception.EntityNotFoundException;
import exception.ScheduleConflictException;
import model.*;
import org.junit.jupiter.api.BeforeEach;
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

    Client client;
    Professional professional;
    OfferedService offeredService;
    LocalDateTime dateTime;

    @BeforeEach
    void setUp() {
        client = new Client(1, "Iaacob", "Hambra", "iaacob@email.com");
        professional = new Professional(1, "name1", "surname1", "email1", "speciality1");
        offeredService = new OfferedService(1, "name1", 40);
        dateTime = LocalDateTime.of(2030, 10, 10, 10, 10);
    }

    @Test
    void shouldCreateAppointment() {
        var appointment = appointmentService.createAppointment(offeredService, professional, client, dateTime);

        assertEquals(offeredService, appointment.getOfferedService());
        assertEquals(professional, appointment.getProfessional());
        assertEquals(client, appointment.getClient());
        assertEquals(dateTime, appointment.getDateTime());
        verify(appointmentRepository).save(appointment);
    }

    @Test
    void shouldCreateAppointmentWithId_whenNoConflicts() {

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

    @Test
    void shouldCompleteAppointment(){
        var a1 = new Appointment(1,offeredService, professional, client, dateTime, AppointmentStatus.SCHEDULED);

        appointmentService.completeAppointment(a1);
        verify(appointmentRepository).updateAppointment(
                1,
                professional.getId()
                ,client.getId(),
                offeredService.getId(),
                dateTime,
                2
        );
    }

    @Test
    void shouldThrowWhenAlreadyCompleted(){
        var a1 = new Appointment(1,offeredService, professional, client, dateTime, AppointmentStatus.COMPLETED);

        assertThrows(ScheduleConflictException.class,
                () -> appointmentService.completeAppointment(a1));
        verify(appointmentRepository, never()).updateAppointment(anyInt(),
                anyInt(), anyInt(), anyInt(), any(), anyInt());
    }

    @Test
    void shouldThrowWhenCancelledCannotBeCompleted(){
        var a1 = new Appointment(1,offeredService, professional, client, dateTime, AppointmentStatus.CANCELLED);

        assertThrows(ScheduleConflictException.class,
                () -> appointmentService.completeAppointment(a1));
        verify(appointmentRepository, never()).updateAppointment(anyInt(),
                anyInt(), anyInt(), anyInt(), any(), anyInt());
    }

}
