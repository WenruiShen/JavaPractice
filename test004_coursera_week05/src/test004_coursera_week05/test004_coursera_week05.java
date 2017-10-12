package test004_coursera_week05;

import test004_coursera_week05.Person.Gender;
import test004_coursera_week05.University_Teacher.Title;

/******************************************************
 * Coursera Assignment Week-05
 * Description: Procedure for Students selecting classes.
 * Requirements: Usage of interfaces, extends, setter/getter,  
 * 				 static, final, abstract£¬@Override, etc.
 * Author: Wenrui Shen
 * Email:  wenrui.shen@ucdconnect.ie
 * Date:   2017/05/12.
 ******************************************************/

public class test004_coursera_week05 {
	
	
	public static void main(String[] args) {
		// Create a University.
		University UCD = new University("University College of Dublin");
		
		// Add some colleges to this university.
		UCD.addCollege("Computer Science", "Belfield");
		UCD.addCollege("Engineering", "Belfield");
		UCD.addCollege("Law", "Belfield");
		UCD.addCollege("Biology", "Belfield");
		UCD.addCollege("Sports", "Belfield");
		
		// Add some teachers.
		UCD.addTeacher(1001, "Law", 	University_Teacher.Title.Dr, 1970032101, "T-A", Person.Gender.Male, "Ireland");
		UCD.addTeacher(1002, "Law", 	University_Teacher.Title.Dr, 1967052305, "T-B", Person.Gender.Female, "America");
		UCD.addTeacher(1003, "Biology", University_Teacher.Title.Dr, 1987071103, "T-C", Person.Gender.Male, "Ireland");
		UCD.addTeacher(1004, "Biology", University_Teacher.Title.Dr, 1968100902, "T-D", Person.Gender.Female, "America");
		UCD.addTeacher(1005, "Engineering", University_Teacher.Title.Dr, 1981061101, "T-E", Person.Gender.Male, "Ireland");
		UCD.addTeacher(1006, "Engineering", University_Teacher.Title.Dr, 1984032402, "T-F", Person.Gender.Female, "Ireland");
		UCD.addTeacher(1007, "Engineering", University_Teacher.Title.Mr, 1979071405, "T-G", Person.Gender.Male, "America");
		UCD.addTeacher(1008, "Engineering", University_Teacher.Title.Mrs, 1980032804, "T-H", Person.Gender.Female, "China");
		UCD.addTeacher(1009, "Computer Science", University_Teacher.Title.Dr, 1984042101, "T-I", Person.Gender.Male, "Ireland");
		UCD.addTeacher(1010, "Computer Science", University_Teacher.Title.Dr, 1986050102, "T-J", Person.Gender.Male, "China");
		UCD.addTeacher(1011, "Computer Science", University_Teacher.Title.Mr, 1979031101, "T-K", Person.Gender.Male, "Ireland");
		
		// Add some Courses.
		UCD.addCourse(2017010001, "Math", 		"Engineering", 30);
		UCD.addCourse(2017010002, "Physics", 	"Engineering", 30);
		UCD.addCourse(2017010003, "Chemistry", 	"Engineering", 30);
		UCD.addCourse(2017010004, "English",	"Law", 		20);
		UCD.addCourse(2017010005, "Art", 		"Law", 		15);
		UCD.addCourse(2017010006, "Sports",		"Sports", 	15);
		
		// Add some students.
		UCD.StudentRegiste(20001, "Law", 		1992072001, "Stu-A", Person.Gender.Male, "Ireland");
		UCD.StudentRegiste(20002, "Biology", 	1996053002, "Stu-B", Person.Gender.Female, "Ireland");
		UCD.StudentRegiste(20003, "Law", 		1988012201, "Stu-C", Person.Gender.Male, "America");
		UCD.StudentRegiste(20004, "Biology", 	1989070104, "Stu-D", Person.Gender.Female, "UK");
		UCD.StudentRegiste(20005, "Engineering",1991042501, "Stu-E", Person.Gender.Male, "China");
		UCD.StudentRegiste(20006, "Engineering",1994112002, "Stu-F", Person.Gender.Female, "Ireland");
		UCD.StudentRegiste(20007, "Computer Science", 1987011002, "Stu-G", Person.Gender.Male, "Ireland");
		
		// Students choose courses.
		University_Student student_1 = UCD.getStudent(20001);
		student_1.addClass(UCD.getCourse(2017010001));
		student_1.addClass(UCD.getCourse(2017010004));
		
		University_Student student_2 = UCD.getStudent(20002);
		student_2.addClass(UCD.getCourse(2017010001));
		student_2.addClass(UCD.getCourse(2017010004));
		student_2.addClass(UCD.getCourse(2017010005));
		
		University_Student student_3 = UCD.getStudent(20003);
		student_3.addClass(UCD.getCourse(2017010001));
		student_3.addClass(UCD.getCourse(2017010003));
		student_3.addClass(UCD.getCourse(2017010004));
		student_3.addClass(UCD.getCourse(2017010005));
		
		// Students delete courses' selection.
		student_3.delClass(2017010004);
		
		System.out.println("******************************************\n");
		// Check Students' classes' tables.
		student_1.getCourses();
		student_2.getCourses();
		student_3.getCourses();
		
		// Check courses' students' list.
		University_Course course_1 = UCD.getCourse(2017010001);
		course_1.checkStudentsList();
		University_Course course_2 = UCD.getCourse(2017010003);
		course_2.checkStudentsList();
		University_Course course_3 = UCD.getCourse(2017010004);
		course_3.checkStudentsList();
		University_Course course_4 = UCD.getCourse(2017010005);
		course_4.checkStudentsList();
	}
}
