package condition.ex;

import java.util.Scanner;

/**
 * 조건문 예시 기능용 클래스
 */
public class ConditionEx {

    // 필드(field) == 맴버 변수 == 인스턴트 변수 (전역 변수 비슷)
    Scanner sc = new Scanner(System.in);

    /**
     * 1 ~ 10 사이 난수가 짝수인지 홀수인지 출력
     */
    public void method1() {
        int ran = (int) (Math.random() * 10 + 1);

        if (ran % 2 != 1) {
            System.out.println(ran + "는 짝수 입니다.");
        } else {
            System.out.println(ran + "는 홀수 입니다.");
        }
    }

    /**
     * 나이를 입력 받아
     * 13세 이하 : 어린이
     * 14세 이상 19세 이하 : 청소년
     * 20세 이상 : 성인
     *
     * 구분하여 출력하기
     */
    public void method2() {
        System.out.print("나이 입력 : ");
        int age = sc.nextInt();
        String result;

        if (age < 0) {
            result = "음수는 입력할 수 없습니다";
        } else if (age <= 13) {
            result = "어린이";
        } else if (age <= 19) {
            result = "청소년";
        } else if (age >= 20) {
            result = "성인";
        } else result = "오류";

        System.out.println(result);
    }

    /**
     * 나이를 입력 받아
     * 13세 이하 : 어린이
     * 14세 이상 19세 이하 : 청소년
     * - 14~16 : 청소년(중)
     * - 17~19 : 청소년(고)
     * 20세 이상 : 성인
     * 0이하 또는 100 초과 : 잘못 입력하셨습니다
     *
     * 구분하여 출력하기
     */
    public void method3() {
        System.out.print("나이 입력 : ");
        int age = sc.nextInt();
        String result;

        if (age <= 0 || 100 < age) result = "잘못 입력하셨습니다.";
        else if (age <= 13) result = "어린이";
        else if (age <= 19) result = "청소년" + ((age<=16) ? "(중)" : "(고)");
        else result = "성인";

        System.out.println(result);
    }


    /** [switch]
     *  입력된 달(월)의 계절 출력하기
     */
    public void method4() {
        System.out.print("달(월) 입력 : ");
        int month = sc.nextInt();
        String result;

        switch (month) {
            // case를 연속해서 작성하는 여러 방법
            // 1) 세로로 쭉
            case 3:
            case 4:
            case 5: result = "봄"; break;

            // 2) 가로로 쭉
            case 6: case 7: case 8: result = "여름"; break;

            // 3) case 값, 값, 값 형식
            case 9, 10, 11 : result = "가을"; break;
            case 12,1,2 : result = "겨울"; break;
            default: result = "잘못 입력";
        }   // switch end

/*        switch (month/3) {
            case 0: result = "겨울"; break;
            case 1: result = "봄"; break;
            case 2: result = "여름"; break;
            case 3: result = "가을"; break;
            case 4: result = "겨울"; break;
            default: result = "뭘 입력한거니";
        }
*/
        System.out.println(result);
    }

