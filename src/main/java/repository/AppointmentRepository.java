package repository;

import database.DatabaseConnection;
import exception.EntityNotFoundException;
import model.*;
import service.OfferedServiceService;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AppointmentRepository {

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
        try(Connection connection = DatabaseConnection.getConnection()){
            String sql = """
            SELECT 1
            FROM appointments
            WHERE professional_id = ?
              AND datetime = ?
              AND status_id = 1
            LIMIT 1;
            """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,professional.getId());
            ps.setTimestamp(2, Timestamp.valueOf(dateTime));
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public boolean existsByClientAndDateTime(Client client, LocalDateTime dateTime) {
        try(Connection connection = DatabaseConnection.getConnection()){
            String sql = """
            SELECT 1
            FROM appointments
            WHERE client_id = ?
              AND datetime = ?
              AND status_id = 1
            LIMIT 1;
            """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,client.getId());
            ps.setTimestamp(2, Timestamp.valueOf(dateTime));
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Appointment> findAll(){
        List<Appointment> appointments = new ArrayList<>();
        try(Connection connection = DatabaseConnection.getConnection()){
            String sql = """
                    SELECT
                                    a.appointment_id,
                                    a.datetime AS appointment_datetime,
                                    s.appointment_status AS status_name,
                                
                                    os.offered_service_id,
                                    os.service_name AS offered_service_name,
                                    os.price,
                                
                                    p.professional_id,
                                    p.name AS professional_name,
                                    p.lastname AS professional_lastname,
                                    p.speciality,
                                    p.email AS professional_email,
                                
                                    c.client_id,
                                    c.name AS client_name,
                                    c.lastname AS client_lastname,
                                    c.email AS client_email
                                
                                FROM appointments a
                                
                                JOIN status s
                                    ON a.status_id = s.status_id
                                
                                JOIN offered_services os
                                    ON a.offered_service_id = os.offered_service_id
                                
                                JOIN professionals p
                                    ON a.professional_id = p.professional_id
                                
                                JOIN clients c
                                    ON a.client_id = c.client_id;                    
                    """;

            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                var offeredServiceId = rs.getInt("offered_service_id");
                var offeredServiceName = rs.getString("offered_service_name");
                var offeredServicePrice = rs.getDouble("price");
                var offeredService = new OfferedService(
                        offeredServiceId,
                        offeredServiceName,
                        offeredServicePrice
                );

                var professionalId = rs.getInt("professional_id");
                var professionalName = rs.getString("professional_name");
                var professionalLastName = rs.getString("professional_lastname");
                var speciality = rs.getString("speciality");
                var professionalEmail = rs.getString("professional_email");
                var professional = new Professional(
                        professionalId,
                        professionalName,
                        professionalLastName,
                        professionalEmail,
                        speciality
                );

                var clientId = rs.getInt("client_id");
                var clientName = rs.getString("client_name");
                var clientLastname = rs.getString("client_lastname");
                var clientEmail = rs.getString("client_email");
                var client = new Client(
                        clientId,
                        clientName,
                        clientLastname,
                        clientEmail
                );

                var appointmentId = rs.getInt("appointment_id");
                var datetime = rs.getTimestamp("appointment_datetime").toLocalDateTime();
                var status = AppointmentStatus.valueOf(rs.getString("status_name"));

                var appointment = new Appointment(
                        appointmentId,
                        offeredService,
                        professional,
                        client,
                        datetime,
                        status
                );

                appointments.add(appointment);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return appointments;
    }

    public Optional<Appointment> findById(int id){
        try(Connection connection = DatabaseConnection.getConnection()){
            String sql = """
                    SELECT
                                    a.appointment_id,
                                    a.datetime AS appointment_datetime,
                                    s.appointment_status AS status_name,
                                
                                    os.offered_service_id,
                                    os.service_name AS offered_service_name,
                                    os.price,
                                
                                    p.professional_id,
                                    p.name AS professional_name,
                                    p.lastname AS professional_lastname,
                                    p.speciality,
                                    p.email AS professional_email,
                                
                                    c.client_id,
                                    c.name AS client_name,
                                    c.lastname AS client_lastname,
                                    c.email AS client_email
                                
                                FROM appointments a
                                
                                JOIN status s
                                    ON a.status_id = s.status_id
                                
                                JOIN offered_services os
                                    ON a.offered_service_id = os.offered_service_id
                                
                                JOIN professionals p
                                    ON a.professional_id = p.professional_id
                                
                                JOIN clients c
                                    ON a.client_id = c.client_id
                    WHERE appointment_id = ?;
                    """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                var offeredServiceId = rs.getInt("offered_service_id");
                var offeredServiceName = rs.getString("offered_service_name");
                var offeredServicePrice = rs.getDouble("price");
                var offeredService = new OfferedService(
                        offeredServiceId,
                        offeredServiceName,
                        offeredServicePrice
                );

                var professionalId = rs.getInt("professional_id");
                var professionalName = rs.getString("professional_name");
                var professionalLastName = rs.getString("professional_lastname");
                var speciality = rs.getString("speciality");
                var professionalEmail = rs.getString("professional_email");
                var professional = new Professional(
                        professionalId,
                        professionalName,
                        professionalLastName,
                        professionalEmail,
                        speciality
                );

                var clientId = rs.getInt("client_id");
                var clientName = rs.getString("client_name");
                var clientLastname = rs.getString("client_lastname");
                var clientEmail = rs.getString("client_email");
                var client = new Client(
                        clientId,
                        clientName,
                        clientLastname,
                        clientEmail
                );

                var appointmentId = rs.getInt("appointment_id");
                var datetime = rs.getTimestamp("appointment_datetime").toLocalDateTime();
                var status = AppointmentStatus.valueOf(rs.getString("status_name"));

                var appointment = new Appointment(
                        appointmentId,
                        offeredService,
                        professional,
                        client,
                        datetime,
                        status
                );

                return Optional.of(appointment);

            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public void updateAppointment(Appointment appointment, int newProfessionalId, int newClientId,
                                         int newOfferedServiceId, LocalDateTime newDateTime, int newStatusId){
        try(Connection connection = DatabaseConnection.getConnection()){
            String sql = """
                UPDATE appointments
                SET professional_id = ?,
                	client_id = ?,
                	offered_service_id = ?,
                    datetime = ?,
                    status_id = ?
                WHERE appointment_id = ?;
                """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, newProfessionalId);
            ps.setInt(2, newClientId);
            ps.setInt(3, newOfferedServiceId);
            ps.setTimestamp(4,Timestamp.valueOf(newDateTime));
            ps.setInt(5,newStatusId);
            ps.setInt(6,appointment.getId());
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected == 0){
                throw new EntityNotFoundException("appointment to update not found");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
