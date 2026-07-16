package ui;

import model.Appointment;
import model.Professional;
import service.AppointmentService;
import service.ClientService;
import service.OfferedServiceService;
import service.ProfessionalService;

import java.time.LocalDateTime;
import java.util.Scanner;

public class App {


    public void run(Scanner input, ClientService clientService, ProfessionalService professionalService,
                    OfferedServiceService offeredServiceService, AppointmentService appointmentService) {
        int option;

        do {
            showMenu();
            option = InputUtils.getInt(input, "Choose an option: ");

            switch (option) {
                case 1 -> handleRegisterClient(input, clientService);
                case 2 -> handleRegisterProfessional(input, professionalService);
                case 3 -> handleRegisterOfferedService(input, offeredServiceService);
                case 4 -> handleCreateAppointment(input, clientService, professionalService,
                        offeredServiceService, appointmentService);
                case 5 -> handleShowAllAppointments(appointmentService);
                case 6 -> handleCancelAppointment(input, appointmentService);
                case 7 -> handleCompleteAppointment(input, appointmentService);
                case 8 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid option.");
            }

        } while (option != 8);
    }

    private void handleRegisterClient(Scanner input, ClientService clientService) {
        String name = InputUtils.getString(input, "name: ");
        String lastname = InputUtils.getString(input, "lastname: ");
        String email = InputUtils.getString(input, "email: ");
        clientService.register(name, lastname, email);
    }

    private void handleRegisterProfessional(Scanner input, ProfessionalService professionalService) {
        String name = InputUtils.getString(input, "name: ");
        String lastname = InputUtils.getString(input, "lastname: ");
        String email = InputUtils.getString(input, "email: ");
        String speciality = InputUtils.getString(input, "speciality: ");
        professionalService.register(name, lastname, email, speciality);
    }

    private void handleRegisterOfferedService(Scanner input, OfferedServiceService offeredServiceService) {
        String name = InputUtils.getString(input, "name: ");
        double price = InputUtils.getDouble(input, "price: ");
        offeredServiceService.register(name, price);
    }

    private void handleCreateAppointment(Scanner input, ClientService clientService, ProfessionalService professionalService,
                                         OfferedServiceService offeredServiceService, AppointmentService appointmentService) {
        System.out.println(clientService.findAll());
        var clientId = InputUtils.getInt(input, "Enter client id: ");

        System.out.println(professionalService.findAll());
        var professionalId = InputUtils.getInt(input, "enter professional id: ");

        System.out.println(offeredServiceService.findAll());
        var offeredServiceId = InputUtils.getInt(input, "enter service id: ");

        try {
            var appointment = appointmentService.createAppointmentWithId(clientId, professionalId, offeredServiceId, LocalDateTime.now());
            System.out.println(appointment);
            System.out.println("Appointment created successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleShowAllAppointments(AppointmentService appointmentService) {
        var appointments = appointmentService.findAll();
        for(Appointment a : appointments){
            System.out.println(a);
        }
    }

    private void handleCancelAppointment(Scanner input, AppointmentService appointmentService) {
        int id = InputUtils.getInt(input, "id: ");
        var appointment = appointmentService.findById(id);
        appointment.cancel();
    }

    private void handleCompleteAppointment(Scanner input, AppointmentService appointmentService) {
        int id = InputUtils.getInt(input, "id: ");
        var appointment = appointmentService.findById(id);
        appointment.complete();
    }

    public void showMenu() {
        System.out.println();
        System.out.println("===== APPOINTMENT SYSTEM =====");
        System.out.println("1. Register client");
        System.out.println("2. Register professional");
        System.out.println("3. Register offered service");
        System.out.println("4. Create appointment");
        System.out.println("5. Show all appointments");
        System.out.println("6. Cancel appointment");
        System.out.println("7. Complete appointment");
        System.out.println("8. Exit");
    }
}
