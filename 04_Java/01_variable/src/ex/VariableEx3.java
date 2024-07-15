package ex;

public class VariableEx3 {
    public static void main(String[] args) {
        /* ----- 강제 형변환 ----
         *
         * 1. 값의 범위가 큰 자료형 ==> 작은 자료형으로 변환 (데이터 손실을 고려해야 함)
         *
         * 2. 의도적으로 자료형을 변환할 때
         *
         * [작성법]
         * (자료형) 변수명;
         * 또는
         * (자료형) 값;
         *
         * - 변수명 / 값이 지정된 자료형을 반환
         */

        System.out.println("[강제 형변환 데이터 손실 확인 1]");

        int num1 = 290;
        byte result1 = (byte) num1;  //byte 강제 형변환

        System.out.println("num1 : " + num1);       // 290
        System.out.println("result1 : " + result1); // 34       // 뭐냐이거 왜이리 똑똑해짐

        System.out.println("========================================");

        System.out.println("[강제 형변환 데이터 손실 확인 2]");

        // 실수를 정수로 강제 형변환

        double num2 = 123.456789;
        int result2 = (int) num2;
        System.out.println("num2 : " + num2);           // 123.456789
        System.out.println("result2 : " + result2);     // 123

        // (자동 형변환)
        System.out.println(num2 - result2);             // 0.456789
        // -> 0.45678900000000056   (2진수 소수 표현 시 문제점) (조교수님이 1학년때 내준 지옥의 과제들)

        System.out.println("========================================");

        /* 난수 + 강제 형변환 */

        // 난수 : Math.random()
        // 범위 : 0.0 <= x < 1.0
        // 자료형 : double

        // 1 ~ 10 사이 난수(정수)
        int randomNumber = (int) (Math.random() * 10 + 1);
        System.out.println("randomNumber : " + randomNumber);

        System.out.println("========================================");
        System.out.println("[의도적으로 강제 형변환]");

        System.out.println(123);    // 123
        System.out.println((double) 123);    // 123.0

        System.out.println("========================================");
        System.out.println("[ 문자 (char) <-> 유니코드(int) ]");

        System.out.println("A의 번호 : " + (int) 'A');  //65
        System.out.println("a의 번호 : " + (int) 'a');  //97

        System.out.println("'이'의 번호 : " + (int) '이');  //51060
        System.out.println("'영'의 번호 : " + (int) '영');  //50689
        System.out.println("'수'의 번호 : " + (int) '수');  //49688

        System.out.println("ㄱ의 번호 : " + (int) 'ㄱ');  //12593
        System.out.println("ㄱ+1의 번호 : " + (int) 'ㄱ' + 1);  //125931
        System.out.println("ㄱ+1의 번호 : " + (char) ('ㄱ' + 1));  //ㄲ
        System.out.println("ㄱ+2의 번호 : " + (char) ('ㄱ' + 2));  //ㄳ
        System.out.println("ㄱ+3의 번호 : " + (char) ('ㄱ' + 3));  //ㄴ
        System.out.println("ㄱ+4의 번호 : " + (char) ('ㄱ' + 4));  //ㄵ

        System.out.println("========================================");
        System.out.println("[오버 플로우]");

        // 오버 플로우(Overflow)
        // - 연산 결과가 자료형의 값의 범위를 초과(+) 한 경우

        // int 자료형 최대 범위 초과
        int max = Integer.MAX_VALUE; // int 최댓값

        System.out.println("max : " + max);             // 2147483647
        System.out.println("max + 1 : " + (max + 1));   // -2147483648

        System.out.println("========================================");

        /**
         * [상수]
         * - 한 번 대입하면 바꿀 수 없는 변수
         *
         * [작성법]
         * final 자료형 변수명 = 값;
         * ==> 변수명은 대문자 + _ 로 형식으로 작성
         */

        final double PI = 3.14159265359;
        final int NATIONAL_CODE = 82;
        //NATIONAL_CODE = 11;	
        //The final local variable NATIONAL_CODE cannot be assigned. It must be blank and not using a compound assignment
        //Cannot assign a value to final variable 'NATIONAL_CODE
        System.out.println("PI : " + PI);
        System.out.println("NATIONAL_CODE : " + NATIONAL_CODE);
    }
}
