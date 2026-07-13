package model;

import java.time.LocalDateTime;

public class Appointment {
    private OfferedService offeredService;
    private Professional professional;
    private Client client;
    private LocalDateTime dateTime;
    private AppointmentStatus status;

    public Appointment(OfferedService offeredService, Professional professional, Client client, LocalDateTime dateTime) {
        this.offeredService = offeredService;
        this.professional = professional;
        this.client = client;
        this.dateTime = dateTime;
        this.status = AppointmentStatus.SCHEDULED;

    }

    public void cancel() {
        this.status = AppointmentStatus.CANCELLED;
    }

    public void complete() {
        this.status = AppointmentStatus.COMPLETED;
    }

    public void reprogram(){
        this.status = AppointmentStatus.SCHEDULED;
    }

    public AppointmentStatus getStatus() {
        return status;
    }


    @Override
    public String toString() {
        return "Appointment {\n" +
                "offeredService: " + offeredService + "\n" +
                "professional: " + professional + "\n" +
                "client: " + client + "\n" +
                "dateTime: " + dateTime + "\n" +
                "status: " + status + "\n" +
                "}";
    }
}
