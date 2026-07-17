package service;

import model.Appointment;
import model.Client;
import model.OfferedService;
import model.Professional;
import repository.AppointmentRepository;
import repository.ProfessionalRepository;

import java.time.LocalDateTime;
import java.util.List;

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
            throw new IllegalArgumentException("El profesional ya tiene un turno fijado a esa hora");
        }

        var clientOccupied = appointmentRepository.existsByClientAndDateTime(client, dateTime);
        if (clientOccupied){
            throw new IllegalArgumentException("El cliente ya tiene un turno fijado a esa hora");
        }

        var appointment = createAppointment(offeredService, professional, client, dateTime);
        appointmentRepository.save(appointment);

        return appointment;
    }



    public List<Appointment> findAll(){
        return appointmentRepository.findAll();
    }

    public Appointment findById(int id){
        return appointmentRepository.findById(id)
                .orElseThrow(()->
                        new IllegalArgumentException("no appointment found with id: "+id));
    }
}
