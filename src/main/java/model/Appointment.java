package model;

import java.time.LocalDateTime;

public class Appointment {
    private long id;
    private Service offeredService;
    private Professional professional;
    private Client client;
    private LocalDateTime dateTime;
    private AppointmentStatus status;

    public Appointment(Service offeredService, Professional professional, Client client, LocalDateTime dateTime) {
        this.offeredService = offeredService;
        this.professional = professional;
        this.client = client;
        this.dateTime = dateTime;
        this.status = AppointmentStatus.SCHEDULED;

    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void cancel() {
        this.status = AppointmentStatus.CANCELLED;
    }

    public void complete() {
        if (status == AppointmentStatus.CANCELLED) {
            System.out.println("A cancelled appointment cannot be completed.");
            return;
        }
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
