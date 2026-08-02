package repository;

import model.Appointment;
import model.AppointmentStatus;
import model.Client;
import model.Professional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AppointmentRepository {

    public final List<Appointment> appointments = new ArrayList<>();
    private int nextId = 1;

    public void save(Appointment appointment){
        try(Connection connection = DatabaseConnection.getConnection()){
            String sql = """
                    INSERT INTO appointments(professional_id, client_id, offered_service_id, datetime)
                    VALUES(?,?,?,?)
                    """;
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            var professionalId = appointment.getProfessional().getId();
            var clientId = appointment.getClient().getId();
            var  offeredServiceId = appointment.getOfferedService().getId();
            var datetime = appointment.getDateTime();
            ps.setInt(1, professionalId);
            ps.setInt(2, clientId);
            ps.setInt(3, offeredServiceId);
            ps.setTimestamp(4, Timestamp.valueOf(datetime));

            ps.executeUpdate();
            ResultSet key = ps.getGeneratedKeys();
            if (key.next()){
                appointment.setId(key.getInt(1));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existsByProfessionalAndDateTime(Professional professional, LocalDateTime dateTime) {
        return appointments.stream()
                .anyMatch(appointment ->
                        appointment.getProfessional().equals(professional)
                                && appointment.getDateTime().equals(dateTime)
                                && appointment.getStatus() == AppointmentStatus.SCHEDULED
                );
    }

    public boolean existsByClientAndDateTime(Client client, LocalDateTime dateTime) {
        return appointments.stream()
                .anyMatch(appointment ->
                        appointment.getClient().equals(client)
                                && appointment.getDateTime().equals(dateTime)
                                && appointment.getStatus() == AppointmentStatus.SCHEDULED
                );
    }

    public List<Appointment> findAll(){
        return appointments;
    }

    public Optional<Appointment> findById(int id){
        for (Appointment a : appointments){
            if (a.getId() == id){
                return Optional.of(a);
            }
        }
        return Optional.empty();
    }
}
