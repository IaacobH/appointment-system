package repository;

import model.OfferedService;

import java.util.ArrayList;
import java.util.List;

public class OfferedServiceRepository {


    private final List<OfferedService> services = new ArrayList<>();
    private int nextId = 1;

    public OfferedService save(OfferedService service){
        service.setId(nextId);
        nextId++;
        services.add(service);
        return service;
    }

    public List<OfferedService> findAll(){
        return services;
    }

    public OfferedService findById(int id){
        for (OfferedService s : services){
            if (s.getId() == id){
                return s;
            }
        }
        return null;
    }
}
