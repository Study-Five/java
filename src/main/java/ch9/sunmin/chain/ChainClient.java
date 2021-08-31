package ch9.sunmin.chain;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ChainClient {
    public static void main(String[] args) {
        ProcessingObject<String> p1 = new HeaderTextProcessing();
        ProcessingObject<String> p2 = new SpellCheckerProcessing();
        p1.setSuccessor(p2); // chaining

        String result = p1.handle("Aren't labdas really cool?!!");
        System.out.println(result);

        // 반환값과 인수의 타입이 같은 함수 UnaryOperator
        UnaryOperator<String> headerProcessing = (String text) -> "From Raoul, Mario and Alan: " + text;
        UnaryOperator<String> spellCheckerProcessing = (String text) -> text.replaceAll("labda", "lambda");

        // Function 에서는 andThen 메서드를 쓸 수 있다.
        Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing);

        String resultWithLambda = pipeline.apply("Aren't labdas really cool?!!");
        System.out.println(resultWithLambda);
    }
}
