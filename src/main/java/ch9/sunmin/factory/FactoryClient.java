package ch9.sunmin.factory;

public class FactoryClient {

    public static void main(String[] args) {
        ProductFactory.Product p = ProductFactory.createProduct("loan");
    }
}
