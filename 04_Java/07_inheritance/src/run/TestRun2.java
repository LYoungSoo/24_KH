package run;

import dto.Child1;
import dto.Parent;

public class TestRun2 {
    public static void main (String[] args) {
        
        // 부모 객체 생성
        Parent p1 = new Parent();
        p1.introduce();
        
        System.out.println("==================================================");
        
        // 자식 객체 생성
        Child1 c1 = new Child1();   // 내부에 Parent 객체 존재
        c1.introduce();
        
        System.out.println("--------------------------------------------------");
        
        System.out.println("[Object.toString() 오버라이딩 확인]");
        System.out.println(p1.toString());
        System.out.println(p1);     // 참조 변수명만 작성해도 ==> toString() 자동 호출
        
        System.out.println("--------------------------------------------------");
        
        System.out.println("[Parent.toString() 오버라이딩 확인]");
        c1.setNoteBook("LG Gram");
        System.out.println(c1);
        
        
    }
}
