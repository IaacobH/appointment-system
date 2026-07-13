package model;

public class OfferedService {
    private String serviceName;
    private double price;

    public OfferedService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }


    @Override
    public String toString() {
        return "OfferedService {\n" +
                "  serviceName: " + serviceName + "\n" +
                "  price: $" + price + "\n" +
                "}";
    }
}
