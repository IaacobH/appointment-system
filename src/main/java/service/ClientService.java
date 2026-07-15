package service;

import model.Client;
import repository.ClientRepository;

import java.util.List;

public class ClientService {

    private final ClientRepository clientRepository;


    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public Client register(String name, String lastname, String email){
        Client client = new Client(name, lastname, email);
        clientRepository.save(client);
        return client;
    }

    public Client findById(int id){
        return clientRepository.findById(id);
    }

    public List<Client> findAll(){
        return clientRepository.findAll();
    }
}
