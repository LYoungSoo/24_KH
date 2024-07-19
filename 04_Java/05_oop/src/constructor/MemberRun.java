package constructor;

public class MemberRun {
    public static void main (String[] args) {
        
        // Member 객체 생성 1 : 생성 후 setter로 값 초기화
        Member mem1 = new Member();
        //              Member 기본 생성자 ==> member 클래스에 작성되어 있음
        
        // mem1이 참조하는 객체의 setMemberId() 메서드 호출
        mem1.setMemberId("member01");
        mem1.setMemberPw("pass01");
        mem1.setMemberName("김회원");
        mem1.setMemberAge(27);
        
        mem1.inform();  // mem1이 참조하는 객체 필드 값
        
        // --------------------------------------------------
        
        // Member 객체 생성 2 : 매개 변수 생성자 이용
        Member mem2 = new Member("member02", "pass02", "최회원", 22);
        // ==> 객체가 만들어지면서 동시에 생성자에 의해서 필드 값이 초기화 된다!!!
        // setter 방법 5줄 ==> 생성자 방법 1줄
        mem2.inform();
        // ** 매개 변수 생성자로 얻은 효과 **
        // ==> 코드 길이 감소(== 재사용성 증가)
        
        // --------------------------------------------------
        
        // this() 생성자 확인
        Member mem3 = new Member();
        mem3.inform();
    }
}
