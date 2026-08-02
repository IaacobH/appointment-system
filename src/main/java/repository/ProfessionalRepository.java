package repository;

import database.DatabaseConnection;
import model.Professional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfessionalRepository {


    private final List<Professional> professionals = new ArrayList<>();
    private int nextId = 1;

    public Professional save(Professional professional) {

        try(Connection connection = DatabaseConnection.getConnection()){

            String sql = """
            INSERT INTO professionals(name, lastname, email, speciality)
            VALUES(?,?,?,?);
            """;

            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1,professional.getName());
            ps.setString(1,professional.getLastname());
            ps.setString(1,professional.getEmail());
            ps.setString(1,professional.getSpeciality());

            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();

            if(keys.next()){
                professional.setId(keys.getInt(1));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        professionals.add(professional);
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
}
