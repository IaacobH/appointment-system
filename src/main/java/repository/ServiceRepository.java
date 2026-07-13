package repository;

import model.OfferedService;

import java.util.ArrayList;
import java.util.List;

public class ServiceRepository {


    private final List<OfferedService> services = new ArrayList<>();

    public void save(OfferedService service){
        services.add(service);
    }

    public List<OfferedService> findAll(){
        return services;
    }
}
