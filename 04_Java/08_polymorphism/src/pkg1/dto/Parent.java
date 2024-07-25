package pkg1.dto;

public class Parent {
    
    // 필드
    private String lastName = "김";
    private int money = 50000;
    
    // 기본 생성자
    public Parent () {
        super();
    }    // 부모(Object) 생성자 호출
    
    // 매개 변수 생성자
    public Parent (String lastName, int money) {
        super();
        this.lastName = lastName;
        this.money = money;
    }
    
    public String getLastName () {
        return lastName;
    }
    
    public void setLastName (String lastName) { this.lastName = lastName; }
    
    public int getMoney () {
        return money;
    }
    
    public void setMoney (int money) {
        this.money = money;
    }
    
    // toString() 오버라이딩
    @Override
    public String toString () {
        return "Parent : " + lastName + " / " + money;
    }
    
}
