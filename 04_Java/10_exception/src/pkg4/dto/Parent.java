package pkg4.dto;

import java.io.IOException;

public abstract class Parent {
    // IOException을 던질 가능성이 있는 메서드 2개
    public void method1() throws IOException {}
    public void method2() throws IOException {}
    
//    public void method3() throws IOException{}
    // 예외처리 구문 제거
    // => 오버라이딩 성립!!
    public abstract void method3();
}
