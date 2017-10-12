package test004_coursera_week05;

import java.util.HashMap;

import test004_coursera_week05.Person.Gender;
import test004_coursera_week05.University_Teacher.Title;

final class University {
	private String University_Name;
	
	private HashMap<String, University_College> CollegesMap = new HashMap<String, University_College>();
	private HashMap<Integer, University_Student> StudentsMap = new HashMap<Integer, University_Student>();
	private HashMap<Integer, University_Course> CoursesMap = new HashMap<Integer, University_Course>();
	private HashMap<Integer, University_Teacher> TeachersMap = new HashMap<Integer, University_Teacher>();
	
	// Constructor.
	University(String University_Name){
		this.University_Name = University_Name;
	}
	
	// Getter name.
	String getUniversityName(){
		return University_Name;
	}
	
	// Setter student.
	boolean StudentRegiste(int Student_No, String college_name, int personID, String name,  
								Person.Gender gender, String nation){
		
		University_College college = CollegesMap.get(college_name);
		if( null == college ){
			System.out.println("Invalid input college_name!");
			return false;
		}
		
		boolean registe_result = false;
		University_Student student = new University_Student(Student_No, college, personID, 
																name, gender, nation);
		if ( null != StudentsMap.put(Student_No, student)){
			registe_result = true;
		}
		return registe_result;
	}
	
	// Getter student.
	University_Student getStudent(int Student_No){
		return StudentsMap.get(Student_No);
	}
	
	// Setter college.
	boolean addCollege(String collegeName, String place){
		boolean addResult = false;
		University_College college = new University_College(collegeName, place);
		if ( null != CollegesMap.put(collegeName, college)){
			addResult = true;
		}
		return addResult;
	}
	
	// Getter college.
	University_College getCollege(String collegeName){
		return CollegesMap.get(collegeName);
	}
	
	// Setter course.
	boolean addCourse(int courseID,	String courseName, String collegeName, int studentsLimit) {
		University_College college = CollegesMap.get(collegeName);
		if( null == college ){
			System.out.println("Invalid input college_name!");
			return false;
		}
		
		boolean addResult = false;
		University_Course course = new University_Course(courseID, courseName, college, studentsLimit);
		if (null != CoursesMap.put(courseID, course)) {
			addResult = true;
		}
		return addResult;
	}

	// Getter course.
	University_Course getCourse(int courseID) {
		return CoursesMap.get(courseID);
	}
	
	// Setter teacher.
	boolean addTeacher(int saffID, String college_name, Title title, int personID, String name, 
							Gender gender, String nation) {
		
		University_College college = CollegesMap.get(college_name);
		if( null == college ){
			System.out.println("Invalid input college_name!");
			return false;
		}
		
		boolean addResult = false;
		University_Teacher teacher = new University_Teacher(saffID, college, title, personID, name, gender, nation);
		if (null != TeachersMap.put(saffID, teacher)) {
			addResult = true;
		}
		return addResult;
	}

	// Getter teacher.
	University_Teacher getTeacher(int teacherID) {
		return TeachersMap.get(teacherID);
	}

}



class University_College {
	private String collegeName;
	private String collegePlace;
	
	University_College(String collegeName, String collegePlace){
		this.collegeName = collegeName;
		this.collegePlace = collegePlace;
	}
	
	// Getter.
	String getName(){
		return collegeName;
	}
	
	String getPlace(){
		return collegePlace;
	}
	
}

