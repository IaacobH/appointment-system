package service;

import model.Appointment;
import model.Client;
import model.OfferedService;
import model.Professional;
import repository.AppointmentRepository;
import repository.ClientRepository;
import repository.OfferedServiceRepository;
import repository.ProfessionalRepository;
import ui.App;

import java.time.LocalDateTime;
import java.util.List;

public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ProfessionalRepository professionalRepository;
    private final OfferedServiceRepository offeredServiceRepository;
    private final ClientRepository clientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, ClientRepository clientRepository, ProfessionalRepository professionalRepository, OfferedServiceRepository offeredServiceRepository) {
        this.clientRepository = clientRepository;
        this.professionalRepository = professionalRepository;
        this.offeredServiceRepository = offeredServiceRepository;
        this.appointmentRepository = appointmentRepository;    }

    public Appointment createAppointment(OfferedService offeredService, Professional professional, Client client, LocalDateTime dateTime){
        var appointment = new Appointment(offeredService, professional, client, dateTime);
        appointmentRepository.save(appointment);
        return appointment;
    }


    public Appointment createAppointmentWithId(int clientId, int professionalId,int offeredServiceId, LocalDateTime dateTime){
        var client = clientRepository.findById(clientId);
        if (client == null){
            throw new IllegalArgumentException("Client not found with id: "+clientId);
        }
        var professional = professionalRepository.findById(professionalId);
        if(professional == null){
            throw new IllegalArgumentException("Professional not found with id: "+professionalId);
        }
        var offeredService = offeredServiceRepository.findById(offeredServiceId);
        if(offeredService == null){
            throw new IllegalArgumentException("Service not found with id: "+offeredServiceId);
        }

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
        return appointmentRepository.findById(id);
    }
}
