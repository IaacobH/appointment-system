package repository;

import model.Appointment;
import model.Client;
import model.Professional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository {

    public final List<Appointment> appointments = new ArrayList<>();
private int nextId = 1;

    public void save(Appointment appointment){
        appointment.setId(nextId);
        nextId++;
        appointments.add(appointment);
    }

    public boolean existsByProfessionalAndDateTime(Professional professional, LocalDateTime dateTime) {
        return appointments.stream()
                .anyMatch(appointment ->
                        appointment.getProfessional().equals(professional)
                                && appointment.getDateTime().equals(dateTime)
                );
    }

    public boolean existsByClientAndDateTime(Client client, LocalDateTime dateTime){
        return appointments.stream()
                .anyMatch(appointment ->
                        appointment.getClient().equals(client)
                            && appointment.getDateTime().equals(dateTime)
                );
    }

    public List<Appointment> findAll(){
        return appointments;
    }

    public Appointment findById(int id){
        for (Appointment a : appointments){
            if (a.getId() == id){
                return a;
            }
        }
        return null;
    }
}
