package service;

import exception.EntityNotFoundException;
import model.OfferedService;
import repository.OfferedServiceRepository;

import java.util.List;

public class OfferedServiceService {

    private final OfferedServiceRepository offeredServiceRepository;

    public OfferedServiceService(OfferedServiceRepository offeredServiceRepository) {
        this.offeredServiceRepository = offeredServiceRepository;
    }


    public OfferedService register(String serviceName, double price){
        OfferedService offeredService = new OfferedService(serviceName, price);
        return offeredServiceRepository.save(offeredService);
    }

    public OfferedService findById(int id) {
        return offeredServiceRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("no service found with id: "+id));
    }

    public List<OfferedService> findAll(){
        return offeredServiceRepository.findAll();
    }

    public void updateOfferedService(int offeredServiceId, String newName, double newPrice){
        offeredServiceRepository.updateOfferedService(offeredServiceId, newName, newPrice);
    }

    public void deleteOfferedService(int id){
        offeredServiceRepository.deleteOfferedService(id);
    }
}
