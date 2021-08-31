package ch9.sunmin.template;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

abstract class OnlineBanking {

    @Builder @Getter @Setter
    static class Customer {
        int id;
        String name;
    }

    static class DataBase {
        public static Customer getCustomerWithId(int id) {
            return Customer.builder()
                           .id(1000234)
                           .name("SunMin")
                           .build();
        }
    }

    /**
     * A common method this class must process
     * @param id
     */
    public void processCustomer(int id) {
        Customer c = DataBase.getCustomerWithId(1000234);
        makeCustomerHappy(c);
    }

    public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
        Customer c = DataBase.getCustomerWithId(12345);
        makeCustomerHappy.accept(c);
    }

    /**
     * All class extends this class have to implement this method
     * @param c
     */
    abstract void makeCustomerHappy(Customer c);

}
