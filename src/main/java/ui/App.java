package ui;

import exception.EntityNotFoundException;
import exception.InvalidAppointmentException;
import exception.ScheduleConflictException;
import model.Appointment;
import model.Client;
import model.OfferedService;
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

        boolean exit = false;

        while (!exit) {
            showMainMenu();
            int option = InputUtils.getInt(input, "Choose an option: ");

            switch (option) {
                case 1 -> clientsMenu(input, clientService);
                case 2 -> professionalsMenu(input, professionalService);
                case 3 -> offeredServicesMenu(input, offeredServiceService);
                case 4 -> appointmentsMenu(input, appointmentService, clientService, professionalService, offeredServiceService);
                case 5 -> {
                    exit = true;
                    System.out.println("Goodbye!");
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void clientsMenu(Scanner input, ClientService clientService) {
        boolean back = false;

        while (!back) {
            showClientsMenu();
            int option = InputUtils.getInt(input, "Choose an option: ");

            switch (option) {
                case 1 -> handleRegisterClient(input, clientService);
                case 2 -> handleUpdateClient(input, clientService);
                case 3 -> handleDeleteClient(input, clientService);
                case 4 -> showAllClients(clientService);
                case 5 -> searchClientById(input, clientService);
                case 6 -> back = true;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void professionalsMenu(Scanner input, ProfessionalService professionalService) {
        boolean back = false;

        while (!back) {
            showProfessionalsMenu();
            int option = InputUtils.getInt(input, "Choose an option: ");

            switch (option) {
                case 1 -> registerProfessional(input, professionalService);
                case 2 -> updateProfessional(input, professionalService);
                case 3 -> deleteProfessional(input, professionalService);
                case 4 -> showAllProfessionals(professionalService);
                case 5 -> searchProfessionalById(input, professionalService);
                case 6 -> back = true;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void offeredServicesMenu(Scanner input, OfferedServiceService offeredServiceService) {
        boolean back = false;

        while (!back) {
            showOfferedServicesMenu();
            int option = InputUtils.getInt(input, "Choose an option: ");

            switch (option) {
                case 1 -> registerOfferedService(input, offeredServiceService);
                case 2 -> updateOfferedService(input, offeredServiceService);
                case 3 -> deleteOfferedService(input, offeredServiceService);
                case 4 -> showAllOfferedServices(offeredServiceService);
                case 5 -> searchOfferedServiceById(input, offeredServiceService);
                case 6 -> back = true;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void appointmentsMenu(Scanner input, AppointmentService appointmentService, ClientService clientService,
                                    ProfessionalService professionalService, OfferedServiceService offeredServiceService) {
        boolean back = false;

        while (!back) {
            showAppointmentsMenu();
            int option = InputUtils.getInt(input, "Choose an option: ");

            switch (option) {
                case 1 -> createAppointment(input, clientService, professionalService, offeredServiceService, appointmentService);
                case 2 -> updateAppointment(input, appointmentService);
                case 3 -> cancelAppointment(input, appointmentService);
                case 4 -> completeAppointment(input, appointmentService);
                case 5 -> showAllAppointments(appointmentService);
                case 6 -> searchAppointmentById(input, appointmentService);
                case 7 -> back = true;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void handleRegisterClient(Scanner input, ClientService clientService) {
        String name = InputUtils.getNonEmptyString(input, "name: ");
        String lastname = InputUtils.getNonEmptyString(input, "lastname: ");
        String email = InputUtils.getNonEmptyString(input, "email: ");
        clientService.register(name, lastname, email);
    }

    private void handleUpdateClient(Scanner input, ClientService clientService){
        System.out.println("update client");
        var clientId = InputUtils.getInt(input, "Enter client id: ");
        var newName = InputUtils.getString(input, "Enter the new name: ");
        var newLastname = InputUtils.getString(input, "Enter the new name: ");
        var newEmail = InputUtils.getString(input, "Enter the new name: ");
        clientService.updateClient(clientId, newName, newLastname, newEmail);
    }

    private void handleDeleteClient(Scanner input, ClientService clientService){
        System.out.println("delete client");
        var clientId = InputUtils.getInt(input, "Enter client id: ");
        try {
            clientService.deleteClient(clientId);
            System.out.println("client deleted successfully");
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());;
        }
    }

    private void showAllClients(ClientService clientService){
        var clients = clientService.findAll();
        for (Client c : clients){
            System.out.println(c);
        }
    }

    private void searchClientById(Scanner input, ClientService clientService){
        var clientId = InputUtils.getInt(input, "Search client. Enter client id: ");
        try {
            var client = clientService.findById(clientId);
            System.out.println(client);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void registerProfessional(Scanner input, ProfessionalService professionalService) {
        String name = InputUtils.getNonEmptyString(input, "name: ");
        String lastname = InputUtils.getNonEmptyString(input, "lastname: ");
        String email = InputUtils.getNonEmptyString(input, "email: ");
        String speciality = InputUtils.getNonEmptyString(input, "speciality: ");
        professionalService.register(name, lastname, email, speciality);
    }

    private void updateProfessional(Scanner input, ProfessionalService professionalService){
        System.out.println("update professional");
        var professionalId = InputUtils.getInt(input, "Enter professional id: ");
        var newName = InputUtils.getString(input, "Enter the new name: ");
        var newLastname = InputUtils.getString(input, "Enter the new lastname: ");
        var newEmail = InputUtils.getString(input, "Enter the new email: ");
        var newSpeciality = InputUtils.getString(input, "Enter the new speciality: ");
        professionalService.updateProfessional(professionalId, newName, newLastname, newEmail, newSpeciality);
    }

    private void deleteProfessional(Scanner input, ProfessionalService professionalService){
        System.out.println("delete professional");
        var professionalId = InputUtils.getInt(input, "Enter professional id: ");
        professionalService.deleteProfessional(professionalId);
    }

    private void showAllProfessionals(ProfessionalService professionalService){
        var professionals = professionalService.findAll();
        for(Professional p : professionals){
            System.out.println(p);
        }
    }

    private void searchProfessionalById(Scanner input, ProfessionalService professionalService){
        System.out.println("search professional");
        var professionalId = InputUtils.getInt(input, "Enter professional id: ");
        System.out.println(professionalService.findById(professionalId));
    }

    private void registerOfferedService(Scanner input, OfferedServiceService offeredServiceService) {
        String name = InputUtils.getNonEmptyString(input, "name: ");
        double price = InputUtils.getDouble(input, "price: ");
        offeredServiceService.register(name, price);
    }

    private void updateOfferedService(Scanner input, OfferedServiceService offeredServiceService){
        System.out.println("update offeredService");
        var offeredServiceId = InputUtils.getInt(input, "Enter offered Service id: ");
        var newName = InputUtils.getString(input, "Enter the new name: ");
        var newPrice = InputUtils.getDouble(input, "Enter the new price: ");
        offeredServiceService.updateOfferedService(offeredServiceId, newName, newPrice);
    }

    private void deleteOfferedService(Scanner input, OfferedServiceService offeredServiceService){
        System.out.println("delete offeredService");
        var offeredServiceId = InputUtils.getInt(input, "Enter offeredService id: ");
        offeredServiceService.deleteOfferedService(offeredServiceId);
    }

    private void showAllOfferedServices(OfferedServiceService offeredServiceService){
        var offeredServices = offeredServiceService.findAll();
        for(OfferedService o : offeredServices){
            System.out.println(o);
        }
    }

    private void searchOfferedServiceById(Scanner input, OfferedServiceService offeredServiceService){
        System.out.println("search offeredService");
        var offeredServiceId = InputUtils.getInt(input, "Enter offeredService id: ");
        System.out.println(offeredServiceService.findById(offeredServiceId));
    }

    private void createAppointment(Scanner input, ClientService clientService, ProfessionalService professionalService,
                                         OfferedServiceService offeredServiceService, AppointmentService appointmentService) {
        System.out.println("create appointment");
        System.out.println(clientService.findAll());
        var clientId = InputUtils.getInt(input, "Enter client id: ");

        System.out.println(professionalService.findAll());
        var professionalId = InputUtils.getInt(input, "enter professional id: ");

        System.out.println(offeredServiceService.findAll());
        var offeredServiceId = InputUtils.getInt(input, "enter service id: ");

        var dateTime = InputUtils.getLocalDateTime(input);

        try {
            var appointment = appointmentService.createAppointmentWithId(clientId, professionalId, offeredServiceId, dateTime);
            System.out.println(appointment);
            System.out.println("Appointment created successfully.");
        } catch (EntityNotFoundException | InvalidAppointmentException | ScheduleConflictException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateAppointment(Scanner input, AppointmentService appointmentService){
        System.out.println("update appointment");
        var appointmentId = InputUtils.getInt(input, "Enter appointment id: ");
        var professionalId = InputUtils.getInt(input, "enter new professional id: ");
        var clientId = InputUtils.getInt(input, "Enter new client id: ");
        var offeredServiceId = InputUtils.getInt(input, "enter new service id: ");
        var dateTime = InputUtils.getLocalDateTime(input);
        System.out.println("1. Scheduled");
        System.out.println("2. completed");
        System.out.println("3. canceled");
        int newStatusId = InputUtils.getInt(input, "enter new status (1-3): ");
        appointmentService.updateAppointment(appointmentId, professionalId, clientId, offeredServiceId, dateTime, newStatusId);
    }

    private void showAllAppointments(AppointmentService appointmentService) {
        var appointments = appointmentService.findAll();
        for(Appointment a : appointments){
            System.out.println(a);
        }
    }

    private void cancelAppointment(Scanner input, AppointmentService appointmentService) {
        int id = InputUtils.getInt(input, "id: ");
        try{
            var appointment = appointmentService.findById(id);
            appointmentService.cancelAppointment(appointment);
        } catch (EntityNotFoundException | ScheduleConflictException e) {
            System.out.println(e.getMessage());
        }
    }

    private void completeAppointment(Scanner input, AppointmentService appointmentService) {
        int id = InputUtils.getInt(input, "id: ");
        try{
            var appointment = appointmentService.findById(id);
            appointmentService.completeAppointment(appointment);
        } catch (EntityNotFoundException | ScheduleConflictException e) {
            System.out.println(e.getMessage());
        }
    }

    private void searchAppointmentById(Scanner input, AppointmentService appointmentService){
        System.out.println("search appointment");
        var id = InputUtils.getInt(input, "Enter appointment id: ");
        var appointment = appointmentService.findById(id);
        System.out.println(appointment);
    }

    public void showMainMenu() {
        System.out.println();
        System.out.println("===== APPOINTMENT SYSTEM =====");
        System.out.println("1. Clients");
        System.out.println("2. Professionals");
        System.out.println("3. Offered Services");
        System.out.println("4. Appointments");
        System.out.println("5. Exit");
    }

    public void showClientsMenu(){
        System.out.println();
        System.out.println("===== CLIENTS =====");
        System.out.println("1. Register client");
        System.out.println("2. Update client");
        System.out.println("3. Delete client");
        System.out.println("4. Show all clients");
        System.out.println("5. Search client by ID");
        System.out.println("6. Back");
    }

    public void showProfessionalsMenu(){
        System.out.println();
        System.out.println("===== PROFESSIONALS =====");
        System.out.println("1. Register professional");
        System.out.println("2. Update professional");
        System.out.println("3. Delete professional");
        System.out.println("4. Show all professionals");
        System.out.println("5. Search professional by ID");
        System.out.println("6. Back");
    }

    public void showOfferedServicesMenu(){
        System.out.println();
        System.out.println("===== OFFERED SERVICES =====");
        System.out.println("1. Register offered service");
        System.out.println("2. Update offered service");
        System.out.println("3. Delete offered service");
        System.out.println("4. Show all offered services");
        System.out.println("5. Search offered service by ID");
        System.out.println("6. Back");
    }

    public void showAppointmentsMenu(){
        System.out.println();
        System.out.println("===== APPOINTMENTS =====");
        System.out.println("1. Create appointment");
        System.out.println("2. Update appointment");
        System.out.println("3. Cancel appointment");
        System.out.println("4. Complete appointment");
        System.out.println("5. Show all appointments");
        System.out.println("6. Search appointment by ID");
        System.out.println("7. Back");
    }
}
