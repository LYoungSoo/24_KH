package basic;

/**
 * 계좌 클래스
 * (class == 객체의 속성, 기능을 정의한 문서(설계도))
 */
public class Account {
    
    /**
     * [캡슐화]
     * 1) 객체의 속성, 기능을 하나로 묶음
     * 2) 외부로 부터의 직접 접근을 제한
     * - 내부가 보이지 않음 ==> 정보 은닉 효과
     *
     * [ 직접 접근을 제한하는 방법 ]
     * - 접근을 제한할 속성에 private 키워드 추가
     * (private : 개인적인, 사적인) ==> 현재 객체만 접근 가능한 (현재 객체 개인적인 것)
     *
     * ************************************************************
     * 기본적으로 모든 클래스의 속성은 private을 적용 하는 것이 원칙!!
     * ************************************************************
     */
    
    // 속성(값 == 변수) // private 추가함
    private String name;            // 이름(계좌주)
    private String accountNumber;   // 계좌 번호
    private long balance;           // 잔액
    private String password;        // 비밀번호
    
    // 기능(메서드)
    
    /**
     * 입금 기능
     *
     * 매개 변수(Parameter) : 메서드 호출 시 전달 받은 값을 저장하는 변수
     *
     * @param amount : 전달 받은 금액
     */
    public void deposit (long amount) {
        balance += amount;  // 전달 받은 금액을 잔액에 누적
        
        // 현재 잔액 얼마인지 출력
        
        System.out.printf("%s 님의 %s 계좌의 현재 잔액 : %d \n", name, accountNumber, balance);
    }
    
    /**
     * 출금 기능
     *
     * @param pw     : 전달 받은 비밀번호
     * @param amount : 전달 받은 출금 금액
     */
    public void withdraw (String pw, long amount) {
        // 자바에서
        // - 기본 자료형 값 비교는 A == B
        // - 참조형(객쳬) 값 비교는 A.equals(B);
        
        if (!password.equals(pw)) {
            System.out.println("비밀번호가 일치하지 않습니다");
            return; // 메서드 종료
        }
        
        // 출금 금액이 잔액보다 큰 경우
        if (balance - amount < 0) { // (amount > balance)
            System.out.println("잔액이 부족합니다");
            return;
        }
        
        if (password.equals(pw)) {  // 불필요해 보이긴 하는데 ...
            balance -= amount;
            System.out.println(amount + "원이 출금되었습니다");
        }
        System.out.printf("%s 님의 %s 계좌의 현재 잔액 : %d \n", name, accountNumber, balance);
        
    }
    
    /* 변수(필드) 간접 접근 기능
     *
     * 1. setter : 변수에 값을 세팅(대입) 하는 기능
     *
     *  public void set필드명 (자료형 변수명){
     *  	this.필드명 = 변수명;
     *  }
     *
     *
     * 2. getter : 변수의 값을 얻어가게 하는 기능
     *
     *  public 반환형 get필드명 (){
     * 		return 필드명;
     *  }
     * */
    
    public String getName () {
        return name;
    }
    
    
    public void setName (String name) {
        this.name = name;
    }
    
    
    public String getAccountNumber () {
        return accountNumber;
    }
    
    
    public void setAccountNumber (String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    
    public long getBalance () {
        return balance;
    }
    
    
    public void setBalance (long balance) {
        this.balance = balance;
    }
    
    
    public String getPassword () {
        return password;
    }
    
    public void setPassword (String password) {
        this.password = password;
    }
    
}
