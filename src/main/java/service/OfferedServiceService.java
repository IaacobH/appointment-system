package service;

import model.OfferedService;
import repository.OfferedServiceRepository;

import java.util.List;

public class OfferedServiceService {

    private final OfferedServiceRepository offeredServiceRepository;

    public OfferedServiceService(OfferedServiceRepository offeredServiceRepository) {
        this.offeredServiceRepository = offeredServiceRepository;
    }


    public void register(String serviceName, double price){
        OfferedService offeredService = new OfferedService(serviceName, price);
        offeredServiceRepository.save(offeredService);
    }

    public OfferedService findById(int id) {
        return offeredServiceRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("no service found with id: "+id));
    }

    public List<OfferedService> findAll(){
        return offeredServiceRepository.findAll();
    }
}
