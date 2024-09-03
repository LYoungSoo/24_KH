package test;

import java.util.List;
import java.util.Scanner;

public class ShopView {
    
    // 필드
    ShopService shopService = new ShopService();
    private Scanner sc = new Scanner(System.in);
    
    // 메서드
    public void mainMenu() {
        selectMember();
    }
    
    private void selectMember() {
        System.out.println("\n다음주까지만 버틸게요\n");
        
        System.out.println("MEMBER_ID 입력 : ");
        String memberId = sc.nextLine();
        
        ShopMember sm = shopService.selectMember(memberId);
        
        if (sm == null) {
            System.out.println("\n!!! 집에 못간다고? !!!\n");
            return;
        }
        System.out.println(sm);
    }
    
}
