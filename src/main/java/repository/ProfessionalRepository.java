package repository;

import model.Professional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Optional<Professional> findById(int id){
        for (Professional p : professionals){
            if (p.getId() == id){
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }
}
