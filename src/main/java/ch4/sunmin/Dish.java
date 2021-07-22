package ch4.sunmin;

import ch6.sunmin.StremGrouping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Dish {
	
	public enum Type {MEAT, FISH, OTHER};

	private String name;
	private boolean vegetarian;
	private int calories;
	private Type type;
	private StremGrouping.CaloricLevel caloricLevel;

	public Dish(String name, boolean vegetarian, int calories, Type type) {
		this.name = name;
		this.vegetarian = vegetarian;
		this.calories = calories;
		this.type = type;
	}

	public StremGrouping.CaloricLevel getCaloricLevel() {
		if(this.getCalories() <= 400) return StremGrouping.CaloricLevel.DIET;
		else if (this.getCalories() <= 700) return StremGrouping.CaloricLevel.NORMAL;
		else return StremGrouping.CaloricLevel.FAT;
	}
	
}
