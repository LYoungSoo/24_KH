package pkg4.dto;

import java.io.EOFException;

public class Child extends Parent{
    // 부모랑 예외 처리 구문이 같으면 문제없음!
//    @Override
//    public void method1() throws IOException {}
    
    // 부모 예외 ==> 자식 예외로 변경
    // ==>  컴파일 오류 없음 == 오버라이딩 성립!
    @Override
    public void method1() throws EOFException {
    }
    
    // 부모 예외 ==> 더 위에 부모 예외로 변경
    // 컴파일 에러 발생! == 오버라이딩 성립 X
    
//    @Override
//    public void method2() throws Exception {}
    
    // 예외처리 구문 제거
    // => 오버라이딩 성립!!
    @Override
    public void method3() {
        // 왜 되는거지???
        // -> 오버라이딩한 코드에서 예외가 발생 안할수도 있어서!!
    }
    
}
