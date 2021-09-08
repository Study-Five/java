package ch11.sunmin;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonClient {
    public static void main(String[] args) {
        Properties properties = new Properties();
    }

    public static String getCarInsuranceName(Optional<Person> person) {
        return person.flatMap(Person::getCar)
                                      .flatMap(Person.Car::getInsurance)
                                      .map(Person.Insurance::getName)
                                      .orElse("Unknown");
    }

    public static Set<String> getCarInsuranceNames(List<Person> persons) {
        return persons.stream()
                      .map(Person::getCar)
                      .map(car -> car.flatMap(Person.Car::getInsurance))
                      .map(ins -> ins.map(Person.Insurance::getName))
                      .filter(Optional::isPresent)
                      .map(Optional::get) // Stream<Optional<String>> dmf Stream<String> 으로 평준화
                      .collect(Collectors.toSet());
    }

    public static Optional<Person.Insurance> nullSafeFindCheapestInsurance(Optional<Person> person, Optional<Person.Car> car) {
        return person.flatMap(p -> car.map(c -> findcheapestInsurance(p, c)));
    }

    public static Person.Insurance findcheapestInsurance(Person p, Person.Car c) {
        return null;
    }
}
