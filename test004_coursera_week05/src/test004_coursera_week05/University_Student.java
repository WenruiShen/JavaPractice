package test004_coursera_week05;

import java.util.HashMap;

import test004_coursera_week05.Person.Gender;

final class University_Student extends Person implements ChooseCourseOperation{
	int studentID;
	University_College college;
	//private int enterYear;
	private HashMap<Integer, University_Course> CoursesMap = new HashMap<Integer, University_Course>();
	
	University_Student(int studentID, University_College college, int ID, String name, 
							Gender gender, String nation){
		super(ID, name, gender, nation);
		this.studentID = studentID;
		this.college = college;
	}
	
	// Setter a course.
	static final int coursesNumberLimnit = 5;
	public boolean addClass(University_Course course){
		boolean addResult = false;
		if (this.CoursesMap.size() >= coursesNumberLimnit)
			return addResult;
		if(null != this.CoursesMap.put(course.CourseID, course)){
			addResult = true;
		}
		System.out.printf("Student:%d(%s) choose course: %d(%s)\n", studentID, name, course.CourseID,course.CourseName);
		course.add_a_Student(studentID, this);
		return addResult;
	}
	
	// Delete a course.
	public boolean delClass(int courseID) {
		boolean delResult = false;
		University_Course course = this.CoursesMap.remove(courseID);
		if (null != course) {
			delResult = true;
		}
		System.out.printf("Student:%d(%s) delete course: %d(%s)\n", studentID, name, courseID,course.CourseName);
		course.del_a_Student(studentID);
		return delResult;
	}
	
	// Getter classes' list.
	public HashMap<Integer, University_Course> getCourses(){
		System.out.printf("Student:%d(%s) has chosen courses:\n", this.studentID, this.name);
		for(University_Course course : CoursesMap.values()){
			System.out.printf("\t%d(%s)\n", course.CourseID, course.CourseName);
		}
		System.out.printf("\n");
		return CoursesMap;
	}
	
}

interface ChooseCourseOperation{
	// Setter a course.
	boolean addClass(University_Course course);		
	// Delete a course.
	boolean delClass(int courseID);
	// Getter classes' list.
	HashMap<Integer, University_Course> getCourses();
}

