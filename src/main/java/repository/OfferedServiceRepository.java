package repository;

import database.DatabaseConnection;
import exception.EntityNotFoundException;
import model.OfferedService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OfferedServiceRepository {

    public OfferedService save(OfferedService offeredService){
        try(Connection connection = DatabaseConnection.getConnection()){
            String sql = """
                    INSERT INTO offered_services(service_name, price)
                    VALUES(?,?);
                    """;
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, offeredService.getServiceName());
            ps.setDouble(2, offeredService.getPrice());

            ps.executeUpdate();
            ResultSet key = ps.getGeneratedKeys();
            if(key.next()){
                offeredService.setId(key.getInt(1));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return offeredService;
    }

    public List<OfferedService> findAll(){
        List<OfferedService> offeredServices = new ArrayList<>();
        try(Connection connection = DatabaseConnection.getConnection()){
            String sql = """
                    SELECT * FROM offered_services;
                    """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                var offeredService = new OfferedService(
                        rs.getInt("offered_service_id"),
                        rs.getString("service_name"),
                        rs.getDouble("price")
                );
                offeredServices.add(offeredService);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return offeredServices;
    }

    public Optional<OfferedService> findById(int id){
        try(Connection connection = DatabaseConnection.getConnection()){
            String sql = """
                    SELECT * FROM offered_services WHERE offered_service_id = ?;
                    """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                var offeredService = new OfferedService(
                        rs.getInt("offered_service_id"),
                        rs.getString("service_name"),
                        rs.getDouble("price")
                );
                return Optional.of(offeredService);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    public void updateOfferedService(int offeredServiceId, String newName, double newPrice){
            try(Connection connection = DatabaseConnection.getConnection()){
                String sql = """
                    UPDATE offered_services SET 
                        service_name = ?,
                        price = ?
                    WHERE offered_service_id = ?;
                    """;
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1,newName);
                ps.setDouble(2,newPrice);
                ps.setInt(3,offeredServiceId);
                int rowsAffected = ps.executeUpdate();
                if(rowsAffected==0){
                    throw new EntityNotFoundException("offered service to update not found with id: "+offeredServiceId);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

    public void deleteOfferedService(int offeredServiceId) {
        try(Connection connection = DatabaseConnection.getConnection()){
            String sql = """
                    DELETE FROM clients WHERE client_id = ?;
                    """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,offeredServiceId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0){
                throw new EntityNotFoundException("offered service to delete not found with id: "+offeredServiceId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
