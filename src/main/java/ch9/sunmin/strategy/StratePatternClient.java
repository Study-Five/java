package ch9.sunmin.strategy;

public class StratePatternClient {
    public static void main(String[] args) {
        // Validator 인터페이스에 Strategy 를 파라미터로 전달해서 유연하게 로직을 수정할 수 있도록 함
        Validator numericValidator = new Validator(new IsNumeric());
        // Predicate<String> 람다를 전달
        numericValidator = new Validator((String s) -> s.matches("\\d+"));
        boolean b1 = numericValidator.validate("aaa");

        Validator lowerCaseValidator = new Validator(new IsAllLowerCase());
        lowerCaseValidator = new Validator((String s) -> s.matches("[a-z]+"));
        boolean b2 = lowerCaseValidator.validate("bbb");

        System.out.printf("validator1 %b\nvalidator2 %b", b1, b2);
    }
}
