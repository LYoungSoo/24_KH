package pkg1.service;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TryCatchService {
    
    private Scanner sc = new Scanner(System.in);
    
    /*
     * Exception(예외) : 코드로 해결 가능한 에러 (if, try-catch, throws 등)
     *
     * Exception Handling(에외 처리) 예외 발생 시 이를 처리하는 구문(try-catch, throws)
     */
    
    /**
     * 고의적으로 예외를 발생시키는 코드 작성
     */
    public void test1() {
        
        // NullPointerException
        // ==> 참조하고 있는 객체가 없는데 객체의 필드/메서드를 호출할 때 던져지는 예외
//        String str = null;
//        str.split("/");
        // Exception in thread "main" java.lang.NullPointerException:
        // Cannot invoke "String.split(String)" because "str" is null
        
        // ArrayIndexOutOfBoundsException
        // - 배열의 index 범위를 초과한 경우 던져지는 예외
//         int[] arr = new int[3];  // index : 0 1 2
//         arr[5] = 10;
        // Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException:
        // Index 5 out of bounds for length 3
        
        // InputMismatchException
        // - 스캐너 입력 시 next자료형()으로 읽어오는 값과 입력된 값의 자료형이 일치하지 않을 때
        System.out.print("정수 입력 : ");
        int input = sc.nextInt();
        // 콘솔 입력 시 문자열/실수/값의 범위 초과 입력
    }
    
    /**
     * try - catch를 이용한 예외 처리
     * <pre>
     * - try { } : 예외가 발생할 가능성이 있는 코드를 { } 내부에 작성하여 시도
     *
     * - catch(예외 참조 변수) { } : try에서 던져진 예외를 ()에 작성된 참조 변수로 잡아서 처리하는 구문
     * </pre>
     */
    public void test2() {
        try {
            System.out.print("정수 입력 1 : ");
            int num1 = sc.nextInt();
            
            System.out.print("정수 입력 2 : ");
            int num2 = sc.nextInt();
            
            System.out.println("합계 : " + (num1 + num2));
            // Exception in thread "main" java.util.InputMismatchException
        } catch (InputMismatchException e) {
            // try에서 던져지게 될 InputMismatchException 객체를 잡아서 처리하는 구문
            
            System.out.println("정수를 입력하라니까 꼭! 말을! 안들어요!");
            // 발생된 예외를 처리했기 때문에 어떤 이유로 어디서 무슨 예외가 발생했는지
            // 콘솔에 빨갛게 뜨는 stackTrace 구문에 출력되지 않음
            
            /* e.printStackTrace()
             * - 예외 발생 원인, 발생된 메서드 추적 결과를 콘솔에 출력하는 메서드
             * ==> 원래 예외 발생하면 나타나는 구문
             */
            e.printStackTrace();
            
            /* String e.getMessage();
             * - 예외 원인이 작성 된 message를 문자열로 반환
             */
            System.out.println(e.getMessage());
        }
    }
    
    public void test3() {
        
        try {
            System.out.print("정수 입력 1 : ");
            int num1 = sc.nextInt();
            System.out.print("정수 입력 2 : ");
            int num2 = sc.nextInt();
            
            System.out.println("몫 : " + (num1 / num2));
            // ==> InputMusmatchException만 처리한 경우,
            // 0으로 나누었을 때 발생하는 ArithmeticExceiption(산술적 예외)를 처리하지 못하여
            // 프로그램이 강제 종료되는 문제 발생
        } catch (InputMismatchException e) {
            System.out.println("int 범위 정수만 입력하라니까 꼭! 말을! 안들어요!");
            e.printStackTrace();
            System.out.println(e.getMessage());
            
        } catch (ArithmeticException e) {
            // 앞에서 처리하지 못한 산술적 예외를 잡아서 처리
            System.out.println("0으로 나눌 수 없음");
            // Exception in thread "main" java.lang.ArithmeticException: / by zero - 산술적 예외
            e.printStackTrace();
            System.out.println(e.getMessage());
            
        } catch (Exception e) {
            System.out.println("슈퍼만능글러브로 모든 에러들을 다 잡아낼수 있음");
            System.out.println("근데 에러 구분은 못함");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        // 발생된 예외를 처리했기 때문에 수행될 수 있는 구문
        System.out.println(">>> 프로그램 종료 <<<");
    }
    
    /**
     * 예외처리 + 다형성(업 캐스팅)
     *
     * RunTimeeException : 실행 중 발생하는 모든 예외의 최상의 부모 예외
     * UncheckedException ==> 예외 처리 구문을 꼭 작성할 필요는 없는 예외
     */
    public void test4() {
        try {
            System.out.print("정수 입력 1 : ");
            int num1 = sc.nextInt();
            
            System.out.print("정수 입력 2 : ");
            int num2 = sc.nextInt();
            
            System.out.println("몫 : " + (num1 / num2));
        }
        // 자식 타입 예외를 먼저 처리(if)
        // ==> 부모 타입을 나머지 예외를 처리하는 (else) 형식으로 사용
        catch (InputMismatchException e) {
            System.out.println("int 범위 정수만 입력해 줘");
        }
        catch (RuntimeException e) {  // 클래스 참조 변수
            
            // catch에 매개 변수 타입을 부모 타입으로 설정!!
            // ==> 던져지는 모든 자식 타입의 예외를 잡아서 처리
            // ==> 예외 처리에 다형성(업 캐스팅) 적용
            
            // 장점 : 여러 예외처리 구문을 작성하지 않고도 예외 처리가 가능
            // 단점 : 발생하는 예외 별 처리 방법을 제시할 수 없다
            
            System.out.println("뭔지 모르겠지만 예외 발생");
        }
        
        // InputMismatchException에 빨간줄(컴파일 에러)이 발생하는 이유
        // ==> 앞에서 부모 타입 참조 변수를 이용해 자식 타입 예외를 잡아서 처리했기 때문에
        //      도달할 수 없는 코드(dead code)가 되어버림!
        
        // 해결 방법 : 부모 처리 구문보다 앞에 작성하면 해결!
//        catch (InputMismatchException e) {
        // Exception 'java.util.InputMismatchException' has already been caught
//            System.out.println("int 범위 정수만 입력"); }
    }
    
    /**
     * catch 문에 다형성 적용 복습 + 예외 강제 발생
     *
     * - throw new 예외 클래스(); ==> 생성되어 예외 객체를 던짐 == 예외 강제 발생
     */
    public void test5() {
        
        // Exception 클래스 : 모든 예외의 최상위 부모
        
        System.out.println("--- 던질 예외 선택 ---");
        System.out.println("1. Exception");
        System.out.println("2. RuntimeException");
        System.out.println("3. ArithmeticException");
        try {
            int input = sc.nextInt();
            switch (input) {
                case 1: throw new Exception();
                case 2: throw new RuntimeException();
                case 3: throw new ArithmeticException();
//                break; ==> Unreachable statement
                default: break;
            }
        } catch (InputMismatchException e) {
            System.out.println("int 정수만 입력해 주세요");
        } catch (ArithmeticException e) {
            System.out.println("ArithmeticException 예외 처리 완료");
        } catch (RuntimeException e) {
            System.out.println("RuntimeException 예외 처리 완료");
        } catch (Exception e) {
            System.out.println("Exception 예외 처리 완료");
        }
    }
    
    /**
     * Checked / Unchecked Exception 확인
     *
     * 1) Checked Exception
     * - 반드시 예외 처리 구문을 작성해야 되는 예외
     * - 예외 처리 구문 미작성 시 컴파일 에러 발생(빨간줄)
     *
     * 2) Unchecked Exception
     * - 예외 처리 구문 작성 여부가 필수가 아닌 선택하는 예외
     * - 예외 처리 구문을 작성하지 않아도 컴파일 에러 발생 X
     * - 대부분의 Unchecked Exception은 if문으로 해결 가능 (사용자, 이용자의 실수 방지)
     */
    public void test6() {
       
        // Unchecked Exception 확인
        // ==> 컴파일 에러 없음!!
//        throw new RuntimeException();
        
        // Checked Exception 확인
        // ==> 컴파일 에러(빨간줄) 발생!!
//        throw new Exception();    // ==> 예외 처리 코드 작성 필요
            // Unhandled exception: java.lang.Exception
            // ==> 예외 처리 안했다!
    }
    
}
