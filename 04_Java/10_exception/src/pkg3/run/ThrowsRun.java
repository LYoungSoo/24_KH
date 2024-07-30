package pkg3.run;

import pkg3.service.ThrowsService;

import java.io.IOException;

public class ThrowsRun {
    public static void main(String[] args) throws IOException {
        ThrowsService service = new ThrowsService();
        
//        service.test1();
        
        // 빨간줄이 뜨는 이유
        // ==> test3()에서 IOException이 던져져 올수도 있기 때문에
        //      대비(예외 처리)를 해야되는데 안해서
        // ==> main() 메서드에 throws를 작성해서 또 예외를 전달 시도
        //     하지만 main()을 호출한 메서드는 존재하지 않기 때문에
        //     예외가 처리되지 않아 프로그램이 강제 종료됨
//        service.test3();
        
        service.test5();
        
        System.out.println("와아아아ㅏ");
    }
}
