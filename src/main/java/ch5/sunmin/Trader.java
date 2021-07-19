package ch5.sunmin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Trader {
    private final String name;
    private final String city;

    public Trader(String n, String c) {
        this.name = n;
        this.city = c;
    }

    public String toString() {
        return "Trader:" + this.name + " in " + this.city;
    }

}
