package model;

public abstract class Person {
    private long id;
    private String name;
    private String lastname;
    private String email;

    public Person(String name, String lastname,String email){
        this.name = name;
        this.lastname = lastname;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void sendEmail() {}

    @Override
    public String toString() {
        return "Person {\n" +
                "  id: " + id + "\n" +
                "  name: " + name + "\n" +
                "  lastname: " + lastname + "\n" +
                "  email: " + email + "\n" +
                "}";
    }
}
