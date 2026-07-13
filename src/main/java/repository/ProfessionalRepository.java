package repository;

import model.Client;
import model.Professional;

import java.util.ArrayList;
import java.util.List;

public class ProfessionalRepository {


    private final List<Professional> professionals = new ArrayList<>();

    public void save(Professional professional) {
        professionals.add(professional);
    }

    public List<Professional> findAll() {
        return professionals;
    }
}
