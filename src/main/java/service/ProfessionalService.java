package service;

import exception.EntityNotFoundException;
import model.Professional;
import repository.ProfessionalRepository;

import java.util.List;

public class ProfessionalService {

    private final ProfessionalRepository professionalRepository;

    public ProfessionalService(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    public void register(String name, String lastname, String email, String speciality){
        Professional professional = new Professional(name, lastname, email, speciality);
        professionalRepository.save(professional);
    }

    public Professional findById(int id){
        return professionalRepository.findById(id)
                .orElseThrow(()->
                        new EntityNotFoundException("no professional found with id "+id));
    }

    public List<Professional> findAll(){
        return professionalRepository.findAll();
    }
}
