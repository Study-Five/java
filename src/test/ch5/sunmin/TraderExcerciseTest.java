package ch5.sunmin;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TraderExcerciseTest {
    static List<Transaction> testList;

    @BeforeAll
    public static void set() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milano");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        testList = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(mario, 2012, 710),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
    }

    @Test
    @DisplayName("[거래자 실습1] : 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정렬하시오")
    public void oneTest() {
        List<Integer> answer = Arrays.asList(300, 400);
        List<Transaction> resultList = TraderExcercise.one(testList);

        assertEquals(answer, resultList.stream().map(Transaction::getValue).collect(Collectors.toList()));
    }

    @Test
    @DisplayName("[거래자 실습2] : 거래자가 근무하는 모든 도시를 중복 없이 나열하시오")
    public void twoTest() {
        List<String> answer = Arrays.asList("Cambridge", "Milano");

        assertEquals(answer, TraderExcercise.two(testList));
    }

    @Test
    @DisplayName("[거래자 실습3] : 켐브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬")
    public void threeTest() {
        List<String> answer = Arrays.asList("Alan", "Brian", "Raoul");

        assertEquals(answer, TraderExcercise.three(testList).stream().map(x -> x.getName()).collect(Collectors.toList()));
    }

    @Test
    @DisplayName("[거래자 실습4] : 모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오")
    public void fourTest() {
        String answer = "Alan,Brian,Mario,Raoul";
        assertEquals(answer, TraderExcercise.four(testList));
    }

    @Test
    @DisplayName("[거래자 실습5] : 밀라노에 거래가 있는가?")
    public void fiveTest() {
        assertTrue(TraderExcercise.five(testList));
    }

    @Test
    @DisplayName("[거래저 실습6] : 캠브리지 거주하는 거래자의 모든 트랜잭션값을 출력하시오")
    public void sixTest() {
        assertEquals(Arrays.asList(300, 1000, 400, 950), TraderExcercise.six(testList));
    }

    @Test
    @DisplayName("[거래자 실습7] : 전체 트랜잭션 중 최댓값은 얼마인가?")
    public void sevenTest() {
        assertEquals(1000, TraderExcercise.seven(testList));
    }

    @Test
    @DisplayName("[거래자 실습8] : 전체 트랜잭션 중 최소값은 얼마인가?")
    public void eightTest() {
        assertEquals(300, TraderExcercise.eight(testList));
    }
}