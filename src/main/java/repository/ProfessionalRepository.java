package repository;

import model.Client;
import model.Professional;
import model.Service;

import java.util.ArrayList;
import java.util.List;

public class ProfessionalRepository {


    private final List<Professional> professionals = new ArrayList<>();
    private long nextId = 1;

    public Professional save(Professional professional) {
        professional.setId(nextId);
        nextId++;
        professionals.add(professional);
        return professional;
    }

    public List<Professional> findAll() {
        return professionals;
    }

    public Professional findById(long id){
        for (Professional p : professionals){
            if (p.getId() == id){
                return p;
            }
        }
        return null;
    }
}
