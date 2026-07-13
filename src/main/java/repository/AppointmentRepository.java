package repository;

import model.Appointment;
import model.Service;

import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository {

    public final List<Appointment> appointments = new ArrayList<>();
private long nextId = 1;

    public Appointment save(Appointment appointment){
        appointment.setId(nextId);
        nextId++;
        appointments.add(appointment);
        return appointment;
    }

    public List<Appointment> findAll(){
        return appointments;
    }

    public Appointment findById(long id){
        for (Appointment a : appointments){
            if (a.getId() == id){
                return a;
            }
        }
        return null;
    }
}
