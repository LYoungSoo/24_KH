package list.view;

import list.dto.BookDTO;
import list.service.BookService;

import java.util.List;
import java.util.Scanner;

// View : 사용자에게 보여지는 부분(출력, 입력)
public class BookView {
    private Scanner sc = new Scanner(System.in);
    private BookService service = new BookService();
    
    /**
     * 사용자에게 보여질 메인 화면
     */
    public void mainMenu() {
        int input = 0;
        
        do {
            System.out.println("\n***** 도서 관리 프로그램 *****\n");
            System.out.println("1. 전체 조회");
            System.out.println("2. index로 조회");
            System.out.println("3. 책 이름으로 조회");
            System.out.println("4. 글쓴이 이름으로 조회");
            System.out.println("5. 가격으로 조회");
            
            System.out.println("6. 책 추가하기");
            System.out.println("7. 책 제거하기");
            System.out.println("8. 책 가격 수정하기");
            
            System.out.println("0. 종료");
            
            System.out.println();
            System.out.print("메뉴 선택 ==> ");
            System.out.println();
            input = sc.nextInt();
            sc.nextLine();      // 입력 버퍼에 남은 엔터 제거
            
            switch (input) {
                case 1: selectAll(); break;
                case 2: selectIndex(); break;
                case 3: selectTitle(); break;
                case 4: selectWriter(); break;
                case 5: selectPrice(); break;
                case 6: addBook(); break;
                case 7: removeBook(); break;
                case 8: updateBook(); break;
                case 0: System.out.println("***** 프로그램 종료 *****");
                    break;
                default: System.out.println("=-=-= 잘못 입력 =-=-=");
                    break;
            }
        } while (input != 0);
    }   // mainMenu()
    
    /**
     * 전체 조회
     * - BookService에 있는 bookList를 얻어와 존재하는 모든 요소의 정보를 출력
     */
    private void selectAll() {
        System.out.println("\n@@@@@ 전체 조회 @@@@@\n");
        List<BookDTO> bookList = service.selectAll();

//        for (BookDTO book : bookList) {
        for (int i = 0; i < bookList.size(); i++) {
            // print() 관련 구문에서 참조 변수명을 작성하면 자동으로 toString이 호출된다!
            System.out.println(i + ") " + bookList.get(i));
        }
    }   // private void selectAll()
    
    /**
     * 입력 받은 index번째 BookDTO를 반환 받아와 출력
     */
    private void selectIndex() {
        System.out.println("\n@@@@@ index로 조회 @@@@@\n");
        System.out.println("조회할 책의 index 번호 입력 : ");
        int index = sc.nextInt();
        sc.nextLine();
        
        // 전달한 index번째 요소가 존재하면 BookDTO 객체 주소, 없다면 null(참조하는 객체가 없음) 반환
        BookDTO book = service.selectIndex(index);
        
        if (book == null) {
            System.out.println("=-=-= 해당 인덱스에 책이 존재하지 않습니다 =-=-=");
            return;
        }
        
        System.out.println("제목 : " + book.getTitle());
        System.out.println("저자 : " + book.getWriter());
        System.out.println("가격 : " + book.getPrice() + "원");
    }
    
    /**
     * 입력 받은 책 제목이 포함된 모든 책 출력하기
     * 단, 없으면 "조회 결과가 없습니다" 출력
     */
    private void selectTitle() {
        System.out.println("\n@@@@@ 책 이름으로 조회(포함) @@@@@\n");
        
        System.out.print("검색할 책 제목 입력 : ");
        String title = sc.nextLine();
        
        List<BookDTO> searchList = service.selectTitle(title);
        
        // 만약 검색 결과가 없을 경우
//        if(searchList.size() == 0) {
        if (searchList.isEmpty()) {      // 오랜만에 본다잉
            System.out.println("=-=-= 조회 결과가 없습니다 =-=-=");
            return;
        }
        
        // 검색 결과 있는 경우
        for (BookDTO book : searchList) {
            System.out.println(book);
        }
    }
    
    /**
     * 입력된 글쓴이 이름이 같은 책을 모두 조회하여 출력하기. 단, 없으면 "조회 결과가 없습니다" 출력
     */
    private void selectWriter() {
        System.out.println("\n@@@@@ 글쓴이 이름으로 조회(포함) @@@@@\n");
        System.out.print("검색할 글쓴이 입력 : ");
        String writer = sc.nextLine();
        
        List<BookDTO> searchList = service.selectWriter(writer);
        if (searchList.isEmpty()) {
            System.out.println("=-=-= 조회 결과가 없습니다 =-=-=");
            return;
        }
        for (BookDTO book : searchList) {
            System.out.println(book);
        }
    }
    
