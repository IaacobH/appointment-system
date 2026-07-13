package repository;

import model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class AppointmentRepository {

    public final List<Appointment> appointments = new ArrayList<>();

    public void save(Appointment appointment){
        appointments.add(appointment);
    }

    public List<Appointment> findAll(){
        return appointments;
    }
}
