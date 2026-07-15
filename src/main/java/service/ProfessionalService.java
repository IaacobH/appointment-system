package service;

import model.Professional;
import repository.ProfessionalRepository;

import java.util.List;

public class ProfessionalService {

    private final ProfessionalRepository professionalRepository;

    public ProfessionalService(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    public Professional register(String name, String lastname, String email, String speciality){
        Professional professional = new Professional(name, lastname, email, speciality);
        professionalRepository.save(professional);
        return professional;
    }

    public Professional findById(int id){
        return professionalRepository.findById(id);
    }

    public List<Professional> findAll(){
        return professionalRepository.findAll();
    }
}