    /** [성적 판별기]
     *  중간고사, 기말고사, 과제 점수를 입력 받아 성적을 부여
     *  - 중간고사(40%), 기말고사(50%), 과제(10%)
     *  - 입력 시 각각 100점 만점으로 입력 받음
     *  - 합산된 점수에 따라 성적 부여
     *
     *  95점 이상  : A+
     *  90점 이상  : A
     *  85점 이상  : B+
     *  80점 이상  : B
     *  75점 이상  : C+
     *  70점 이상  : C
     *  65점 이상  : D+
     *  60점 이상  : D
     *  나머지     : F
     *
     * //내맘대로 결석 6번부터 출석미달 F
     *
     *  [실행 화면]
     *  이름 : 홍길동
     *  중간고사 점수(40%) : 100
     *  기말고사 점수(50%) : 80
     *  과제 점수(10%) : 50
     *
     *  최종 점수 : 85점
     *  성적 : B+
     */
    public void method5() {
    	
        int exam;
        double score = 0;
        String grade;
        int absent =0;

        System.out.print("이름 : ");
        String name = sc.nextLine();

        do {

            System.out.print("결석 횟수 입력 : ");
            absent = sc.nextInt();

            if (absent <0 || absent > 15) System.out.println("15 이하의 양수로 입력할것");
        } while (absent <0 || absent > 15);


        do {

            System.out.print("중간고사 점수를 입력하세요 : ");
            exam = sc.nextInt();

            if (exam <0 || exam > 100) System.out.println("점수는 0~100점 사이");
        } while (exam <0 || exam > 100);
        score += ((double) exam * 0.4);
        System.out.println(score);

        do {

            System.out.print("기말고사 점수를 입력하세요 : ");
            exam = sc.nextInt();

            if (exam <0 || exam > 100) System.out.println("점수는 0~100점 사이");
        } while (exam <0 || exam > 100);
        score += ((double) exam * 0.5);
        System.out.println(score);

        do {

            System.out.print("과제 점수를 입력하세요 : ");
            exam = sc.nextInt();

            if (exam <0 || exam > 100) System.out.println("점수는 0~100점 사이");
        } while (exam <0 || exam > 100);
        score += ((double) exam * 0.1);
        System.out.println(score);

        switch ((int)score/10) {
            case 10, 9 : grade = "A"; break;
            case 8 : grade = "B"; break;
            case 7 : grade = "C"; break;
            case 6 : grade = "D"; break;
            default: grade = "F";
        }
        if (score > 60 && score % 10 >= 5) grade += "+";
        /*
        grade = switch ((int) (score / 5)) {
            case 20, 19 -> "A+";
            case 18 -> "A";
            case 17 -> "B+";
            case 16 -> "B";
            case 15 -> "C+";
            case 14 -> "C";
            case 13 -> "D+";
            case 12 -> "D";
            default -> "F";
        };
         */
        if (absent>=6) grade = "F(출석미달)";
        System.out.println(name + "의 최종 점수 : " + score + "점, " + grade + " (println)");
        System.out.printf("%s의 최종 점수 : %.1f점, %s (printf)",name, score, grade);
    }

    // 방법 1
	/*
	if(sum >= 95) result = "A+";
	else if(sum >= 90) result = "A";
	else if(sum >= 85) result = "B+";
	else if(sum >= 80) result = "B";
	else if(sum >= 75) result = "C+";
	else if(sum >= 70) result = "C";
	else if(sum >= 65) result = "D+";
	else if(sum >= 60) result = "D";
	else  result = "F";
	*/

    // 방법 2
	/*
	if(sum >= 90) result = "A" + ( sum>=95 ? "+" : "" );
	else if(sum >= 80) result = "B" + ( sum>=85 ? "+" : "" );
	else if(sum >= 70) result = "C" + ( sum>=75 ? "+" : "" );
	else if(sum >= 60) result = "D" + ( sum>=65 ? "+" : "" );
	else  result = "F";
	*/

    // 방법 3
	/*
	switch( (int)sum/5 ) {
	case 20, 19 : result = "A+"; break;
	case 18 : result = "A"; break;
	case 17 : result = "B+"; break;
	case 16 : result = "B"; break;
	case 15 : result = "C+"; break;
	case 14 : result = "C"; break;
	case 13 : result = "D+"; break;
	case 12 : result = "D"; break;
	default : result = "F";
	}
	*/


    /** [switch를 이용한 메서드 호출]
     */
    public void displayMenu() {
        System.out.println("1. method1() - 난수 홀짝");
        System.out.println("2. method2() - 나이 구분 1");
        System.out.println("3. method3() - 나이 구분 2");
        System.out.println("4. method4() - 계절 찾기");
        System.out.println("5. method5() - 성적 판별기");

        System.out.print("번호 선택 >> ");
        int num = sc.nextInt();

        System.out.println("============================================================");

        switch (num) {
            case 1: method1(); break;
            case 2: method2(); break;
            case 3: method3(); break;
            case 4: method4(); break;
            case 5: method5(); break;
            default:    System.out.println("잘못 입력하셨습니다.");
        }
    }

