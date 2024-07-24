package pkg1.dto;

// Parent 상속
public class Child extends Parent {
    
    private String car;
    
    public Child() {
        super();    // 안쓰면 컴파일러가 추가해줌
    }
    
    // 자식 객체 생성 시 자식 + 부모 필드 한번에 초기화
    public Child(String lastName, int money, String car) {
        super(lastName, money);
        this.car = car;
    }
    
    public String getCar() {
        return car;
    }
    
    public void setCar(String car) {
        this.car = car;
    }
}