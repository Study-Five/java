package ch4.sunmin;

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
	
}
