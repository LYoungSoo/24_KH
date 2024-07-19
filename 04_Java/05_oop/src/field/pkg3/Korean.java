package field.pkg3;

/* [[static]]
 *
 * - 뜻 : 정적인(움직임이 없다)
 * - static 붙은 필드/메서드는 프로그램 실행시 모두 static 영역에 할당되고 종료 시 사라지게 됨
 *  a) 프로그램 실행 중에는 절대 없어지지 않음 == 정적
 *      <-> 동적 : 객체가 생겼다/사라졌다 함 ===> Heap 영역
 *
 *  b) static의 특징 : 공유
 *  - static 붙은 필드 / 에서드는 '프로그램 실행 시' 클래스명.필드명 / 클리스명.메서드명() 이름으로
 *    static 영역에 할당(생성)되어지고, 어디서든 클래스명.필드명 / 클래스명.메서드명() 으로 호출 가능한 상태가 됨!
 *
 */

public class Korean {
    
    // 필드
    
    // static 변수 ( == 클래스 변수 ) ==> 일반적으로 어디서든 접근 가능하게 public을 작성
    public static int nationalCode = 82;
    
    // 인스턴스 변수
    // ==> 만들어진 인스턴스(객체)만 사용 가능한 변수
    // ==> 캡슐화 원칙에 의해서 private 설정
    private String name;    // 이름
    private String id;      // 주민 등록 번호
    
    // private 필드 간접 접근 방법 (getter/setter)
    
    // name의 getter
    public String getName() {
        return name;
    }
    
    // id의 getter
    public String getId() {
        return id;
    }
    
    // name의 setter
    public void setName(String name) {
        // 매개 변수 : 전달 받은 값을 저장하는 변수
        // this : (해당 클래스) 해당 객체
        this.name = name;
    }
    
    // id의 setter
    public void setId (String id) {
        this.id = id;
    }
    
}
