package test004_coursera_week05;

import java.util.HashMap;

final class University_Course extends Course_Info_Handler implements Course_Operation {
	private HashMap<Integer, University_Student> StudentsMap = new HashMap<Integer, University_Student>();
	private HashMap<Integer, University_Teacher> TeachersMap = new HashMap<Integer, University_Teacher>();
	
	University_Course(int CourseID,	String CourseName, University_College college, int studentsLimit){
		this.CourseID = CourseID;
		this.CourseName = CourseName;
		this.college = college;
		this.studentsLimit = studentsLimit;
	}

	@Override
	public boolean add_a_Lecture(Integer staffID, University_Teacher lecture) {
		// TODO Auto-generated method stub
		boolean addResult = false;
		if( null != TeachersMap.put(staffID, lecture)){
			addResult = true;
		}
		return addResult;
	}

	@Override
	public boolean del_a_Lecture(Integer staffID) {
		// TODO Auto-generated method stub
		boolean delResult = false;
		if( null != TeachersMap.remove(staffID)){
			delResult = true;
		}
		return delResult;
	}

	@Override
	public HashMap<Integer, University_Teacher> checkTeachersList() {
		// TODO Auto-generated method stub
		return TeachersMap;
	}

	@Override
	public boolean add_a_Student(Integer studentNo, University_Student student) {
		// TODO Auto-generated method stub
		boolean addResult = false;
		if( null != StudentsMap.put(studentNo, student)){
			addResult = true;
		}
		return addResult;
	}

	@Override
	public boolean del_a_Student(Integer studentNo) {
		// TODO Auto-generated method stub
		boolean delResult = false;
		if( null != StudentsMap.remove(studentNo)){
			delResult = true;
		}
		return delResult;
	}

	@Override
	public HashMap<Integer, University_Student> checkStudentsList() {
		// TODO Auto-generated method stub
		System.out.printf("Course:%d(%s, college:%s) Students list:\n", this.CourseID, this.CourseName, this.college.getName());
		for(University_Student student : StudentsMap.values()){
			System.out.printf("\t%d(%s)\n", student.studentID, student.name);
		}
		System.out.printf("\n");
		return StudentsMap;
	}

	@Override
	void CourseInfoUpdate(Course_Info courseInfo) {
		// TODO Auto-generated method stub
		this.CourseID = courseInfo.CourseID;
		this.CourseName = courseInfo.CourseName;
		this.CourseIntro = courseInfo.CourseIntro;
		this.college = courseInfo.college;
		this.studentsLimit = courseInfo.studentsLimit;
		this.course_type = courseInfo.course_type;
		this.place = courseInfo.place;
		this.year = courseInfo.year;
		this.semester = courseInfo.semester;
	}
	
	
}

// Abstract.
abstract class Course_Info_Handler extends Course_Info {
	// Setter Course Introduction.
	void CourseIntroductionUpdate(String CourseIntro){
		this.CourseIntro = CourseIntro;
	}
	
	// Getter Course Introduction.
	String CourseIntroduction(){
		return this.CourseIntro;
	}
	
	abstract void CourseInfoUpdate(Course_Info courseInfo);
	
	Course_Info CourseInfoCheck(){
		Course_Info responseInfo = new Course_Info();
		responseInfo.CourseID = this.CourseID;
		responseInfo.CourseName = this.CourseName;
		responseInfo.CourseIntro = this.CourseIntro;
		responseInfo.college = this.college;
		responseInfo.studentsLimit = this.studentsLimit;
		responseInfo.course_type = this.course_type;
		responseInfo.place = this.place;
		responseInfo.year = this.year;
		responseInfo.semester = this.semester;
		return responseInfo;
	}
	
	
}

class Course_Info {
	int CourseID;
	String CourseName;
	String CourseIntro;
	University_College college;
	int studentsLimit;
	
	public enum CourseType{
		Elective, Compulsory 
	}
	CourseType course_type;
	
	String place;
	int year;
	int semester;
}

// Interface.
interface Course_Operation{
	// Setter Lectures.
	boolean add_a_Lecture(Integer staffID, University_Teacher lecture);
	boolean del_a_Lecture(Integer staffID);
	// Getter Lectures.
	HashMap<Integer, University_Teacher> checkTeachersList(); 
	
	// Setter Student.
	boolean add_a_Student(Integer studentNo, University_Student student);
	boolean del_a_Student(Integer studentNo);
	// Getter Student.
	HashMap<Integer, University_Student> checkStudentsList(); 
}