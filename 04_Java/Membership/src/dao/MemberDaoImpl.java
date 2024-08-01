package dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import dto.Member;

// MemberDao 인터페이스를 상속 받아 구현

public class MemberDaoImpl implements MemberDao {
    
    // 회원 데이터가 저장될 파일 경로를 상수로 지정
    private final String FILE_PATH = "/io_test/membership.dat";
    
    // 회원 목록을 저장해 둘 List 객체
    private List<Member> memberList = null;
    
    // 스트림 객체 참조 변수
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    
    
    // 기본 생성자
    // - 회원 다수를 관리할 회원 목록(List)이 필요한데,
    // --
    // 이미 파일로 저장된 회원 목록이 있으면 읽어오고 없으면 새로 만들기! 를 하겠따
    public MemberDaoImpl() throws FileNotFoundException, IOException, ClassNotFoundException {
        
        // membership.dat 파일이 존재하는지 검사
        File file = new File(FILE_PATH);
        if(file.exists()) {
            try {
                ois = new ObjectInputStream(new FileInputStream(FILE_PATH));
                
                // 저장된 객체를 파일에서 읽어와 다운캐스팅 하여 memberList가 참조하게 함
                memberList = (ArrayList<Member>) ois.readObject();   // List<Member>  <=>  Object
            } finally {
                // try에서 발생하는 예외를 throws 구문으로 처리하면 catch() 구문을 작성하지 않아도 된다!
                
                if (ois != null) ois.close();
            }   // try-catch
        }   // if
        // 파일이 존재하지 않는 경우
        else {
            // 새로운 ArrayList를 만들어서 참조
            memberList = new ArrayList<>();
        }
    }   // MemberDaoImpl()
    
    // memberList 반환
    @Override
    public List<Member> getMemberList() {
        return memberList;
    }
    
    // 회원 추가
    @Override
    public boolean addMember(Member member) throws IOException {
        // 1) 매개 변수로 전달 받은 새 회원 정보를 memberList에 추가
        memberList.add(member);
        
        // 2) memberList를 지정된 파일로 출력(저장)
        //      ==> 현재 매서드 말고 다른 메서드에서도 파일 출력(저장) 기능이 자주 사용될 예정!!
        //          ===> saveFile() 메서드 만들어 호출
        saveFile();
        
        return true;
    }
    
    // 파일 저장
    @Override
    public void saveFile() throws IOException {
        
        // memberList를 지정된 파일에 출력(저장)
        try {
        oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH));
        oos.writeObject(memberList);
        } finally {
            if (oos != null) oos.close();   // flush() + 메모리 반환
        }
    }
    
    
}