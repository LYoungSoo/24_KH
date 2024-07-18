package basic;

// 같은 패키지의 클래스는 import X
public class BasicRun {
    public static void main (String[] args) {
        // 국민 객체 생성
        // = 국민(Nation) 클래스에 작성된 내용대로 Heap 메모리 영역에 할당(생성) 하는 것
        Nation n1 = new Nation();
        
        // 국민 객체에 값(속성) 대입
        n1.name = "홍길동";
        n1.age = 20;
        n1.gender = '남';
        
        // 국민 객체가 공통적으로 가진 기능(메서드) 수행
        n1.introduce();
        
        // 국민 객체 생성
        Nation n2 = new Nation();
        
        n2.name = "신짱구";
        n2.age = 5;
        n2.gender = '남';
//        n2.tel = "010-0123-4567";
        n2.introduce();
        
        // 세금 납부 여부 확인
        System.out.println("------------------");
        n1.납세의의무();
        
        System.out.println();
        n2.납세의의무();
        
    }
    
}
