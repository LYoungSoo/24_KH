package set.service;

import com.sun.source.tree.Tree;
import set.dto.Person;

import java.util.*;

public class SetService {
    /* Set (집합, 주머니)
     * - 기본적으로 순서를 유지하지 않음 (Index 없음! ==> get() 메서드가 없음)
     * - 중복 데이터 저장 X (같은 객체가 들어오면 덮어쓰기)
     *
     * Set 인터페이스의 자식 클래스
     * 1. HashSet(대표) : 처리 속도가 빠른 Set
     * 2. LinkedHashSet : 순서를 유지하는 Set
     * 3. TreeSet       : 자동 정렬되는 Set
     */
    
    /**
     * Set / HashSet 사용법
     */
    public void test1() {
        
        // HashSet 객체 생성
        Set<String> set = new HashSet<String>();
        
        // 1. boolean add(E e) : 요소 추가
        set.add("네이버");
        set.add("카카오");
        set.add("라인");
        set.add("쿠팡");
        set.add("배달의민족");
        set.add("당근");
        set.add("토스");
        set.add("직방");
        set.add("야놀자");
        
        System.out.println(set);    // set.toString();
        // ==> HashSet 순서 유지 X
        // 중복 데이터 추가 후 결과 확인
        set.add("배달의민족");
        set.add("배달의민족");
        set.add("배달의민족");
        
        System.out.println(set);    // 중복 허용 x
        
        // null (참조하는 객체가 없음) 도 중복 안됨을 확인!
        set.add(null);
        set.add(null);
        set.add(null);
        System.out.println(set);
        
        // 2. int size() : Set에 저장된 객체(참조 변수)의 수 반환
        System.out.println("Set.size() = " + set.size());
        
        // 3. boolean remove(E e) : Set에 저장된 객체 중 같은 객체를 찾아 제거
        //    제거 성공 : true / 제거할 객체가 존재하지 않으면 false
        System.out.println("쿠팡 제거 : " + set.remove("쿠팡"));
        System.out.println("삼성 제거 : " + set.remove("삼성"));
        System.out.println(set);
        
        // 4. boolean contains(E e)
        // 같은 요소가 있으면 true, 없으면 false
        System.out.println("네이버 있음? " + set.contains("네이버"));
        System.out.println("KT 있음? " + set.contains("KT"));
        
        // 5. boolean empty()
        // 비어있으면 true, 아니면 false
        
        // 6. void clear()
        // 저장된 객체를 모두 제거
        System.out.println("비어있음? : " + set.isEmpty());
        set.clear();
        System.out.println("set.clear()로 모두 제거완료");
        System.out.println("비어있음? : " + set.isEmpty());
        System.out.println(set);
        
        /* Set
         * - index가 없음
         * ==> index를 활용하는 메서드가 없다
         * ==> 원하는 요소 하나만을 꺼낼 수 없다
         * ==> 순서 유지 X
         * ==> 중복 허용 X
         */
    }
    
    /**
     * Set에 저장된 객체를 꺼내는 방법
     * 1. Iterator(반복자) 이용
     * 2. List로 변환
     * 3. 향상된 for문 이용
     */
    public void test2() {
        Set<String> snackSet = new HashSet<String>();
        
        snackSet.add("포카칩");
        snackSet.add("새우깡");
        snackSet.add("홈런볼");
        snackSet.add("꼬깔콘");
        snackSet.add("프링글스");
        snackSet.add("빈츠");
        snackSet.add("쿠크다스");
        snackSet.add("웨하스");
        
        /* 1. Iterator(반복자) 이용 */
        // - 컬렉션에 저장된 요소를 하나 씩 순차 접근하는 기능을 제공하는 인터페이스
        // - 모든 컬렉션들에 이미 오버라이딩을 완료하여 기능을 제공할 수 있게 준비되었음
        Iterator<String> it = snackSet.iterator();
        System.out.println("[Iterator]");
        
        // hasNext() : 다음 순차 접근할 요소가 있으면 true
        // next() : 다음 요소가 있으면 꺼내와서 반환 ==> 화살표(커서)를 한 칸 움직임
        while (it.hasNext()) {
            String snack = it.next();
            System.out.println(snack);
//            System.out.println(it.next());
        }
        
        System.out.println("--------------------------------------------------");
        
        /* 2. List로 변환 */
        List<String> snackList = new ArrayList<String>(snackSet);
        System.out.println("[List로 변환]");
        
        for (int i = 0; i < snackList.size(); i++) {
            System.out.println(snackList.get(i));
        }
        
        System.out.println("--------------------------------------------------");
        
        /* 향상된 for문 */
        for (String snack : snackList) {
            System.out.println(snack);
        }
    }
    
