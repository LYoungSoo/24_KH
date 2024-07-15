package ex;

public class PrintEx {
    public static void main(String[] args) {
        /**
         * System.out == 지정된 출력용 화면 == 이클립스는 console 창
         *
         * System.out.print(출력 내용);
         * -() 내의 값을 console에 출력
         * -> 줄이 바뀌지 않고 이후 출력도 오른쪽에 이어서 출력
         *
         * System.out.println(출력 내용);
         * -() 내의 값을 console에 출력 + 줄바꿈
         *
         * System.out.printf("탬플릿", 사용될 변수||값...);
         * - 정해진 형식(템플릿)에 맞는 문자열을 출력하는 구문
         * - 템플릿 문자열 중간에 작성된 '%' 패턴 구문에 "사용될 변수||값" 을 순서대로 대입
         *
         * (JS의 ``(백틱) 구문과 같은 템플릿 문자열)
         * ex)
         * [JS]
         * const name = "홍길동";
         * console.log(`나는 ${name} 입니다`);
         *
         * [Java]
         * String name = "홍길동";
         *
         * System.out.printf("나는 %s 입니다.", name);   //이거 c언어에서도 쓰던?
         *
         * %d : 정수 (byte, short, int)
         * %c : 문자 (char)
         * %s : 문자열 (String)
         * %b : 논리형 (boolean)
         * %f : 실수형 (float/double)
         *
         * 양수    : 오른쪽 정렬
         * 음수(-) : 왼쪽 정렬
         *
         * %숫자d : 정수가 출력된 칸을 숫자 만큼 확보
         * ex) %-6d :  ㅁㅁㅁㅁㅁㅁ
         *
         * %.숫자f : 소수점 아래 몇째 자리 까지 표시할지 지정 (지정된 자리 아래에서 반올림 처리)
         */

        // print(), println() 확인
        System.out.print("이름 : ");      // 줄바꿈 X
        System.out.println("이영수");     // 줄바꿈 O
        System.out.println("와 자바칩 프라푸치노 먹고싶다~");    // 줄바꿈 O

        System.out.println();   //출력 내용 없이 줄 바꿈 == <br>
        System.out.println();
        System.out.println();

        System.out.println("[printf() 확인하기]");

        String  name   = "홍길동";  // (참조형)
        int     age    = 25;        // (정수 기본형, 4 byte)
        double  height = 212.5;     // (실수 기본형, 8 byte)
        char    gender = '남';      // (문자형, 2 byte)
        boolean javaStudy = true;   // (논리형, 1 byte)

        // 홍길동은 25세 남성으로 키는 212.5cm 이고, 자바 공부 여부 : true;

        System.out.println(name + "은 " + age + "세 " + gender + "성으로 키는 "
                + height + "cm이고, 자바 공부 여부 : " + javaStudy);
        //printf 버전
        System.out.printf("%s은 %d세 %c성으로 키는 %fcm 이고, 자바 공부 여부 : %b",
                name, age, gender, height,javaStudy);

        System.out.println();   // 줄바꿈
        System.out.printf("%s/%f", name, height);   // 홍길동/212.500000

        // 문자열 6칸 오른쪽 정렬 : %6s
        System.out.println();
        System.out.printf("%6s/%f", name, height); //ㅁㅁㅁ홍길동/212.500000


        // 문자열 6칸 왼쪽 정렬 : %-6s
        System.out.println();
        System.out.printf("%-6s/%f", name, height); //홍길동ㅁㅁㅁ/212.500000


        // 소수점 아래로 1자리 : %.1f
        // -> 둘째 자리에서 반올림
        double num = 1.25;
        System.out.println();
        System.out.printf("%s/%.1f", name, num); //홍길동/1.3
    }

}
