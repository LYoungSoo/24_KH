package pkg2.dto;

public class Fish extends Animal {
    private int gill;   // 아가미 개수
    
    public Fish() {
        super();
    }
    
    public Fish(int eyes, int gill) {
        super(eyes);
        this.gill = gill;
    }
    
    public int getGill() {
        return gill;
    }
    
    public void setGill(int gill) {
        this.gill = gill;
    }
    
    @Override
    public String toString() {
        return "Fish : " + gill + " / " + getEyes();
    }
    
    @Override
    public void move() {
        System.out.println("물고기 : 첨벙첨벙 팔딱팔딱");
    }
    
    @Override
    public void eat() {
        System.out.println("물고기 : 뻐끔뻐끔 입으로 먹음");
    }
    
    @Override
    public void sleep() {
        System.out.println("물고기 : 무섭게 눈뜨고 잠");
    }
}
