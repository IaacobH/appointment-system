import model.Appointment;
import model.Client;
import model.OfferedService;
import model.Professional;
import repository.AppointmentRepository;
import repository.ClientRepository;
import repository.ProfessionalRepository;
import repository.ServiceRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main {

    static void main(String[] args) {
        ClientRepository clientRepository = new ClientRepository();
        ProfessionalRepository professionalRepository = new ProfessionalRepository();
        ServiceRepository serviceRepository = new ServiceRepository();
        AppointmentRepository appointmentRepository = new AppointmentRepository();

        var client1 = new Client("iaacob", "hambra", "iaacobh@gmail.com");
        var client2 = new Client("cliente2", "apellido2", "cli2@gmail.com");

        var professional1 = new Professional("carlos", "varela",
                                              "carlosv@gmail.com","psiquiatra");
        var offeredService1 = new OfferedService("consulta",0);
        var appointment1 = new Appointment(offeredService1,professional1,client1,LocalDateTime.now());


        clientRepository.save(client1);
        clientRepository.save(client2);
        professionalRepository.save(professional1);
        serviceRepository.save(offeredService1);
        appointmentRepository.save(appointment1);

        System.out.println(clientRepository.findAll());
        System.out.println(appointment1);


    }
}
