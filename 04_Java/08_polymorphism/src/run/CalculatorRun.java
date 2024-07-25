package run;

import pkg3.dto.Calculator;
import pkg3.dto.LYSCalculator;

public class CalculatorRun {
    public static void main(String[] args) {
        
        // 인터페이스도 추상 클래스처럼
        // - 객체 생성 x
        // 부모 타입 참조 변수로 사용 O
        // ==> 다형성 업캐스팅 + 동적 바인딩
        
        Calculator c = new LYSCalculator();
        System.out.println("LYS Calculator");
        System.out.println("--------------------------------------------------");
        System.out.println ("1. Plus" + " : " + c.plus(35,7) );
        System.out.println ("2. Minus" + " : " + c.minus(35,7) );
        System.out.println ("3. Multiple" + " : " + c.multi(35, 7));
        System.out.println ("4. Division" + " : " + c.div(35, 7));
        System.out.println ("5. Modulo" + " : " + c.mod(59, 7));
        System.out.println ("6. Pow" + " : " + c.pow(5, 7));
        System.out.println ("7. Area of Circle" + " : " + c.areaOfCircle(5.7));
        System.out.println ("8. ToBinary" + " : " + c.toBinary(35));
        System.out.println ("9. ToHexadecimal" + " : " + c.toHexadecimal(59));
    }
}
