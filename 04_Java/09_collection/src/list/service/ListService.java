package list.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/* Collection Framework : Java에서 제공하는 자료구조를 모아둔 것
 * (java.util 패키지에 존재)
 *
 * [특징]
 * 1) 크기 제한 X
 * 2) 추가, 수정, 삭제, 검색, 정렬 등 다양한 기능이 제공됨
 * 3) 객체만 저장 가능!! (정확히는 객체 참조 주소만 저장 가능)
 *
 * [List]
 * - 자료(Data)를 순차적으로 나열한 자료 구조(배열 비슷)
 * - index가 존재함 ==> 각 Data를 index로 구분할 수 있어 중복된 Data를 저장할 수 있다!!!
 *
 */
public class ListService {
    public void test1() {
        
        // ArrayList 객체 생성 ==> List 인터페이스를 상속 받은 자식 클래스로 가장 많이 사용하는 List
        
        // List : 부모 인터페이스 ==> 객체 생성 불가 / 부모 참조 변수 가능

//        List list = new ArrayList();
        List list = new ArrayList(3);   // 3칸 짜리 생성
        
        // E(Element) : 요소를 뜻하는 상징적인 글자 (특정 자료형 X, Object 타입)
        
        // boolean add(E e)
        // - List에 주어진 객체(e)를 맨 끝에 추가
        list.add("아무거나");
        list.add(new Object());
        list.add(new int[4]);
        
        // 4번째 ==> 크기 초과 오류 발생 (O/X)
        list.add(100);    // int 기본 자료형은 추가 불가(컬렉션은 객체 참조 '주소'만 저장가능. 값 저장 X)
        // ==> Integer Wrapper class로 변환되어 저장됨
        
        System.out.println(list.toString());
        
        // 2. void add(int index, E e) - index번째에 e를 추가 ==> 중간에 삽입한다
        list.add(2, "중간");
        System.out.println(list);   // .toString() 생략 가능
//        list.add(6, "8번");  // java.lang.IndexOutOfBoundsException: Index: 6, Size: 5
        
        // 3. int size()
        // - 저장된 Data(객체)의 수를 반환
        // - 배열의 length와 같은 역할을 함
        System.out.println("현재 저장된 data 개수 : " + list.size());
        
        // (E==Object)
        
        // 4. E get(int index)
        // - index번째 요소를 반환
        System.out.println(list.get(2));
        
        System.out.println("--------------------------------------------------");
        
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%d 번째 인덱스 요소 : %s\n", i, list.get(i));
        }
        
        System.out.println("--------------------------------------------------");
        
