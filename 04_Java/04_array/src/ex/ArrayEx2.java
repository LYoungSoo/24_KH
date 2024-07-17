package ex;
import java.util.Arrays;
import java.util.Scanner;

public class ArrayEx2 {
    
    Scanner sc = new Scanner(System.in);
    
    /** 얕은 복사
     * - 참조형 변수가 가지고 있는 "주소"만 복사하는 것!!
     *
     * - 서로 다른 변수가 같은 배열/객체를 참조하기 떄문에 같은 값을 공유!!!
     */
    
    public void method1() {
        // 원본 배열
        int[] arr = {10,20,30,40,50};   // int 배열 자료형
        
        // 얕은 복사한 배열 선언
        int[] copyArr = arr;
        
        System.out.println("[변경 전]");
        System.out.println("arr :\t\t" + Arrays.toString(arr));
        System.out.println("copyArr :\t" + Arrays.toString(copyArr));
        
        // 복사한 배열의 값 변경
        copyArr[3] = 4444;
        copyArr[4] = 55555;
        
        System.out.println("[변경 후]");
        System.out.println("arr :\t\t" + Arrays.toString(arr));
        System.out.println("copyArr :\t" + Arrays.toString(copyArr));
        
        // 복사한 배열을 수정 했지만 원본도 같이 수정됨
        // ==> 얕은 복사는 주소만 복사해서 같은 배열을 참조하게 만들어 값을 공유함
        
        // 야 이거 C언어 포인터잖아 ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ
        // 환장하겠다 진짜
    }
    
    /**깊은 복사
     * - 원본 배열 / 갹체가 가지고 있는 값을 똑같이 복사한 배열 / 객체를 만드는 것 (복제의 개념)
     * - 새로운 배열 / 객체가 만들어 지는 것으로 원본과는 독립되어 있음 ==> 값을 공유하지 않는다!!!
     */
    public void method2() {
        // 원본 배열
        int[] arr = {10,20,30,40,50};
        
        // 깊은 복사 1 : for 문 이용
        int[] copyArr = new int[arr.length];    // 원본 크기 새 배열 생성
        
        // 원본 배열 요소의 값을 같은 인덱스의 복사된 배열 요소에 대입
        for(int i=0; i<arr.length; i++) {
            copyArr[i] = arr[i];
        }
        
        // 깊은 복사 2 : System.arraycopy() 이용
        // System.arraycopy (원본 배열명, 원본 복사 시작 인덱스,
        //                  복사 배열명, 복사 배열의 삽입 시작 인덱스, 복사 길이)
        int[] copyArr2 = new int[arr.length];
        System.arraycopy(arr, 0, copyArr2, 0, arr.length);
        
        // 원본, 복사1, 복사2의 0번 인덱스 값을 변경
        arr[0] = 1111;
        copyArr[0] = 2222;
        copyArr2[0] = 3333;
        
        System.out.println("arr :\t\t" + Arrays.toString(arr));
        System.out.println("copyArr :\t" + Arrays.toString(copyArr));
        System.out.println("copyArr2 :\t" + Arrays.toString(copyArr2));
        
        // 이하 자동완성에서 본 친구들 테스트=============================
        System.out.println("----------------------------------------");
        int[] copyArr6 = Arrays.copyOf(arr, arr.length);
        int[] copyArr7 = arr.clone();
        copyArr6[0] = 7777;
        copyArr7[0] = 8888;
        System.out.println("copyArr6 :\t" + Arrays.toString(copyArr6));
        System.out.println("copyArr7 :\t" + Arrays.toString(copyArr7));
    }
    
//    /**
//     */
//    public void method3() {}
    
    /**
     * 실행을!
     */
    public void display () {
        System.out.println("method1() : ");
        System.out.println("method2() : ");
        System.out.println("method3() : ");
        System.out.println("method4() : ");
        
        System.out.print(" Ex2 : ");
        switch (sc.nextInt()) {
            case 1: method1(); break;
            case 2: method2(); break;
            default: break;
            /*case 1: method1(); break;
            default: break;*/
        }
    }
}
