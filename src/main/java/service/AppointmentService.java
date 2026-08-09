package service;

import exception.EntityNotFoundException;
import exception.InvalidAppointmentException;
import exception.ScheduleConflictException;
import model.Appointment;
import model.Client;
import model.OfferedService;
import model.Professional;
import repository.AppointmentRepository;
import repository.ProfessionalRepository;

import java.time.LocalDateTime;
import java.util.List;

import static model.AppointmentStatus.CANCELLED;
import static model.AppointmentStatus.COMPLETED;

public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ProfessionalService professionalService;
    private final OfferedServiceService offeredServiceService;
    private final ClientService clientService;

    public AppointmentService(AppointmentRepository appointmentRepository, ClientService clientService, ProfessionalService professionalService, OfferedServiceService offeredServiceService) {
        this.clientService = clientService;
        this.professionalService = professionalService;
        this.offeredServiceService = offeredServiceService;
        this.appointmentRepository = appointmentRepository;    }

    public Appointment createAppointment(OfferedService offeredService, Professional professional, Client client, LocalDateTime dateTime){
        var appointment = new Appointment(offeredService, professional, client, dateTime);
        appointmentRepository.save(appointment);
        return appointment;
    }
    

    public Appointment createAppointmentWithId(int clientId, int professionalId,int offeredServiceId, LocalDateTime dateTime){
        var client = clientService.findById(clientId);
        var professional = professionalService.findById(professionalId);
        var offeredService = offeredServiceService.findById(offeredServiceId);

        var professionalOccupied= appointmentRepository.existsByProfessionalAndDateTime(professional, dateTime);
        if(professionalOccupied){
            throw new ScheduleConflictException("El profesional ya tiene un turno fijado a esa hora");
        }

        var clientOccupied = appointmentRepository.existsByClientAndDateTime(client, dateTime);
        if (clientOccupied) {
            throw new ScheduleConflictException("El cliente ya tiene un turno fijado a esa hora");
        }

        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new InvalidAppointmentException(
                    "Appointment date cannot be in the past"
            );
        }

        return createAppointment(offeredService, professional, client, dateTime);
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public Appointment findById(int id) {
        return appointmentRepository.findById(id)
                .orElseThrow(()->
                        new EntityNotFoundException("no appointment found with id: "+id));
    }

    public void cancelAppointment(Appointment appointment){
        if(appointment.getStatus() == CANCELLED){
            throw new ScheduleConflictException("the appointment is already cancelled");
        }
        appointmentRepository.updateAppointment(
                appointment.getId(),
                appointment.getProfessional().getId(),
                appointment.getClient().getId(),
                appointment.getOfferedService().getId(),
                appointment.getDateTime(),
                3
        );
    }

    public void completeAppointment(Appointment appointment){
        if(appointment.getStatus() == COMPLETED){
            throw new ScheduleConflictException("the appointment is already cancelled");
        }
        if(appointment.getStatus() == CANCELLED){
            throw new ScheduleConflictException("cannot complete a cancelled appointment");
        }
        appointmentRepository.updateAppointment(
                appointment.getId(),
                appointment.getProfessional().getId(),
                appointment.getClient().getId(),
                appointment.getOfferedService().getId(),
                appointment.getDateTime(),
                2
        );
    }

    public void updateAppointment(int appointmentId, int newProfessionalId, int newClientId,
                                  int newOfferedServiceId, LocalDateTime newDateTime, int newStatusId){
        appointmentRepository.updateAppointment(appointmentId, newProfessionalId, newClientId, newOfferedServiceId,
                newDateTime, newStatusId);
    }
}
