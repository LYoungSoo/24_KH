package cls.pkg2;

import cls.pkg1.ClassTest1;
//import cls.pkg1.DefaultClass;

public class ClassRun2 {
    public static void main (String[] args) {
        
        // public class + 다른 패키지
        // ClassTest1 객체 생성 하기 ==> 문제 X
        ClassTest1 t1 = new ClassTest1();
        t1.method();
        
        
        // (default) class + 같은 패키지
        // DefaultClass 객체 생성 ==> 문제 X
        // ==> 다른 패키지라서 접근 불가능 / Cannot be accessed from outside package
        
//        DefaultClass d1 = new DefaultClass();
//        d1.method();
    }
}
