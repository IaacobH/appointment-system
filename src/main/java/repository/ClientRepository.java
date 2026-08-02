package repository;

import database.DatabaseConnection;
import model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepository {

    private final List<Client> clients = new ArrayList<>();

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

}
