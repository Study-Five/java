package ch8.sunmin;

import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

public class NewCollection {
    public static void main(String[] args) {
        Map<String, Integer> ageOfFriends = Map.ofEntries(Map.entry("Sunmin", 28),
//                                                          Map.entry("Sunmin", 30),
                                                          Map.entry("Hyerin", 30),
                                                          Map.entry("Kwangwoon", 31),
                                                          Map.entry("SeJeong", 29));

//        System.out.println(ageOfFriends);
//
//        ageOfFriends.forEach((friend, age) -> System.out.println(friend + " is " + age + " years old"));

        ageOfFriends.entrySet().stream()
                               .sorted(Map.Entry.comparingByKey())
                               .forEachOrdered(System.out::println);

        System.out.println(ageOfFriends.getOrDefault("JongSun", -1));

        Map<String, String> favouriteMovies = new HashMap<>();

        favouriteMovies.put("Raphael", "Star Wars");
        favouriteMovies.put("Olivia", "james bond");
        favouriteMovies.replaceAll((freind, movie) -> movie.toUpperCase());
        System.out.println(favouriteMovies);

        Map<String, String> family = Map.ofEntries(entry("Teo", "Star Wars"), entry("Cristina", "James Bond"));

        Map<String, String> friends = Map.ofEntries(entry("Raphael", "Star Wars"), entry("Cristina", "Matrix"));

        Map<String, String> everyone = new HashMap<>(family);
        friends.forEach((k, v) -> everyone.merge(k, v, (movie1, movie2) -> movie1 + " & " + movie2)); // 중복된 키가 있으면 두 값을 연결
        System.out.println(everyone);

    }
}
