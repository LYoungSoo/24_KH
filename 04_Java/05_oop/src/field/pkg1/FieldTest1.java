package field.pkg1;

/* 필드(Field)
 * - 클래스 바로 아래에 작성하는 변수 == 객체의 속성을 의미
 *
 * - 필드 ( ==맴버 변수) 구성
 * 1) 인스턴스 변수
 * 2) 클래스 변수 ( == static 변수)
 *
 *
 * [[ 필드 접근 제한자 ]]
 *
 *  1) [+] public       : 같은 프로젝트 내에서 어디서든 접근 가능
 *  - public 필드         ==> 같은 프로젝트 내 어디서든 해당 클래스를 이용해 만들어진 객체의 public 필드 직접 접근 가능
 *
 *  2) [#] protected    : 같은 패키지 또는 상속 관계에서만 접근 가능
 *  - protected 필드      ==> 기본적으로 같은 패키지에서만 직접 접근이 가능하지만
 *                              다른 패키지에 존재하는 클래스 중 상속 관계의 클래스에서는 직접 접근 가능
 *
 *  3) [~] (default)    : 같은 패키지 내에서만 접근 가능
 *  - (default) 필드      ==> 같은 패키지 내에서 해당 클래스를 이용해 만들어진 객체의 (default) 필드 직접 접근 가능
 *
 *  4) [-] private      : 같은 클래스(해당 객체) 만 접근 가능
 *  - private 필드        ==> 해당 클래스를 이용해 만들어진 객체 내부에서 사용 가능
 *                              (무슨 일이 있어도 외부에서 직접 접근이 불가능한 필드)
 *
 */

public class FieldTest1 {
    // [[ 필드 작성법 ]]
    // [접근 제한자] [예약어] 자료형 변수명 [= 초기값];
    public int publicValue = 100;
    protected int protectedValue = 200;
    int defaultValue = 300;
    private int privateValue = 400;
    
    // 같은 클래스(해당 객체) 내부에서 필드 접근
    // ==> 모든 접근 제한자가 가능
    public void method () {
        System.out.println(publicValue);
        System.out.println(protectedValue);
        System.out.println(defaultValue);
        System.out.println(privateValue);
    }
}
