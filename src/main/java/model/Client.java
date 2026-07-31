package model;

public class Client extends Person{

    public Client(int id, String name, String lastname,String email) {
        super(id, name, lastname, email);
    }


    @Override
    public String toString() {
        return "Client {\n" +
                super.toString() + "\n" +
                "}";
    }
}
