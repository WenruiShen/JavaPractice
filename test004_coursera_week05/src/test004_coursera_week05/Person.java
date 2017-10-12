package test004_coursera_week05;

import java.util.Date;

class Person {
	public enum Gender{
		Male, Female, Others
	}
	
	int ID;
	String name;
	Gender gender;
	String nation;
	Date birthDate;
	Date deadDate;
	
	Person(int ID, String name, Gender gender, String nation ){
		this.ID = ID;
		this.name = name;
		this.gender = gender;
		this.nation = nation;
	}
	
	// Getter
	
}
