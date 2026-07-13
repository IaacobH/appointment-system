package repository;

import model.Client;
import model.Service;

import java.util.ArrayList;
import java.util.List;

public class ClientRepository {

    private final List<Client> clients = new ArrayList<>();
    private long nextId = 1;

    public Client save(Client client) {
        client.setId(nextId);
        nextId++;


        clients.add(client);
        return client;

    }

    public List<Client> findAll() {
        return clients;
    }

    public Client findById(long id){
        for (Client c : clients){
            if (c.getId() == id){
                return c;
            }
        }
        return null;
    }

}
