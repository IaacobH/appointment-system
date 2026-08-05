package repository;

import database.DatabaseConnection;
import exception.EntityNotFoundException;
import model.Professional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfessionalRepository {

    public Professional save(Professional professional) {

        try(Connection connection = DatabaseConnection.getConnection()){

            String sql = """
            INSERT INTO professionals(name, lastname, email, speciality)
            VALUES(?,?,?,?);
            """;

            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1,professional.getName());
            ps.setString(2,professional.getLastname());
            ps.setString(3,professional.getEmail());
            ps.setString(4,professional.getSpeciality());

            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();

            if(keys.next()){
                professional.setId(keys.getInt(1));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return professional;
    }

    public List<Professional> findAll() {

        List<Professional> professionals = new ArrayList<>();

        try(Connection connectin = DatabaseConnection.getConnection()){
            String sql = """
                    SELECT * FROM professionals;
                    """;
            PreparedStatement ps = connectin.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                var p = new Professional(
                        rs.getInt("professional_id"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("email"),
                        rs.getString("speciality"));
                professionals.add(p);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return professionals;
    }

    public Optional<Professional> findById(int id){

        try(Connection connection = DatabaseConnection.getConnection()){
            String sql = """
                    SELECT * FROM professionals
                    WHERE professional_id = ?;
                    """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                Professional profesional = new Professional(
                        rs.getInt("professional_id"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("email"),
                        rs.getString("speciality")
                );

                return Optional.of(profesional);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public void updateProfessional(int professionalId, String newName, String newLastname,
                                   String newEmail, String newSpeciality){
            try(Connection connection = DatabaseConnection.getConnection()){
                String sql = """
                    UPDATE professionals SET 
                        name = ?,
                        lastname = ?,
                        email = ?,
                        speciality
                    WHERE professional_id = ?;
                    """;
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1,newName);
                ps.setString(2,newLastname);
                ps.setString(3,newEmail);
                ps.setString(4,newSpeciality);
                ps.setInt(5, professionalId);
                int rowsAffected = ps.executeUpdate();
                if(rowsAffected==0){
                    throw new EntityNotFoundException("professional to update not found with id: "+professionalId);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

    }

    public void deleteProfessional(int professionalId) {
        try(Connection connection = DatabaseConnection.getConnection()){
            String sql = """
                    DELETE FROM clients WHERE client_id = ?;
                    """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,professionalId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0){
                throw new EntityNotFoundException("professional to delete not found with id: "+professionalId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
