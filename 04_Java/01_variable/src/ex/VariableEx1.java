package ex;

/* 자바 코드 실행 방법
 * 1) 위에 Run 버튼(▶)
 * 2) ctrl + f11
 * 
 * System.out.println() 자동완성
 * sysout ==> ctrl + space ==> enter
 * */

public class VariableEx1 {
	/**[main mathod]
	 * <p>
	 * 자바 프로그램을 실행 시키기 위한 구문 (기능)
	 * </p>
	 * */
	public static void main(String[] args) {
		// 변수 : 메모리에 값을 저장하는 공간
		//		변수에 저장된 값은 바꿀 수 있음
		
		/* 자료형 (Data type)
		 *  - 변수에 저장되는 값의 종류/크기
		 * 
		 * [Java 기본 자료형 8가지]
		 * - 논리형 -
		 * boolean (1byte) - true / false
		 * - 정수형 -
		 * byte		(1 byte)
		 * short	(2 byte)
		 * int 		(4 byte) - 정수 기본형(123 ==> int로 인식) (Default) (int를 기준으로 short / long)
		 * long		(8 byte) - 숫자 뒤에 영문자 l 또는 L 을 붙임 (123L)
		 * 
		 * - 실수형 -
		 * float	(4 byte) - 숫자 뒤에 f 또는 F (3.14f)
		 * double	(8 byte) - 실수 기본형
		 * 
		 * - 문자형 -
		 * char		(2 byte) - 문자 하나를 의미, 'A' 형식으로 작성
		 */
		
		// 변수 선언 : 메모리에 값을 저장할 공간을 만듦(== 할당)
		
		int num1; 	// 메모리에 int형 데이터(정수)를 저장하기 위한 공간 4byte를 할당하고
					// 변수 이름을 num1 이라 지정		
		
		// 자바는 초기화(처음 값 대입) 되지 않은 지역변수를 출력, 연산할 수 없다! (대입 연산만 가능)
		// System.out.println(num1);
		// The local variable num1 may not have been initialized ==> num1 변수가 초기화가 되지 않음
		
		// 변수에 값 대입
		num1 = 2000;
		System.out.println(num1); // 값이 대입되면 오류 X
		
		System.out.println("----------------------------------------------------------------------");
		System.out.println("[자료형별 리터럴 작성법]");
		
		// 논리형 (1byte)
		boolean isTrue = true;
		boolean isFalse = false;
		
		// 문자열 이어쓰기
		System.out.println("isTrue : " + isTrue);
		System.out.println("isFalse : " + isFalse);
		
		// JavaScript에서는 true == 1 비교 가능하지만 Java는 안됨!
		// Why? 자료형이 같지 않아서 비교 불가
//		 System.out.println(true=1);
		
		// [정수형]
		int intNum = 2147483647;
		// int intNum2 = 2147483648;	// The literal 2147483648 of type int is out of range 
		
//		long longNum = 2147483648;		// The literal ~ of type int is out of range. 일반 정수값 == int(기본형)
		long longNum = 2147483648L;		// 뒤에 , L == long 표기
		System.out.println("intNum : " + intNum);
		System.out.println("longNum : " + longNum);
		// longNum 뒤에 L 안붙어서 출력됨 ==> 코드에만 리터럴 표기법 작성 / 출력시 미표기됨
		
		// [실수형]
		
//		float floatNum = 1.23;			// Type mismatch: cannot convert from double to float
		float floatNum = 1.23f;
		// Type mismatch: cannot convert from double to float ==> float 변수에 double 값을 저장할 수 없다.
		// 왜? 실수 기본형은 double (1.23처럼 보통의 실수 표기법이 double로 인식됨)
		
		double doubleNum = 3.14;
		
		System.out.println("floatNum : " + floatNum);
		System.out.println("doubleNum : " + doubleNum);
		
		// [문자형]
		char ch1 = 'A'; 	// 2byte, 리터럴 표기법 ('')
		char ch2 = '가';
		char ch3 = '☎';
		char ch4 = '李';
		
//		char ch5 = 'ab';		// Invalid character constant
//		char ch5 = "ab";		// Type mismatch: cannot convert from String to char
		
		System.out.println("ch1 : " + ch1);
		System.out.println("ch2 : " + ch2);
		System.out.println("ch3 : " + ch3);
		System.out.println("ch4 : " + ch4);
		
		System.out.println("==================================================");
		
		System.out.println("[float와 double의 차이점]");

		/* float(4 byte), double(8 byte)
		 * byte 크기가 float의 2배라서 double이 아님!!
		 * 정확도가 2배 = 소수점 표현 자릿수가 2배라서 double!!	
		*/		
		
		float test1 = 0.1234567890123456789f;	// float  : 0.12345679			==> 6~8자리
		float test2 = 1.1234567890123456789f;	// float  : 4.123457			==> 7~8자리
		float test3 = 9.1234567890123456789f;	// float  : 9.123457			==> 7~8자리
		double test6 = 0.1234567890123456789;	// double : 0.12345678901234568	==> 15~17자리
		double test7 = 1.1234567890123456789;	// double : 4.123456789012345	==> 15~17자리
		double test8 = 9.1234567890123456789;	// double : 9.123456789012346	==> 15~17자리
		
		System.out.println("float : " + test1);
		System.out.println("float : " + test2);
		System.out.println("float : " + test3);
		System.out.println("double : " + test6);
		System.out.println("double : " + test7);
		System.out.println("double : " + test8);
		
		
		//========================================
		
		/* String(문자열)
		 * - 기본 자료형(8가지)에 포함되지 않음!!
		 * 
		 * - 기본 자료형 O : 변수에 직접 값을 저장
		 * - 기본 자료형 X : 변수에 값의 위치(주소)를 저장 (pointer) == 참조형
		 * */
		
		String str1 = "문자열 리터럴 표기법은 쌍따옴표";
		String str2 = "String은 주소를 저장하고 찾아가는 참조형";
		String str3 = "맨앞글자 대문자면 기본 자료형이 아니라는거임";
		System.out.println(str1);
		System.out.println(str2);
		System.out.println(str3);
	}

}
