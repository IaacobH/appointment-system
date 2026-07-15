package repository;

import model.Professional;

import java.util.ArrayList;
import java.util.List;

public class ProfessionalRepository {


    private final List<Professional> professionals = new ArrayList<>();
    private int nextId = 1;

    public Professional save(Professional professional) {
        professional.setId(nextId);
        nextId++;
        professionals.add(professional);
        return professional;
    }

    public List<Professional> findAll() {
        return professionals;
    }

    public Professional findById(int id){
        for (Professional p : professionals){
            if (p.getId() == id){
                return p;
            }
        }
        return null;
    }
}
