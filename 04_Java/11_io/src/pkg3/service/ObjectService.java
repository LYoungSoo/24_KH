package pkg3.service;

import pkg3.dto.Member;

import java.io.*;

/* ObjectInputStream / ObjectOutputStream
 *
 * - 객체를 입/출력하는 용도의 바이트 기반 보조 스트림(바이트 기반 == 1byte 단위)
 *   (보조 스트림 == 혼자 사용 불가, 기반 스트림과 같이 사용!)
 * ****************************************************************************************************
 * 직렬화(Serializable)
 * - 스트림을 통과하려는 객체(Object)를 직렬(직선) 형태로 변환하는 것
 *
 * [직렬화 방법]
 * - 스트림을 통과하려는 객체에게 Serializable 인터페이스를 상속받게 하면 된다!!!
 *
 * 역직렬화
 * - 직렬화 된 객체를 다시 정상 객체 형태로 바꾸는 것
 * [역직렬화 방법]
 * - ObjectInputStream 을 이용해서 "직렬화" 된 객체를 읽어오게 되면 자동으로 "역직렬화" 가 수행됨
 * ****************************************************************************************************
 
 */
public class ObjectService {
    
    /**
     * 객체를 외부 파일로 출력
     */
    public void objectOutput() {
        
        // 출력용 스트림 참조 변수 선언
        FileOutputStream fos = null;    // 기반
        ObjectOutputStream oos = null;  // 보조
        
        try {
            // 출력용 스트림 참조 변수 선언
            Member member = new Member("member01", "pass01", "홍길동", 20);
            
            // 폴더가 없으면 생성
            String folder = "/io_test/object";
            File f = new File(folder);
            if (!f.exists()) {  // 폴더가 없으면
                f.mkdirs();     // 경로까지 모조리 생성
            }
            
            // 출력 스트림 객체 생성
//            fos = new FileOutputStream(folder+"/"+member.getName()+".ser");
            fos = new FileOutputStream(folder + "/Member.dat");
            // /io_test/object/Member.dat
            // .dat 확장자 : data를 담고 있는 파일을 나타내는 확장자
            
            oos = new ObjectOutputStream(fos);
            oos.writeObject(member);
//            oos.flush();
            System.out.println("Member 객체 출력 완료");
            
        } catch (Exception e) {
            e.printStackTrace();
            
        } finally {
            //스트림 닫기
            try {
                if (oos != null) oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }   // objectOutput()
    
    /**
     * 파일에서 Member 객체 읽어오기
     */
    public void objectInput() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        
        try {
            // 스트림 객체 생성
            fis = new FileInputStream("/io_test/object/Member2.dat");
            ois = new ObjectInputStream(fis);
            
            // 직렬화된 객체를 읽어와 역직렬화 후 Member 타입으로 다운캐스팅
            Member member = (Member) ois.readObject();
            
            // 확인
            System.out.println(member);
            
        } catch (Exception e) {
            e.printStackTrace();
/*            StackTraceElement[] stackTraceElements = e.getStackTrace();
            for (StackTraceElement element : stackTraceElements) {
                System.out.println(element);
            }*/ // 결론은 같은 코드가 빨간 System.err 대신 그냥 흰글씨로 출력되는정도의 차이?
        } finally {
            try {
                if (fis != null) fis.close();
                if (ois != null) ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }   // objectInput()
    
}
