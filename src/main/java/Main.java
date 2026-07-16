import repository.AppointmentRepository;
import repository.ClientRepository;
import repository.ProfessionalRepository;
import repository.OfferedServiceRepository;
import service.AppointmentService;
import service.ClientService;
import service.OfferedServiceService;
import service.ProfessionalService;
import ui.App;
import ui.InputUtils;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        var clientRepository = new ClientRepository();
        var professionalRepository = new ProfessionalRepository();
        var offeredServiceRepository = new OfferedServiceRepository();
        var appointmentRepository = new AppointmentRepository();

        var clientService = new ClientService(clientRepository);
        var professionalService = new ProfessionalService(professionalRepository);
        var offeredServiceService = new OfferedServiceService(offeredServiceRepository);
        var appointmentService = new AppointmentService(
                appointmentRepository, clientRepository, professionalRepository, offeredServiceRepository);


        clientService.register("iaacob", "hambra", "iaacobh@gmail.com");
        clientService.register("cliente2", "apellido2", "cli2@gmail.com");
        clientService.register("cliente3", "apellido3", "cli3@gmail.com");


        professionalService.register("carlos", "varela","carlosv@gmail.com","psiquiatra");
        professionalService.register("pepe", "pereyra","pepepe@gmail.com","masajista");

        offeredServiceService.register("consulta", 10);
        offeredServiceService.register("masaje", 30);


        var app = new App();
        app.run(input, clientService, professionalService, offeredServiceService, appointmentService);






    }
}
