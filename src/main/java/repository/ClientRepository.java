package repository;

import model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientRepository {

    private final List<Client> clients = new ArrayList<>();

    public void save(Client client) {
        clients.add(client);
    }

    public List<Client> findAll() {
        return clients;
    }

}
