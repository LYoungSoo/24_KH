package dto;

public class Student {
    
    private String name;
    private int score;
    
    public Student() {}
    public Student(String name, int score) {		//********** 오버로딩
        this.name = name;
        this.score = score;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
}