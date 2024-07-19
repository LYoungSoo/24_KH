package field.pkg2;

import field.pkg1.FieldTest1;

public class FieldRun2 extends FieldTest1 {
    public static void main (String[] args) {
        
        // FieldTest1 객체 생성
        FieldTest1 f1 = new FieldTest1();
        
        // public
        System.out.println(f1.publicValue);
        
        // protected    ==> 문제 발생 (상속 관계 X)
//        System.out.println(f1.protectedValue);
        
        // default ==> 문제 발생
//        System.out.println(f1.defaultValue);
        // 'defaultValue' is not public in 'field.pkg1.FieldTest1'. Cannot be accessed from outside package
        
        // private ==> 문제 발생
//        System.out.println(f1.privateValue);
        // 'privateValue' has private access in 'field.pkg1.FieldTest1'
    }
    
    @Override
    public void method () {
        // 상속 관계이기 때문에 protected 직접 접근 가능
        // main은 static이기 때문에 접근이 안된다 뭐라나
        System.out.println(protectedValue);
    }
}
