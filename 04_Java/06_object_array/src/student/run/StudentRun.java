package student.run;

import student.view.StudentView;

// 실행용 클래스
public class StudentRun {
    
    public static void main (String[] args) {
        
        // StudentView 객체 생성
        StudentView view = new StudentView();
        view.mainMenu();
    }
}
