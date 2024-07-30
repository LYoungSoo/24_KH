package pkg4.run;

import pkg4.exception.UserException;

public class UserDefinedExceptionRun {
    public static void main(String[] args) {
        
        try {
            // 사용자 정의 예외 강제 발생
            throw new UserException("발생 시 전달한 메시지");
        } catch (UserException e) {
            System.out.println("---- catch문 ----");
            System.out.println(e.getMessage());
        }
        
    }
}
