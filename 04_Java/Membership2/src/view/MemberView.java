package view;

import dto.Member;
import service.MemberService;
import service.MemberServiceImpl;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

// View : 사용자에게 보여지는 역할을 하는 클래스/ 객체
// - 보여줄 화면을 출력 / 필요한 데이터를 입력

public class MemberView {
    
    private MemberService service = null;
    private BufferedReader br = null;
    
    // 기본 생성자
    public MemberView() {
        
        try {
            // 객체 생성 중 발생한 예외를 View에 모아서 처리
            service = new MemberServiceImpl();
            
            // 키보드를 입력 받기 위한 스트림 생성
            br = new BufferedReader(new InputStreamReader(System.in));
            
        } catch (Exception e) {
            System.out.println("*** 프로그램 실행 중 오류 발생 ***");
            e.printStackTrace();
            System.exit(0); // 프로그램 종료  // 이거 c언어 return 0; 으로 종료할떄 호출되는거
        }
        
    }   // MemberView()
    
    // ----------------------------------------------------------------------------------------------------
    // [메인 메뉴]
    public void mainMenu() {
        
        int input = 0;
        do {
            try {
                // 메뉴 출력 후 입력된 번호를 반환 받기
                input = selectMenu();
                
                // 선택된 메뉴 번호에 따라 case 선택
                switch (input) {
                    case 1: addMember(); break;
                    case 2: selectAll(); break;
                    case 3: selectName(); break;
                    case 4: updateAmount(); break;
                    case 5: updateMember(); break;
                    case 6: break;
                    case 0: System.out.println("*** 프로그램 종료 ***");
                        break;
                    default: System.out.println("### 메뉴에 작성된 번호만 입력 해주세요! ###");
                }
                
                System.out.println("==================================================");
                
            } catch (NumberFormatException e) {
                System.out.println("\n### 숫자만 입력해주세요 ###\n");
                input = -1;             // 첫 반복에서 종료되지 않게 값 변경(에러가 발생할 경우 계속 반복)
            } catch (IOException e) {
                System.out.println("\n### 입출력 관련 예외 발생 ###\n");
                e.printStackTrace();    // 예외 추적
                
            } catch (Exception e) {     // 나머지 예외 처리
                e.printStackTrace();
            }
            
        } while (input != 0);
    }
    
    // ----------------------------------------------------------------------------------------------------
    // [메뉴 출력 / 선택하는 메서드]
    private int selectMenu() throws NumberFormatException, IOException {
        
        System.out.println("\n===== 회원 관리 프로그램 =====\n");
        System.out.println("1. 회원 가입(추가)");
        System.out.println("2. 회원 전체 조회");
        System.out.println("3. 이름 검색(동명이인 있으면 모두 조회)");
        System.out.println("4. 특정 회원 사용 금액 누적하기");
        System.out.println("5. 회원 정보 수정");
        System.out.println("6. 회원 탈퇴");
        System.out.println("0. 종료");
        System.out.print("메뉴 선택 >>> ");
        
        int input = Integer.parseInt(br.readLine());
        System.out.println();   // 줄바꿈
        
        return input;
    }
    
    // ----------------------------------------------------------------------------------------------------
    // [1. 회원가입(추가)]
    
    private void addMember() throws IOException {
        System.out.println("\n--- 회원가입 추가 ---\n");
        System.out.print("이름: ");
        String name = br.readLine();
        
        //정상 입력(11글자)이 될 때까지 다시 입력 받기
        String phone = null;
        
        while (true) {
            System.out.print("휴대폰 번호(- 제외) : ");
            phone = br.readLine();
            
            
            if (phone.length() != 11) {
                System.out.println("*** 다시 입력 해주세요 ***");
                continue;
            }
            
            break;
        }
        
        // 회원 추가 서비스 호출 후 결과 반환 받기
        boolean result = service.addMember(name, phone);
        
        if (result) {
            System.out.println("\n*** 회원이 추가 되었습니다 ***\n");
        } else {
            System.out.println("\n### 중복되는 휴대폰 번호가 존재합니다 ###\n");
        }
    }   // addMember()
    
    // ----------------------------------------------------------------------------------------------------
    // [2. 회원 전체 조회]
    
    private void selectAll() {
        System.out.println("\n----- 회원 전체 조회 -----\n");
        
        // 회원 목록을 조회해 반환하는 서비스 호출
        List<Member> memberList = service.getMemberList();
        
        // 조회된 회원 목록이 없을 경우
        // ==> MemberDaoImpl 생성자 코드에 의해서 memberList는 "절대로" null이 될 수 없다!!!
        // ===> 참조하는 List는 있지만, 비어있는지 검사를 해야 한다!
        if(memberList.isEmpty()) {  // memberList == null 아니다!!!
            System.out.println("\n### 회원이 존재하지 않습니다 ###\n");
            return;
        }
        
        String[] gradeArr = {"일반", "골드", "다이아"};
        System.out.println("[이름]   [휴대폰 번호] [누적 금액] [등급]");
        // 향상된 for문
        for(Member member : memberList) {
            System.out.printf("%-6s %-12s %8d  %5s \n",
                    member.getName(), member.getPhone(), member.getAmount(), gradeArr[member.getGrade()]);
        }
    }
    
    // ----------------------------------------------------------------------------------------------------
    // [3. 이름 검색(동명이인 있으면 모두 조회)]
    
