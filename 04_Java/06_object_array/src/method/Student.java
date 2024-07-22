package method;

// class : 객체(속성, 기능)를 정의하는 문서 (== 설계도)
public class Student {
    
    /* 필드(== 맴버 변수)*/
    // 필드 1 - class변수(== static 변수) ==> 클래스명.변수명 형태로 호출하기 때문에 class 변수
    public static String schoolName = "KH대학교";
    public static final int MIN_VALUE = 0;      // 스킬 역량 최소값
    public static final int MAX_VALUE = 100;    // 스킬 역량 최대값
    
    // 필드 2 - instance 변수 ==> new 연산자에 의해서 생성된 객체(instance)의 변수
    private String name;            // 이름
    private String studentNumber;   // 학번
    private char gender;            // 성별
    private int java;               // 자바 역량 점수
    private int html;               // html 역량 점수
    
    /* 생성자 */   // constructor
    // - new 연산자에 의해 객체 생성 시 실행되는 메서드(기능)
    public Student () {}     // 기본 생성자
    
    // (오버로딩 적용) 매개 변수 생성자
    public Student (String name, String studentNumber, char gender) {
        this.name = name;
        this.studentNumber = studentNumber;
        this.gender = gender;
    }
    /* 메서드(== 맴버 메서드) */
    // getter / setter
    
    /* 반환형 : 메서드 호출 시 반환되는 값의 자료형 */
    public String getName () {
        return name;
    }
    /* 매개 변수 : 메서드 호출 시 전달된 값을 저장하는 변수 */
    public void setName (String name) {
        this.name = name;
        // this 참조 변수 : 생성된 객체 자체를 참조하는 변수(이 객체!)
        
        // 반환형 void : return 옆에 반환할 값이 없다
        // return; // 미작성 시 컴파일러가 자동 추가
    }
    
    public String getStudentNumber () {
        return studentNumber;
    }
    public void setStudentNumber (String studentNumber) {
        this.studentNumber = studentNumber;
    }
    
    public char getGender () {
        return gender;
    }
    public void setGender (char gender) {
        this.gender = gender;
    }
    
    public int getJava() {
        return java;
    }
    public void setJava (int java) {
        this.java = java;
    }
    
    public int getHtml () {
        return html;
    }
    public void setHtml (int html) {
        this.html = html;
    }
    
    // getter / setter 자동완성(eclipse) : alt + shift + s ==> r (static은 체크할 필요가 없고)
    // 이거 몰라서 jsp할때 두시간동안 개뻘짓했지
    
    // 객체의 필드 값을 문자열로 반환하는 메서드
    public String toString () {
        
        // 240001 / 홍길동 / 남 / java : 80 / html : 70
        // String String.format("패턴", 값) ==> 패턴 형태의 문자열을 반환하는 메서드
        // (printf) 와 비슷한데 (printf)는 출력, String.format()은 문자열을 만들어서 반환
        return String.format("%s / %s / %c / java = %d / html = %d", studentNumber, name, gender, java, html);
    }
}
