package map.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

public class MapService {
    /* Map : 특정 키워드(Key)를 입력하면 해당되는 상세한 값(Value)를 얻을 수 있는 컬렉션 객체
     * - K:V 형식으로 구성
     * - K:V 한 쌍을 "Entry" 라고 부름
     * - Key : 키워드 역할(== 변수명)
     *
     * 1) 중복 X
     *  ==> 중복되는 Key 입력 시 Value를 덮어씌움
     *  ==> Key 역할의 객체는 hashCode(), equals() 오버라이딩 필수!
     *
     * 2) 순서 유지 X (단, LinkedHashMap 제외)
     *
     * * Key만 묶어서 보면 Set과 같은 특징을 지니고 있음!
     *  ==> Map.keySet() / Map.EntrySet() 제공
     *
     * Value : 키워드에 해당하는 상세한 값
     * - Key로 인해서 모든 Value가 구분됨
     *  ==> Map.get(Key) / Map.remove(Key) 형태로 사용
     */
    public void test1() {
        
        // HashMap : 대표적인 Map 상속 객체
        // - Hash 단어가 붙으면 빠름 ==> 대신 Key 역할의 객체가 equals(), hashCode() 오버라이딩 필수!!
        
        //   Key     Value
        Map<String, String> map = new HashMap<String, String>();
        
        // 1. V put (K k, V v) : map에 K:V 한 쌍을 추가. 추가 성공시 Value가 반환됨
        // ==> 중복되는 key가 없으면 null, 중복되는 key가 있으면 이전 value 반환
        System.out.println(map.put("아침", "참치 김밥"));
        System.out.println(map.put("점심", "돈까스"));
        System.out.println(map.put("저녁", "삼겹살"));
        System.out.println(map.put("저녁", "오겹살"));
        System.out.println(map.put("저녁", "꽃등심"));
        // Key 중복 ==> 이전 value를 내보내고 (반환) 새 value로 대체
        
        System.out.println(map.toString());     // map 전체 확인
        
        System.out.println("--------------------------------------------------");
        
        // 2. V get (K k) : 해당 Key에 대응되는 Value 반환
        // 단, 일치하는 K가 없으면 null 반환
        System.out.println(map.get("아침"));
        System.out.println(map.get("점심"));
        System.out.println(map.get("저녁"));
        System.out.println(map.get("야식"));  // null - 일치하는 키 없음
        
        // 3. int size() : Map에 저장된 K:V 쌍의 개수를 반환
        System.out.println("size() : " + map.size());
        
        System.out.println("--------------------------------------------------");
        
        // 4. V remove(K k) :
        // Map에서 Key가 일치하는 쌍을 제거
        // 일치하는 Key가 존재하면 Value 반환, 존재하지 않으면 null 반환
        System.out.println(map.remove("아침") + "제거");
        System.out.println(map.remove("점심") + "제거");
        System.out.println(map.remove("야식") + "제거");
        
        System.out.println(map);    // 남은 요소 확인
    }
    
    /**
     * Map에 저장된 모든 요소 순차 접근하기
     */
    public void test2() {
        
        // K는 String ==> 문자열만 작성
        // V는 Object ==> 모든 클래스의 최상의 부모로서 모든 객체 참조 가능 == 모든 자료형 가능
        Map<String, Object> student = new HashMap<>();
        
        // Map을 유사 DTO처럼 사용 - Student에 대한 별도 기능만 없고 나머지는 다 들어있다 ???
        
        student.put("name", "홍길동");  // String
        student.put("grade", 3);        // Integer
        student.put("gender", "남");    // Character
        student.put("avg", 80.4);       // Double
        
        /* 1) Map.keyset() 이용 */
        // ==> key만 모아둔 집합(Set) 반환
        // 모아둔 key 집합에서 key를 하나씩 꺼내서 Map.get(key)를 통해 value를 하나씩 꺼냄
        Set<String> keys = student.keySet();
        System.out.println("Key : " + keys);
        
        for (String key : keys) {
            System.out.printf("Student[%s] : %s \n", key, student.get(key));
        }
        
        System.out.println("--------------------------------------------------");
        
        /* 2) Map.entryset() 이용 */
        // ==> K:V 쌍 형태의 객체를 모아둔 집합(Set) 반환
        
        Set<Entry<String, Object>> set = student.entrySet();
        System.out.println(set);
        
        // 향상된 for문
        for(Entry<String, Object> entry : set) {
            // 나보다 똑똑함 이제
            System.out.printf("Student[%s] : %s \n", entry.getKey(), entry.getValue());
        }
        
        
    }
    
}
