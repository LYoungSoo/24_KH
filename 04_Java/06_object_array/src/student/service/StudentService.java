package student.service;

import student.dto.StudentDTO;

import java.util.Random;

// 기능(비즈니스 로직) 제공용 클래스
public class StudentService {
    
    // StudentDTO 객체를 참조하는 StudenDTO 참조 변수 5개 묶음 배열 생성
    // 인덱스 : 0 ~ 4
    // 각 인덱스 요소 : 초기 값 null
    private StudentDTO[] students = new StudentDTO[5];
    Random random = new Random();
    
    /* 기본 생성자 */
    public StudentService () {   // 생성자 명 == 클래스 명 이어야 함
        
        // 학생 객체 배열(students)에 샘플 학생 3명 사전 추가
        
        // students[0] : StudentDTO 자료형 참조 변수
        students[0] = new StudentDTO("24001", "짱구", '남');
        students[1] = new StudentDTO("24002", "유리", '여');
        students[2] = new StudentDTO("24003", "멩구", '남');
        
        // students[3] / [4] == null
        
        /* 0 ~ 100 사이 난수 발생 */
        // 1) Math.floor(Math.random() * 101) == 0 ~ 100
        // 2) Random random = new Random();     random.nextInt(101); == 0 ~ 100
        
        
        for (StudentDTO std : students) {   // 향상된 for문
            
            // 현재 접근한 요소가 참조하는 객체가 없다면 반복을 종료
            // == 기본 0,1,2 배열은 출력하고 3,4에 값이 있으면 출력, 값이 null이면 break;
            if (std == null) break;
            
            // random.nextInt(101) : 0 이상 101 미만 정수형 난수
            std.setHtml(random.nextInt(101));
            std.setCss(random.nextInt(101));
            std.setJs(random.nextInt(101));
            std.setJava(random.nextInt(101));
        }
    }   // StudentService() 기본 생성자
    
    /**
     * 학생 객체 배열 students 중 비어있는 인덱스에 전달 받은 학생 객체(std)추가
     *
     * @param std: 입력 받은 값을 저장한 학생 객체 주소
     * @return true : 학생 추가 성공 / false : 학생 추가 실패
     * - 실패 조건 : students 배열이 꽉 차있을 때 추가 학생이 전달된 경우(Array Index Out Of Bounds Exception)
     */
    public boolean addStudent (StudentDTO std) {
        
        // 0번 인덱스 부터 순서대로 비어있는(null) 요소를 찾아 전달 받은 std를 대입
        for (int i = 0; i < students.length; i++) {   // 향상된 for문
            if (students[i] == null) {     // 비어있는 요소가 있을 경우
                students[i] = std;
                
                //여기는 점수를 랜덤값으로 뽑아서 대입!
                students[i].setHtml(random.nextInt(101));
                students[i].setCss(random.nextInt(101));
                students[i].setJs(random.nextInt(101));
                students[i].setJava(random.nextInt(101));
                
                return true;
            }
        }
        
/*        // 0번 인덱스 부터 순서대로 비어있는(null) 요소를 찾아 전달 받은 std를 대입
        for(StudentDTO temp : students) {   // 향상된 for문
            if (temp == null) {     // 비어있는 요소가 있을 경우
                temp = std;
                return true;
            }
        }
        */
        
        // 비어있는(null) 요소가 없을 경우 false 반환
        return false;
    }
    
    /**
     * students 객체 배열에 저장된 주소를 얻어가게 하는 메서드
     *
     * @return students 객체 배열에 저장된 주소
     *
     * public 반환형 get필드명() {
     * return 필드명;
     * }
     */
    public StudentDTO[] getStudents () {
        return students;
    }
    
    /**
     * 학생 1명 정보 조회 (index 검색)
     *
     * @param index
     * @return 조회된 학생 객체 주소 || null
     */
    public StudentDTO selectIndex (int index) {
        
        // 전달 받은 index 번위가 students의 인덱스 범위 밖인 경우
        if (index < 0 || index >= students.length) return null;
        
        return students[index];
    }
    
    /**
     * 전달 받은 이름과 이름이 일치하는 학생 객체를 반환
     *
     * @param targetName
     * @return null 또는 일치하는 학생 객체(주소 값)
     */
    public StudentDTO selectName (String targetName) {
        
        for (StudentDTO std : students) {
            // 저장된 학생이 없늘 경우
            //  ==> 뒤에도 없을 것!! 그냥 여기서 검색 종료
            if (std == null) return null;
            
            // == : 기본 자료형 비교
            // A.equals(B) = 참조형(객체) 비교
            if( std.getName().equals(targetName) ) {
                return std;
            }
        }
        
        return null;
    }
}
