package repository;

import model.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceRepository {


    private final List<Service> services = new ArrayList<>();
    private long nextId = 1;

    public Service save(Service service){
        service.setId(nextId);
        nextId++;
        services.add(service);
        return service;
    }

    public List<Service> findAll(){
        return services;
    }

    public Service findById(long id){
        for (Service s : services){
            if (s.getId() == id){
                return s;
            }
        }
        return null;
    }
}
