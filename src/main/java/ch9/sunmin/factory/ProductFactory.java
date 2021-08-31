package ch9.sunmin.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ProductFactory {

    // 인수를 받지 않고 값을 반환하는 함수 Supplier 사용
    final static Map<String, Supplier<Product>> map = new HashMap<>();
    static {
        map.put("loan", ProductFactory.Loan::new);
        map.put("stock", ProductFactory.Stock::new);
        map.put("bond", ProductFactory.Bond::new);
    }

    static class Product {

    }
    static class Loan extends Product {

    }
    static class Stock extends Product{

    }
    static class Bond extends Product{

    }
    public static Product createProduct(String name) {
//        switch (name){
//            case "loan" : return new Loan();
//            case "stock" : return new Stock();
//            case "bond" : return new Bond();
//            default: throw new RuntimeException("No such product " + name);
//        }

        Supplier<Product> p = map.get(name);
        if(p != null) return p.get();
        throw new IllegalArgumentException("No such product " + name);
    }
}
