package model;

public class Professional extends Person{
    private String speciality;

    public Professional(String name, String lastname, String email, String speciality) {
        super(name, lastname, email);
        this.speciality = speciality;
    }


    @Override
    public String toString() {
        return "Professional {\n" +
                super.toString() + "\n" +
                "speciality: " + speciality + "\n" +
                "}";
    }
}