    /**
     * Hash가 포함된 컬렉션을 이용하는 방법
     * ex) HashSet, LinkedHashSet, HashMap, HashTable, 등등... (TreeSet, ...)
     *
     * 1. Object.equals() 오버라이딩 하기
     * 2. Object.hashCode() 오버라이딩 하기
     *
     * *** 1,2 둘 다 수행 해야만 함!!! ***
     * ==> Java는 동일한 객체임을 비교할 때 동등(equals)/동일(hashCode) 모두 true 여야지만
     * 같은 객체로 인식한다.
     */
    public void test3() {
        Person p1 = new Person("홍길동", 25, '남');
        Person p2 = new Person("홍길순", 22, '여');
        Person p3 = new Person("짱구", 5, '남');
        
        // 중복된 필드를 가진 Person 객체 2개 생성
        Person p4 = new Person("유리", 5, '여');
        Person p5 = new Person("유리", 5, '여');
        
        // HashSet에 p1 ~ p5 추가
        Set<Person> set = new HashSet<Person>();
        
        set.add(p1);
        set.add(p2);
        set.add(p3);
        set.add(p4);
        set.add(p5);
        
        // 향상되니 for문으로 하나씩 출력
        
        // [Key Point!]
        // - p4/p5가 중복된 데이터로 인식되서 한 번만 출력되는지 확인
        
        // 확인 1 (equals, hashCode 오버라이딩 X) ==> 유리가 중복되어 출력됨
        for (Person p : set) {
            System.out.println(p);
        }
        
        System.out.println();
        
        // equals 오버라이딩 확인 == 동등 비교 (동명이인 개념)
        System.out.println(p4.equals(p5));  // t
        System.out.println(p4.equals(p1));  // f
        
        // hashCode 오버라이딩 확인
        System.out.println(p1.hashCode());
        System.out.println(p2.hashCode());
        System.out.println(p3.hashCode());
        System.out.println(p4.hashCode());
        System.out.println(p5.hashCode());
    }
    
    /**
     * TreeSet : 이진 트리 구조를 이용해 객체를 저장하는 Set ==> 오름차순 정렬 + 중복 제거
     *
     * ** 선행 조건 **
     * - 저장되는 객체가 Comparable 인터페이스를 상속해 구현해야 함
     */
    public void test4() {
        
        // 로또 번호 생성기
        Random random = new Random();
        
        Set<Integer> lotto = new TreeSet<>();
        
        // 1 ~ 45 난수를 TreeSet에 추가
        while (lotto.size() < 6) {
            lotto.add(random.nextInt(45) + 1);
        }
        System.out.println(lotto);
    }
    
    /**
     * 로또 번호 생성기
     *
     * - 금액을 천원 단위로 입력 받아 천원당 1회씩 번호를 생성해서 List에 저장한 후,
     * 생성 종료되는 시점에 한 번에 출력
     * 단, 5회 출력 시 마다 구분선 추가
     * <pre>
     * 금액 입력 : 6000
     * 1회 : [1, 2, 3, 4, 5, 6]
     * 2회 : [12, 13, 14, 15, 16, 17]
     * 3회 : [20, 21, 22, 23, 24, 25]
     * 4회 : [20, 21, 22, 23, 24, 25]
     * 5회 : [20, 21, 22, 23, 24, 25]
     * ------------------------------
     * 6회 : [20, 21, 22, 23, 24, 25]
     *
     * ==> 중복 없이 오름차순 정렬 (TreeSet)
     * </pre>
     */
    public void lottoNumberGenerator() {
        Scanner sc = new Scanner(System.in);
        int price;
        Random random = new Random();
        
        // 생성된 로또 번호(1회)를 저장할 List 생성
        List<Set<Integer>> lottoList = new ArrayList<Set<Integer>>();   // 2차원 배열같은 모습
        do {
            System.out.print("금액 입력 : ");
            price = sc.nextInt();
            if (price < 1000) System.out.println("1000원 이상의 금액을 입력해 주십시오.");
            if (100000 < price) System.out.println("10만원 이상으로는 구매하실 수 없습니다");
        } while (price < 1000 || 100000 < price);
        
        System.out.println(price + "원 만큼 로또 구매 : " + price/1000 + " 게임");
        
        // 입력된 금액 천원당 1회 반복
        for (int i = 0; i < price / 1000; i++) {
            int count = 0;
            // 1회치에 해당되는 로또 번호 생성
            Set<Integer> lotto = new TreeSet<>();
            // 이렇게 안하고 .clear()을 사용하면 주소참조를 저장한 것이기 때문에 빈 배열이 출력됨
            while (lotto.size() < 6) {
                lotto.add(random.nextInt(45) + 1);
            }
            // lottoList에 추가
            lottoList.add(lotto);
        }
        // 제시된 형태에 맞게 출력
        for (int i = 0; i < lottoList.size(); i++) {
            if (i % 5 == 0) System.out.println("------------------------------");
            System.out.printf("%d회 : %s \n", i+1, lottoList.get(i));
//            if ((i+1) % 5 == 0) System.out.println("------------------------------");
        }
        
    }
    
}
