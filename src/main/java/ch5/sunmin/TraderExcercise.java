package ch5.sunmin;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TraderExcercise {

    public enum CITY {
        CAMBRIDGE("Cambridge"),
        MILANO("Milano");

        private String city;

        CITY(String city) {
            this.city = city;
        }

        public String getCity() {
            return city;
        }
    }

    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milano");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        one(transactions);
        two(transactions);
    }

    public static List<Transaction> one(List<Transaction> transactions) {

        List<Transaction> result = transactions.stream()
                                                .filter(x -> x.getYear() == 2011)
                                                .sorted(Comparator.comparing(Transaction::getValue))
                                                .collect(Collectors.toList());
        result.stream().forEach(System.out::println);
        return result;
    }

    public static List<String> two(List<Transaction> transactions) {

        List<String> result =  transactions.stream()
                                            .map(x -> x.getTrader().getCity())
                                            .distinct().collect(Collectors.toList());

        result.stream().forEach(System.out::println);
        return result;
    }

    public static List<Trader> three(List<Transaction> transactions) {
        List<Trader> result = transactions.stream()
                                               .map(Transaction::getTrader)
                                               .filter(x -> x.getCity().equals("Cambridge"))
                                               .distinct()
                                               .sorted(Comparator.comparing(x -> x.getName()))
                                               .collect(Collectors.toList());
        System.out.println("3번 ========");
        result.stream().forEach(System.out::println);

        return result;
    }

    public static String four(List<Transaction> transactions) {

        return transactions.stream()
                    .map(Transaction::getTrader)
                    .map(Trader::getName)
                    .distinct()
                    .sorted()
                    .collect(Collectors.joining(","));
    }

    public static boolean five(List<Transaction> transactions) {
        return transactions.stream()
                           .anyMatch(x -> x.getTrader().getCity().equals(CITY.MILANO.getCity()));

    }

    public static List<Integer> six(List<Transaction> transactions) {
        return transactions.stream()
                    .filter(x -> x.getTrader().getCity().equals(CITY.CAMBRIDGE.getCity()))
                    .map(Transaction::getValue)
                    .collect(Collectors.toList());
    }

    public static int seven(List<Transaction> transactions) {
        return transactions.stream()
                           .map(Transaction::getValue)
                           .reduce(Integer::max)
                           .get();
    }

    public static int eight(List<Transaction> transactions) {
        return transactions.stream().min(Comparator.comparing(Transaction::getValue)).get().getValue();
    }
}
