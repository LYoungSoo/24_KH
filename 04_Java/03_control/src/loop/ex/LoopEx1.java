package loop.ex;

import java.util.Scanner;

public class LoopEx1 {
    Scanner sc = new Scanner(System.in);

    /**
     * 1부터 10까지 출력하기
     */
    public void method1() {
        for (int i = 0; i <= 10; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("1========================================");
    }

    /**
     * 첫 번째 입력 받은 수 부터
     * 두 번째 입력 받은 수 이하 까지
     * 세 번째 입력 받은 수 만큼 증가하여 출력하기
     */
    public void method2() {
        System.out.print("시작 끝 증가값 : ");
        int start = sc.nextInt();
        int end = sc.nextInt();
        int value = sc.nextInt();

        for (int i = start; i <= end; i += value) {
            System.out.print(i + "-");
        }
        System.out.println();
        System.out.println("2========================================");

    }

    /**
     * 두 수를 입력 받아
     * 작은 수 부터 큰 수 이하까지 1씩 증가하며 반복
     */
    public void method3() {
        System.out.print("숫자 2개 입력 : ");

        int a = sc.nextInt();
        int b = sc.nextInt();

        // 1. a와 b를 비교해서 각각 다른 for문을 사용
        if (a < b) {
            for (int i = a; i <= b; i++) {
                System.out.print(i + " ");
            }
        } else {
            for (int i = b; i <= a; i++) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
        System.out.println("a----------");

        // 2. 추가 변수를 이용해서 시작, 종료 값 지정하기
        int start = a;
        int end = b;

        if (a > b) {
            start = b;
            end = a;
        }

        for (int i = start; i <= end; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("b----------");

        // 3. a에 작은 값, b에 큰 값을 저장하게 만들기
        if (a > b) {
            int temp = a;
            a = b;
            b = temp;
        }

        for (int i = a; i <= b; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("c----------");

        // 4. 자바에서 제공하는 기능(메서드) 이용
        int min = Math.min(a, b);   // a,b중 작은 값
        int max = Math.max(a, b);   // a,b중 큰 값

        for (int i = min; i <= max; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("d----------");

        // 5. c언어에서 배워먹은 최고의 최적화 XOR연산
        if (a > b) {
            a = a ^ b;
            b = a ^ b;
            a = a ^ b;
        }
        for (int i = a; i <= b; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("3========================================");
    }


    /**
     * 1부터 100 사이의 정수 중 5의 배수의 합, 개수 구하기
     * sum, count
     */
    public void method4() {
        int sum = 0;
        int count = 0;
        String add = "";
        String correct = "";

        for (int i = 1; i <= 100; i++) {
            if (i % 5 == 0) { // 5의 배수인 경우
                sum += i; // sum에 i값(5의 배수) 누적
                correct += i + "\t";
                add += sum + "\t";
                count++;  // count 1 증가(5의 배수 카운트)
            }
        }
        System.out.println(correct);
        System.out.println(add);
        System.out.printf("합계 : %d  / 개수 : %d", sum, count);
    }

    /**
     * 구구단 3단 출력 하기
     */
    public void method5() {
        System.out.println("[3단]");

        for (int i = 1; i <= 9; i++) {
            System.out.printf("3 X %d = %d\n", i, i * 3);
        }
    }

    /**
     * 입력 받은 단 출력하기
     * 단, 입력 받은 단이 2~9 사이 숫자가 아니면 "잘못 입력 하셨습니다" 출력
     */
    public void method6() {
        System.out.println("단 입력 (2~19) : ");
        int dan = sc.nextInt();
        int count = 0;

        if (dan < 2 || 19 < dan) {
            System.out.println("잘못 입력하셨습니다.");
            return;
        }

        for (int i = 1; i <= 19; i++) {
            if (dan * i < 100) System.out.printf("%d X %d = %d\t\t", dan, i, dan * i);
            else System.out.printf("%d X %d = %d\t", dan, i, dan * i);
            count++;
            if (count > 6) {
                count = 0;
                System.out.println();
            }
        }
    }

    /**
     * while문 기본 작성법
     */
    public void method7() {
        int num = 1;

        // while(조건식) ==> 조건식이 true인 경우에 반복 수행
        while (num <= 10) {
            System.out.print(num + " ");
            num++;
        }
    }

    // for  (초기식; 조건식; 증감식);
    // ==> 지정된 횟수 만큼 반복
    // while(조건식);
    // ==> 조건식이 false가 될 때 까지 계속 반복

    /**
     * [구구단 출력하기 version.2]
     * 입력 받은 단 출력하기
     *
     * 단, 입력 받은 단이 2 ~ 9 사이 숫자가 아니면
     * "잘못 입력 하셨습니다" 출력 후
     * 다시 단 입력 받기
     */
    public void method8() {
        int dan;
        int count = 0;

        while (true) { // 무한 반복
            System.out.println("단 입력 (2~19) : ");
            dan = sc.nextInt();
            if (2 <= dan && dan <= 19) break;
            System.out.println("잘못 입력하셨습니다.");
        }

        for (int i = 1; i <= 19; i++) {
            if (dan * i < 100) System.out.printf("%d X %d = %d\t\t", dan, i, dan * i);
            else System.out.printf("%d X %d = %d\t", dan, i, dan * i);
            count++;
            if (count > 6) {
                count = 0;
                System.out.println();
            }
        }
    }

    /**
     * 이거만 실행하고 나머진 다 꺼버려야지
     */
    public void display() {
        System.out.print(" ? : ");
        switch (sc.nextInt()) {
            case 1: method1(); break;
            case 2: method2(); break;
            case 3: method3(); break;
            case 4: method4(); break;
            case 5: method5(); break;
            case 6: method6(); break;
            case 7: method7(); break;
            case 8: method8(); break;
        }
    }
//==================
}