        // List + 향상된 for문
        for (Object obj : list) {
            String str = null;
            
            //obj가 참조하는 객체가 String 타입이라면
            if (obj instanceof String) str = "[String]";
            else if (obj instanceof int[]) str = "[Int[]]]";
            else if (obj instanceof Integer) str = "[Integer]";
            else str = "[Object]";
            
            System.out.println(str + " : " + obj);
        }
    }
    
    /**
     * List + 제네릭(타입제한) 확인
     * + List 메서드 몇 가지 더 확인
     */
    public void test2() {
        /* 제네릭(Generics, <>)
         * -
         * 1) 컬렉션의 타입을 제한하여 한 가지 자료형만 저장할 수 있게 하는 기능
         * 2) 클래스나 메서드가 다룰 타입을 지정하는 기능
         */
        
        // String 으로 타입이 제한된 List 생성
//        List<String> menuList = new ArrayList<>();    // 똑같은듯?
        List<String> menuList = new ArrayList<String>();
        
        // add() 확인
        // menuList.add(123);   // String으로 제한되서 다른 자료형 추가 불가능
        menuList.add("보쌈");
        menuList.add("닭갈비");
        menuList.add("양념갈비");
        menuList.add("냉면");
        menuList.add("칼국수");
        menuList.add("삼겹살");
        menuList.add("뭐먹지");
        menuList.add("잠이나 잘래");
        
//        for (Object obj : menuList) {
        for (String menu : menuList) {
            String str = null;
            if (menu instanceof String) str = "[String]";
//            else if (menu instanceof int[]) str = "[Int[]]]";
//              java.lang.String cannot be converted to int[]
//            else if (menu instanceof Integer) str = "[Integer]";
            else str = "[Object]";
            System.out.println(str + " : " + menu);
        }
        
        // 5. E set(int index, E e)
        // - 지정된 index번째 요소를 e(두 번째 매개 변수)로 수정
        // ==> 메서드 반환 값으로 이전 객체가 반환 된다
        
        System.out.println("--------------------------------------------------");
        System.out.println(menuList);
        String before = menuList.set(2, "소갈비");
        System.out.println(before + " 가 " + menuList.get(2) + " 로 변경됨");
        System.out.println(menuList);
        
        // 6. boolean contains(E e)
        // - List 내부에 e와 같은 객체가 존재하면 true / 없으면 false 반환 (유무 확인 기능)
        
        System.out.println(
                "--------------------------------------------------");
        System.out.println("보쌈 : " + menuList.contains("보쌈"));
        System.out.println("족발 : " + menuList.contains("족발"));
        
        // 7. int indexOf(E e)
        // - List에 내부에 e와 같은 객체가 존재하면 해당 index를 반환, 존재하지 않으면 -1을 반환
        System.out.println("삼겹살 : " + menuList.indexOf("삼겹살"));
        System.out.println("오겹살 : " + menuList.indexOf("오겹살"));
        
        // 8. E remove(int index)
        // - List에서 지정된 index 번째 요소를 제거 ==> 중간이 제거되면 뒤쪽 요소는 앞으로 이동됨
        // ===> 제거된 요소는 메서드 결과로 반환됨
        
        System.out.println(menuList.remove("닭갈비") + "(닭갈비) 제거");
        System.out.println(menuList.remove(3) + "제거");
        System.out.println(menuList);
    }
    
    /**
     * 왜 컬렉션 참조 변수를 부모 타입으로 작성하는가?
     * - 다형성 업캐스팅의 특징을 이용하
     * ==> 프로그래밍 과정에서 미처 생각치 못한 비효율적인 요소를 쉽게 개선하기 위해서
     * ex) ArrayList로 만들었는데 중간에 추가, 삭제가 빈번히 일어나는 경우 ==> LinkedList가 훨씬 효율적
     *  LinkedList 만들었는데 검색이 빈번하게 일어나는 경우 ==> ArrayList가 훨씬 효율적
     */
    public void test3() {
        List<Integer> list = new ArrayList<>();         // 24 174900 5
//        List<Integer> list = new LinkedList<>();        // 134 3005000 4
        
        Random random = new Random();
        
        // System.currentTimeMillis() ==> 1970년 1월 1일 09:00:00.00 기준
        // 현재 시간 까지 지난 시간을 ms로 반환(long)
//        System.out.println(System.nanoTime());
        long start = System.currentTimeMillis();
        
        for (int i = 0; i < 1000000; i++) {
            list.add(random.nextInt(1000000));   // 0 ~ 999999 사이 난수
//            System.out.println(list.get(i));
        }
        long end = System.currentTimeMillis();
        System.out.println("생성 완료 - 걸린시간 : " + (end - start) + "ms");
        
        System.out.println("--------------------------------------------------");
        
        start = System.nanoTime();
        list.add(500000, 123456789);    // 중간에 데이터 삽입
        end = System.nanoTime();
        System.out.println("중간 삽입 완료 - 걸린시간 : " + (end - start) + "nm");
        
        start = System.currentTimeMillis();
        System.out.println(list.indexOf(123456789));
        end = System.currentTimeMillis();
        System.out.println("검색 시간 : " + (end - start) + "ms");
        
        
    }
}
