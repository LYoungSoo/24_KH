package pkg2.service;

import pkg2.dto.Animal;
import pkg2.dto.Cat;
import pkg2.dto.Fish;

public class AbsService {
    
    /**
     * 추상 클래스 객체 생성 확인
     */
    public void test1() {
        
        //'Animal' is abstract; cannot be instantiated
        // Cannot instantiate the type Animal
        // ==> Animal은 객체화 할 수 없다! / 왜? 추상 클래스라서
        Cat c = new Cat();
        Fish f = new Fish();
        
        // 오버라이딩한 추상 메서드 호출
        c.move();
        c.eat();
        c.sleep();
        
        f.move();
        f.eat();
        f.sleep();
    }
    
    /**
     * 추상 클래스 활용법
     *
     * 선행 지식 : 다형성 업캐스팅 + 동적 바인딩
     */
    public void test2() {
        // 추상 클래스는 객체 생성은 x
        // 대신 참조형 변수는 O
        
        Animal a1 = new Cat(2,10);
        Animal a2 = new Fish(2,2);
        
        // 추상 클래스를 이용한 객체 배열 ==> 추상 클래스 참조 변수 묶음 ==> 문제 없음!
        Animal[] animals = new Animal[4];
        
        animals[0] = new Cat();
        animals[1] = a1;        // a1에 저장된 값 == Cat 객체 참조 주소. Cat은 Animal 자식 클래스
        animals[2] = new Fish();
        animals[3] = a2;
        
        // 향상된 for문 ==> 동적 바인딩 확인
        for(Animal a : animals) {
            System.out.println("----------------------------------------");
            System.out.println(a);  // a.toString() 출력. Cat : 0 / 0 으로 출력됨
            a.move();
            a.eat();
            a.sleep();
        }
/*        Animal a0 = new Animal() {
            @Override
            public void move() {
                System.out.println("막 만든 움직임");
            }
            
            @Override
            public void eat() {
                System.out.println("대충 만들어서 안먹음");
            }
            
            @Override
            public void sleep() {
                System.out.println("이름안달아준 창조물이라 안잠");
            }
        };  // Animal a3
        a0.move();
        a0.eat();
        a0.sleep();*/
        
    }
}
