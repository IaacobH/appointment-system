package model;

import java.time.LocalDateTime;

public class Appointment {
    private long id;
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

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Professional getProfessional() {
        return professional;
    }

    public Client getClient() {
        return client;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
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
        return "Appointment{" +
                "id=" + id +
                ", offeredService=" + offeredService +
                ", professional=" + professional +
                ", client=" + client +
                ", dateTime=" + dateTime +
                ", status=" + status +
                '}';
    }
}
