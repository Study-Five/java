package ch9.sunmin.template;

public class TemplateMethodClient {

    /**
     * 람다 이전에는 템플릿 메서드에 선언된 @abstract 클래스를 구현해서 써야 했다.
     */
    static class Template extends OnlineBanking {
        @Override
        void makeCustomerHappy(Customer c) {
            System.out.println("Hello " + c.getName());
        }
    }

    public static void main(String[] args) {
        Template template = new Template();
        template.processCustomer(123);

        // Consumer 함수형 인터페이스를 파라미터로 받는 메서드를 선언하면
        // 람다로 템플릿 메서드에 해당하는 전략을 전달할 수 있다.
        new Template().processCustomer(1337, (OnlineBanking.Customer c) -> System.out.println("Hi ! " + c.getName()));
    }
}
