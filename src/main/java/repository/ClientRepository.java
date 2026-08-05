package repository;

import database.DatabaseConnection;
import exception.EntityNotFoundException;
import model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepository {

    public Client save(Client client) {
        try(Connection connection = DatabaseConnection.getConnection();
        ) {
            String sql = """
                    INSERT INTO clients(name, lastname, email)
                    VALUES(?,?,?);
                    """;
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, client.getName());
            ps.setString(2, client.getLastname());
            ps.setString(3,client.getEmail());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();

            if(keys.next()){
                client.setId(keys.getInt(1));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return client;

    }

    public List<Client> findAll() {

        List<Client> clients = new ArrayList<>();

        try(Connection connection = DatabaseConnection.getConnection()){
            String sql = """
                    SELECT * FROM clients
                    """;

            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int clientId = rs.getInt("client_id");
                String name = rs.getString("name");
                String lastname = rs.getString("lastname");
                String email = rs.getString("email");

                Client client = new Client(
                        clientId,
                        name,
                        lastname,
                        email
                );

                clients.add(client);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return clients;
    }

    public Optional<Client> findById(int id){

        try(Connection connection = DatabaseConnection.getConnection()){
            String sql = """
                    SELECT * FROM clients WHERE client_id = ?;
                    """;

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                int clientId = rs.getInt("client_id");
                String name = rs.getString("name");
                String lastname = rs.getString("lastname");
                String email = rs.getString("email");

                Client client = new Client(
                        clientId,
                        name,
                        lastname,
                        email
                );

                return Optional.of(client);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public void updateClient(int clientId, String newName, String newLastname, String newEmail) {
        try(Connection connection = DatabaseConnection.getConnection()){
            String sql = """
                    UPDATE clients SET 
                        name = ?,
                        lastname = ?,
                        email = ?
                    WHERE client_id = ?;
                    """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,newName);
            ps.setString(2,newLastname);
            ps.setString(3,newEmail);
            ps.setInt(4, clientId);
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected==0){
                throw new EntityNotFoundException("client to update not found with id: "+clientId);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteClient(int clientId) {
        try(Connection connection = DatabaseConnection.getConnection()){
            String sql = """
                    DELETE FROM clients WHERE client_id = ?;
                    """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,clientId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0){
                throw new EntityNotFoundException("client to delete not found with id: "+clientId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
