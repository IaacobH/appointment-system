package model;

public class Service {
    private long id;
    private String serviceName;
    private double price;

    public Service(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OfferedService {\n" +
                "  serviceName: " + serviceName + "\n" +
                "  price: $" + price + "\n" +
                "}";
    }
}
