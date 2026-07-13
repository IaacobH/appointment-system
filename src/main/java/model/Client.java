package model;

public class Client extends Person{

    public Client(String name, String lastname,String email) {
        super(name, lastname, email);
    }


    @Override
    public String toString() {
        return "Client {\n" +
                super.toString() + "\n" +
                "}";
    }
}