    /**
     * 검색할 가격의 범위(최소값, 최대값)을 입력 받아 가격 범위 내의 모든 책을 조회하여 출력
     * 단, 없으면 "조회 결과가 없습니다" 출력
     */
    private void selectPrice() {
        System.out.println("\n@@@@@ 가격으로 조회 @@@@@\n");
        int minPrice = 0;
        int maxPrice = 0;
        
        do {
            System.out.print("검색할 최소 가격 입력 : ");
            minPrice = sc.nextInt();
            sc.nextLine();
            System.out.print("검색할 최대 가격 입력 : ");
            maxPrice = sc.nextInt();
            sc.nextLine();
            
            if (minPrice > 0 || maxPrice > 0) break;    // 0보다 큰 금액 들어오면 do-while 탈출
            System.out.println("\n ***** 금액은 0보다 커야합니다. *****");
        } while (minPrice <= 0 && maxPrice <= 0);       // price<0 하면 0 들어왔을때 맘에안들게 작동
        
        if (minPrice > maxPrice) {
            System.out.println(minPrice + " > " + maxPrice);
            minPrice = minPrice ^ maxPrice;
            maxPrice = maxPrice ^ minPrice;
            minPrice = minPrice ^ maxPrice;
            System.out.println("최대금액 최소금액 교체 성공");
            System.out.println(minPrice + " < " + maxPrice);
        }
        
        List<BookDTO> searchList = service.selectPrice(minPrice, maxPrice);
        if (searchList.isEmpty()) {
            System.out.println("=-=-= 조회 결과가 없습니다 =-=-=");
            return;
        }
        for (BookDTO book : searchList) {
            System.out.println(book);
        }
    }
    
    /**
     * 제목, 글쓴이, 가격을 입력 받아 BookService의 bookList에 추가하기
     */
    private void addBook() {
        System.out.println("\n@@@@@ 책 추가하기 @@@@@\n");
        
        System.out.print("제목 : ");
        String title = sc.nextLine();
        
        System.out.print("글쓴이 : ");
        String writer = sc.nextLine();
        
        System.out.print("가격 : ");
        int price = sc.nextInt();
        
        // 입력 받은 정보를 묶어서 Service로 전달할 수 있도록 BookDTO 객체를 생성
        BookDTO newBook = new BookDTO(title, writer, price);
        
        boolean result = service.addBook(newBook);
        
        if (result) System.out.println("***** 추가 완료 *****");
        else System.out.println("!!!!! 추가 중 문제 발생 !!!!!");
    }
    
    /**
     * 인덱스 번호를 입력 받아
     * 1) 일치하는 인덱스가 있으면 bookList에서 제거 후 "[책 제목] 책이 목록에서 제거 되었습니다" 출력
     * 2) 일치하는 인덱스가 없으면 "해당 인덱스에 존재하는 책이 없습니다" 출력
     */
    private void removeBook() {
        System.out.println("\n@@@@@ 책 제거하기 @@@@@\n");
        
        System.out.println("제거할 책의 index 번호 입력 : ");
        int index = sc.nextInt();
        sc.nextLine();
        
/*        String result = service.removeBook(index);
        if (result == null) {
            System.out.println("=-=-= 조회 결과가 없습니다 =-=-=");
            return;
        }
        System.out.printf("[%s] 책이 목록에서 제거 되었습니다\n", result);*/
        
        //===========내 입맛대로 수정코드
        System.out.println(service.selectIndex(index));
            System.out.print("해당 책을 제거하시겠습니까? (Y/N) : ");
            char yn = sc.next().charAt(0);
            if (Character.toUpperCase(yn) == 'Y') {
                System.out.printf("[%s] 책이 목록에서 제거 되었습니다\n", service.removeBook(index));
            }
            else if (Character.toUpperCase(yn) == 'N') {
                System.out.println("책 제거 취소됨");
                return;
            }
            else System.out.println("잘못된 문자 입력됨");
    }
    
    
    private void updateBook() {
        System.out.println("\n@@@@@ 책 가격 수정하기 @@@@@\n");
        System.out.print("수정할 책의 인덱스 입력 : ");
        int index = sc.nextInt();
        
        // 인덱스가 일치하는 요소의 BookDTO(참조 주소)를 반환 받기
        // ex) index가 0인 경우
        // BookService의 bookList.get(0)에 저장된 주소와 아래 book에 저장된 주소가 "같음"
        BookDTO book = service.selectIndex(index);
        
        if (book == null) {
            System.out.println();
            return;
        }
        // 책이 존재하면 가격 입력 받기
        System.out.printf("[%d] %s\n", index, book);
        System.out.print("수정할 가격 입력 : ");
        int newPrice = sc.nextInt();
        
        // 수정 전 가격을 임시 변수에 저장
        int beforePrice = book.getPrice();
        
        // book이 참조하는 BookDTO의 가격을 newPrice로 수정
        book.setPrice(newPrice);    // == service.selectIndex(book) 이건가?
        
        // 결과 출력
        System.out.printf("[%s] 가격이 (%d) ==> (%d) 수정되었습니다.\n", book.getTitle(), beforePrice, newPrice);
    }
    
    
}