    private void selectName() throws IOException {
        System.out.println("\n----- 이름 검색(동명이인 있으면 모두 조회) -----\n");
        
        // 검색할 이름 입력 받기
        System.out.print("검색할 이름 입력 : ");
        String searchName = br.readLine();
        
        // 이름 검색 서비스 호출 후 결과 반환 받기
        List<Member> searchList = service.selectName(searchName);
        
        // 검색 결과가 없을 경우
        if(searchList.isEmpty()) {
            System.out.println("\n### 검색 결과가 없습니다.###\n");
            return;
        }
        
        // 검색 결과가 있을 경우
        for(Member member : searchList) {
            System.out.println(member);
        }
    }
    
    // ----------------------------------------------------------------------------------------------------
    // [4. 특정 회원 사용 금액 누적하기]
    private void updateAmount() throws IOException {
        System.out.println("\n----- 특정 회원 사용 금액 누적하기 -----\n");
        
        System.out.print("회원 이름 입력 : ");
        String targetName = br.readLine();
        
        // 이름이 일치하는 회원 모두 조회
        List<Member> searchList = service.selectName(targetName);
        
        // 이름이 일치하는 회원이 없을 경우
        if(searchList.isEmpty()) {
            System.out.println("\n### 이름이 일치하는 회원이 존재하지 않습니다 ###\n");
            return;
        }
        
        // 금액 증가 대상 회원만 참조할 Member 참조 변수 선언
        Member target = null;
        
        // 1) 동명이인이 있을 경우 ==> 이름/전화번호를 출력해서 한 명만 선택하게 함
        if (searchList.size() > 1) {        // 한명만 있으면 체크할 필요가 없으니
            System.out.println("\n*** 대상 회원을 선택 해주세요 ***\n");
            
            // 일반 for문 사용 이유 ==> i 값 활용하고 싶어서
            for (int i = 0; i < searchList.size(); i++) {
                System.out.printf("%d) %s (%s)\n",
                        i+1, searchList.get(i).getName(), searchList.get(i).getPhone());
            }
            
            System.out.print("선택할 회원의 번호를 입력 : ");
            int input = Integer.parseInt(br.readLine()) -1;
            // 문자열 ==> 정수로 변환 ===> 입력된 번호를 index에 맞추기 위해 -1
            
            // 입력된 번호가 searchList의 index 범위를 초과한 경우
            if(input < 0 || searchList.size() <= input) {
                System.out.println("\n### 없는 회원 번호입니다. 다시 시도해주세요 ###\n");
                return;
            }
            
            // target에 회원 저장
            target = searchList.get(input);
        } else {
        // 2) 동명 이인이 없을 경우
            target = searchList.get(0);     // 회원이 하나밖에 없음
        }
        
        // 누적할 금액 입력
        System.out.print("누적할 금액 입력 : ");
        int acc = Integer.parseInt(br.readLine());
        
        // 대상 회원, 누적할 금액을 서비스로 전달하여
        // - 금액 누적
        // - 등급 조정
        // - 파일에 데이터 저장
        
        // - 서비스에서 반환 받을 문자열
        // ex) 홍길동 회원님의 누적 금액
        //     2000 ==> 10000
        //     * 골드 * 등급으로 변경 되셨습니다
        
        String result = service.updateAmount(target, acc);
        
        System.out.println(result);
    }   // updateAmount()
    
    // ----------------------------------------------------------------------------------------------------
    // [5. 회원 정보 수정]
    private void updateMember() throws IOException {
        System.out.println("\n----- 회원 정보 수정 -----\n");
        
        String targetName = br.readLine();
        
        // 이름이 일치하는 회원 모두 조회
        List<Member> searchList = service.selectName(targetName);
        
        // 이름이 일치하는 회원이 없을 경우
        if(searchList.isEmpty()) {
            System.out.println("\n### 이름이 일치하는 회원이 존재하지 않습니다 ###\n");
            return;
        }
        
        // 금액 증가 대상 회원만 참조할 Member 참조 변수 선언
        Member target = null;
        
        // 1) 동명이인이 있을 경우 ==> 이름/전화번호를 출력해서 한 명만 선택하게 함
        if (searchList.size() > 1) {        // 한명만 있으면 체크할 필요가 없으니
            System.out.println("\n*** 대상 회원을 선택 해주세요 ***\n");
            
            // 일반 for문 사용 이유 ==> i 값 활용하고 싶어서
            for (int i = 0; i < searchList.size(); i++) {
                System.out.printf("%d) %s (%s)\n",
                        i+1, searchList.get(i).getName(), searchList.get(i).getPhone());
            }
            
            System.out.print("선택할 회원의 번호를 입력 : ");
            int input = Integer.parseInt(br.readLine()) -1;
            // 문자열 ==> 정수로 변환 ===> 입력된 번호를 index에 맞추기 위해 -1
            
            // 입력된 번호가 searchList의 index 범위를 초과한 경우
            if(input < 0 || searchList.size() <= input) {
                System.out.println("\n### 없는 회원 번호입니다. 다시 시도해주세요 ###\n");
                return;
            }
            
            // target에 회원 저장
            target = searchList.get(input);
        } else {
            // 2) 동명 이인이 없을 경우
            target = searchList.get(0);     // 회원이 하나밖에 없음
        }
        
        // 수정할 전화번호 입력
        System.out.print("수정할 전화번호 입력 : ");
        String changePhone = null;
        
        while (true) {
            System.out.print("휴대폰 번호(- 제외) : ");
            changePhone = br.readLine();
            
            
            if (changePhone.length() != 11) {
                System.out.println("*** 다시 입력 해주세요 ***");
                continue;
            }
            
            break;
        }
        
        String result = service.updateMember(target, changePhone);
        
        System.out.println(result);
    }
    
}
