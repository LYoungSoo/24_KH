package loop.ex;

import java.util.Scanner;

public class LoopEx2 {
    
    // 필드 (전역 변수 비슷)
    Scanner sc = new Scanner(System.in);
    
    /**
     * 다음 모양 출력하기
     * 1234
     * 1234
     * 1234
     */
    public void method1 () {
        for (int i = 1; i <= 3; i++) {
            for (int k = 1; k <= 4; k++) {
                System.out.print(k);
            }
            System.out.println();
        }
    }
    
    /**
     * 다음 모양 출력하기
     * <p>
     * 54321
     * 54321
     * 54321
     * 54321
     */
    public void method2 () {
        for (int i = 1; i <= 4; i++) {
            for (int k = 5; k >= 1; k--) {
                System.out.print(k);
            }
            System.out.println();
        }
    }
    
    /**
     * 행, 열, 정방향(1) / 역방향(1) 입력 받아 출력
     * <p>
     * 행 입력 : 3
     * 열 입력 : 6
     * 정방향(1) / 역방향(-1) : -1
     * 654321
     * 654321
     * 654321
     */
    public void method3 () {
        int row, col, direction;
        
        System.out.print("행 입력 ==> ");
        do {
            row = sc.nextInt();
            if (row < 1) System.out.println("양수만 입력");
        } while (row < 1);
        
        System.out.print("열 입력 ==> ");
        do {
            col = sc.nextInt();
            if (col < 1) System.out.println("양수만 입력");
        } while (col < 1);
        
        System.out.println("정방향, 역방향 입력 (1, -1)");
        do {
            direction = sc.nextInt();
            if (direction != 1 && direction != -1) System.out.println("1, -1 만 입력");
        } while (direction != 1 && direction != -1);
        
        for (int i = 0; i < row; i++) {
            
            if (direction == 1) {
                for (int k = 1; k <= col; k++) {
                    System.out.print(k);
                }
            } else if (direction == -1) {
                for (int k = col; k > 0; k--) {
                    System.out.print(k);
                }
            }
            
            System.out.println();
        }
        System.out.println("==================================================");
        
        // 정방향 초기화
        int start = 1;
        int end = col;
        
        if (direction == -1) {   // 역방향
            start = col;
            end = 1;
        }
        
        for (int i = 0; i < row; i++) {
            for (int k = start; ; k += direction) {
                if (direction == 1 && k <= end) {
                    System.out.print(k);
                    continue;
                }
                
                if (direction == -1 && k >= end) {
                    System.out.print(k);
                    continue;
                }
                break;
            }
        }
    }
    
    /**
     * 다음 모양 출력 하기
     * <p>
     * (0,0) (0,1) (0,2)
     * (1,0) (1,1) (1,2)
     * (2,0) (2,1) (2,2)
     */
    public void method4 () {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                System.out.printf("(%d,%d)", row, col);
            }
            System.out.println();
        }
    }
    
    /**
     * 다음 모양 출력 하기
     * <p>
     * (0,0)
     * (1,0) (1,1)
     * (2,0) (2,1) (2,2)
     */
    public void method5 () {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col <= row; col++) {
                System.out.printf("(%d,%d)", row, col);
            }
            System.out.println();
        }
    }
    
    /**
     * 다음 모양 출력 하기
     *
     * (0,0) (0,1) (0,2)
     * (1,0) (1,1)
     * (2,0)
     */
    public void method6 () {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3 - row; col++) {
                System.out.printf("(%d,%d)", row, col);
            }
            System.out.println();
        }
    }
    
    
    /**
     * [do ~ while]
     * - 최소 1회 이상 반복하는 while문
     */
    public void method7 () {
        
        int num = 1;
        do {
            System.out.println("반복 출력");
            num++;
        } while (num < 10);
        
        System.out.println("--- 종료 ---");
    }
    
    
    /**
     * 0이 입력될 때 까지 입력된 모든 수의 합
     */
    public void method8 () {
        
        int input;// 숫자를 입력 받을 변수
        
        int sum = 0; // 합계
        
        do {
            System.out.print("숫자 입력 : ");
            input = sc.nextInt();
            sum += input;
            
        } while (input != 0);
        
        System.out.println("합계 : " + sum);
        
    }
    
    
    /**
     * ========================================
     */
    public void display () {
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
}