    /** [연습문제]
     *  국어, 영어, 수학, 사탐, 과탐 점수를 입력받아
     *  40점 미만 과목이 있으면 FAIL
     *  평균이 60점 미만인 경우도 FAIL
     *  모든 과목 40점 이상, 평균 60점 이상인 경우 PASS
     *
     *  [출력 예시]
     *  점수 입력(국 영 수 사 과) : 100 50 60 70 80
     *
     *  1) 40점 미만 과목이 존재하는 경우
     *  FAIL [40점 미만 과목 : 국어 영어]
     *
     *  2) 평균 60점 미만인 경우
     *  FAIL [점수 : 50.4 (평균 미달)]
     *
     *  3) PASS인 경우
     *  PASS [점수 : 83.4 / 100]
     */
    public void practice() {

        int kor = 0;
        int eng = 0;
        int mat = 0;
        int soc = 0;
        int sci = 0;
        double avg;
        String subject="";
        String result;

        System.out.println("점수 5개를 한번에 입력해 주십시오");

        do {
            if ( kor < 0 || 100 < kor || eng < 0 || 100 < eng ||
                 mat < 0 || 100 < mat || soc < 0 || 100 < soc || sci < 0 || 100 < sci)
                System.out.println("점수는 0 ~ 100 사이로 입력해 주십시오");
            kor = sc.nextInt();
            eng = sc.nextInt();
            mat = sc.nextInt();
            soc = sc.nextInt();
            sci = sc.nextInt();
        } while ( kor < 0 || 100 < kor || eng < 0 || 100 < eng ||
                  mat < 0 || 100 < mat || soc < 0 || 100 < soc || sci < 0 || 100 < sci);

        avg = (kor + eng + mat + soc + sci) /5;
        result = "PASS [점수 : "+ avg +" / 100]";
        if (avg < 60.0) result = "FAIL [점수 : "+ avg +" (평균 미달)]";
        if (kor < 40) subject += " 국어";
        if (eng < 40) subject += " 영어";
        if (mat < 40) subject += " 수학";
        if (soc < 40) subject += " 사회";
        if (sci < 40) subject += " 과학";
        if (subject.length() > 0) result = "FAIL [40점 미만 과목 : " + subject + "]";

        System.out.println(result);


        /*
        public void practice() {
		System.out.print("점수 입력(국 영 수 사 과) : ");

		int kor = sc.nextInt(); // 국
		int eng = sc.nextInt(); // 영
		int mat = sc.nextInt(); // 수
		int soc = sc.nextInt(); // 사
 		int sci = sc.nextInt(); // 과


 		// 40점 미만인 과목 검사
 		boolean flag = false;

 		String str = ""; // 빈칸(자료형 : String, 내용 X)

 		if(kor < 40) {
 			flag = true;
 			str += "국어 ";
 		}

 		if(eng < 40) {
 			flag = true;
 			str += "영어 ";
 		}

 		if(mat < 40) {
 			flag = true;
 			str += "수학 ";
 		}

 		if(soc < 40) {
 			flag = true;
 			str += "사회 ";
 		}

 		if(sci < 40) {
 			flag = true;
 			str += "과학 ";
 		}


 		// 40점 미만 과목이 존재하는 경우
 		if(flag) {
 			System.out.printf("FAIL [40점 미만 과목 : %s]", str);
 			return;
 		}

 		// 평균(double형 결과를 반환 받기 위해 5.0으로 나눔)
 		double avg = (kor + eng + mat + soc + sci) / 5.0;

 		if(avg < 60.0) {
 			System.out.printf("FAIL [점수 : %.1f (평균 미달)]", avg);
 			return; //  Early return; (중간에 메서드를 종료)
 		}

 		System.out.printf("PASS [점수 : %.1f / 100]", avg);
	}

        */
    }

}