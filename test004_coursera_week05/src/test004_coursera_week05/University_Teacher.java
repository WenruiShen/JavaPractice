package test004_coursera_week05;

import java.util.HashMap;

final class University_Teacher extends Person{
	public enum Title{
		Dr, Mr, Mrs
	}
	private int saffID;
	private University_College college;
	private Title title;
	//private HashMap<Integer, University_Course> CoursesMap = new HashMap<Integer, University_Course>();
	
	University_Teacher(int saffID, University_College college, Title title, int ID, String name, 
								Gender gender, String nation){
		super(ID, name, gender, nation);
		this.saffID = saffID;
		this.college = college;
		this.title = title;
	}
}
