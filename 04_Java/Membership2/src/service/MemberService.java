package service;

// Service : 기능(비즈니스 로직) 제공 클래스/객체
// - 프로그램의 핵심 기능 작성

import dto.Member;

import java.io.IOException;
import java.util.List;

public interface MemberService {
    
    // 인터페이스의 메서드
    // - public abstract method(중요)
    // - default method
    
    /**
     * 전달 받은 이름, 휴대폰 번호를 이용해서 회원 추가
     * 단, 목록에 있는 회원 중 같은 번호의 회원이 존재하면 false를 반환 / 없으면 가입 후 true 반환
     * @param name
     * @param phone
     * @return
     */
    public abstract boolean addMember(String name, String phone) throws IOException;
    // Modifier 'abstract' is redundant for interface methods
    // interface라서 모든게 public abstract이라 의미가 없다! 안써도 자동으로 달린다!
    
    /**
     * 전체 회원 목록 조회
     * @return
     */
    public abstract List<Member> getMemberList();
    
    /**
     * searchName과 같은 이름을 지닌 회원 조회
     * - 돔영이인이 존재하면 모두 조회
     * @param searchName
     * @return searchList (저장된 요소 0개 이상)
     */
    List<Member> selectName(String searchName);
    
    /**
     * 전달 받은 회원의 금액 누적하기
     * @param target
     * @param acc
     * @return 결과 문자열
     */
    String updateAmount(Member target, int acc) throws IOException;
    
    /**
     * 전달 받은 회원의 전화번호 수정하기
     * @return 결과 문자열
     */
    String updateMember(Member target, String changePhone) throws IOException;
    

}