package ex;

//import : 다른 패키지에 존재하는 클래스를 가져오는 구문
import java.util.Scanner;


public class ScannerEx {

 // main method : 자바 실행 메서드(기능)
 public static void main(String[] args) {
     /*  Scanner 클래스
         - 사용자로부터 입력을 받기 위해 제공하는 클래스

         - java.util 패키지에 속해 있음
      */

     // new 연산자 : 메모리(heap)에 새로운 객체를 만들겠다는 의미
     // System.in : 지정된 입력 장치((이클립스)기본값 키보드)

     // new Scanner(System.in)
     // - 키보드 입력을 읽어들이는 Scanner 객체 생성
     Scanner sc = new Scanner(System.in);
     System.out.print("정수 입력 : ");

     // sc.nextInt();
     // - 다음에 입력된 정수를 반환
     // - 단, 정수가 아닌 값을 입력하면 예외(Exception) 발생
     int i = sc.nextInt();
     System.out.println("i : " + i);

     System.out.print("정수 입력2 : ");
     int j = sc.nextInt();
     System.out.println("j : " + j);

     System.out.println("i + j = " + (i + j));       //개똑똑해졌다잉


 }
}