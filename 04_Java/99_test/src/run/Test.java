package run;

import java.util.ArrayList;
import java.util.List;
import dto.Student;

public class Test {
	public static void main(String[] args) {
//		ArrayList<Student> list = null;
		// Exception in thread "main" java.lang.NullPointerException: Cannot invoke "java.util.ArrayList.add(Object)" because "list" is null
		ArrayList<Student> list = new ArrayList<Student>();
//		List<Student> list = new ArrayList<Student>();
			// The constructor Student(String, int) is undefined
				// Student.java 수정함
		list.add(new Student("강건강", 84));
		list.add(new Student("남나눔", 78));
		list.add(new Student("도대담", 96));
		list.add(new Student("류라라", 67));
		
//		for(int i = 0; i <list.length(); i++) {
			// The method length() is undefined for the type ArrayList<Student>
		for(int i = 0; i <list.size(); i++) {	//**********
			Student s = list.get(i);
//			System.out.println(s);
			System.out.printf("%s 학생의 점수 : %d점", s.getName(), s.getScore());
		}
	}
}
