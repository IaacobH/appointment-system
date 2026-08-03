import repository.AppointmentRepository;
import repository.ClientRepository;
import repository.ProfessionalRepository;
import repository.OfferedServiceRepository;
import service.AppointmentService;
import service.ClientService;
import service.OfferedServiceService;
import service.ProfessionalService;
import ui.App;

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
                appointmentRepository, clientService, professionalService, offeredServiceService);

        var app = new App();
        app.run(input, clientService, professionalService, offeredServiceService, appointmentService);

    }
}
