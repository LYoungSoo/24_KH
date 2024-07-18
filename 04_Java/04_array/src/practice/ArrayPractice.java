package practice;

import java.util.Scanner;

public class ArrayPractice {
    Scanner sc = new Scanner(System.in);
    
    /**
     * [실습 문제 1]
     * 길이가 9인 배열을 선언 및 할당하고, 1부터 9까지의 값을 반복문을 이용하여
     * 순서대로 배열의 각 인덱스 요소에 대입하고 출력한 후 짝수 번째 인덱스 값의 합을 출력하세요.
     * (0 번째 인덱스는 짝수로 취급)
     */
    public void practice1 () {
        int[] arr = new int[9];
        
        String even = "";
        
        // 배열 요소 초기화
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + 1;
        }
        
        // 출력 및 합계
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
            if (i % 2 == 0) {
                sum += arr[i];
                even += arr[i] + " ";
            }
        }
        System.out.println("\n짝수번째 인덱스 ( " + even + ") 합 : " + sum);
    }       // 뭐 70퍼는 자동완성이 다 만들어주냐 개무섭게
    
    /**
     * 길이가 9인 배열을 선언 및 할당하고, 9부터 1까지의 값을 반복문을 이용하여
     * 순서대로 배열의 각 인덱스 요소에 대입하고 출력한 후
     * 홀수 번째 인덱스 값의 합을 출력하세요. (0번째 인덱스는 짝수로 취급)
     */
    public void practice2 () {
        System.out.println("practice 2----------");
        
        int sum = 0;
        String odd = "";
        int[] arr = new int[9];                 // 배열선언 및 할당
        
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr.length - i;              // 대입
            System.out.print(arr[i] + " ");
            if (i % 2 == 1) {
                sum += arr[i];
                odd += arr[i] + " ";
            }
        }
        System.out.println("\n홀수 번째 인덱스 ( " + odd + ") 합 : " + sum);
        
    }
    
    /**
     * 사용자에게 입력 받은 양의 정수만큼 배열 크기를 할당하고
     * 1부터 입력 받은 값까지 배열에 초기화한 후 출력하세요.
     */
    public void practice3 () {
        System.out.println("practice 3----------");
        System.out.print("양의 정수 : ");
        int num = sc.nextInt();
        
        int[] arr = new int[num];
        
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + 1;
            System.out.print(arr[i] + "\t");
            if ((i + 1) % 20 == 0) System.out.println();    // 20개씩 끊어서 줄바꿈
        }
    }
    
    /**
     * 정수 5개를 입력 받아 배열을 초기화 하고
     * 검색할 정수를 하나 입력 받아 배열에서 같은 수가 있는 인덱스를 찾아 출력.
     * 배열에 같은 수가 없을 경우 "일치하는 값이 존재하지 않습니다" 출력
     */
    public void practice4 () {
        System.out.println("practice 4----------");
        int[] arr = new int[5];
        int num;
        boolean flag = false;
        
        for (int i = 0; i < 5; i++) {
            System.out.print("입력 " + i + " : ");
            arr[i] = sc.nextInt();
        }
        
        System.out.print("검색할 값 : ");
        num = sc.nextInt();
        
        for (int i = 0; i < arr.length; i++) {
            if (num == arr[i]) {
                System.out.print("인덱스 " + i);
                flag = true;
            }
        }
        if (!flag) System.out.println("일치하는 값이 존재하지 않습니다.");
        
        for (int i = 0; i < arr.length; i++) {
            System.out.print("[" + i + "]:" + arr[i] + " ");
        }
    }
    
    /**
     * 사용자가 배열의 길이를 직접 입력하여 그 값만큼 정수형 배열을 선언 및 할당하고
     * 배열의 크기만큼 사용자가 직접 값을 입력하여 각각의 인덱스에 값을 초기화 하세요.
     * 그리고 배열 전체 값을 나열하고 각 인덱스에 저장된 값들의 합을 출력하세요.
     */
    public void practice5 () {
        System.out.println("practice 5----------");
        sc = new Scanner(System.in);
        int sum;
        
        System.out.print("정수 : ");
        sum = sc.nextInt();
        
        int[] arr = new int[sum];
        sum = 0;
        for (int i = 0; i < arr.length; i++) {
            System.out.print("배열 " + i + "번째 인덱스에 넣을 값 : ");
            arr[i] = sc.nextInt();
            sum += arr[i];
        }
        
        for (int i = 0; i < arr.length; i++) {
            System.out.print("[" + i + "]:" + arr[i] + " ");
        }
        // for(int value : arr)  System.out.print(value + " ");
        System.out.println("\n총 합 : " + sum);
        
    }
    
    /**
     * 주민등록번호를 입력받아 char 배열에 저장한 후 출력하세요.
     * 단, char 배열 저장 시 성별을 나타내는 숫자 이후부터 *로 지정하세요.
     */
    public void practice6 () {
        System.out.println("practice 6----------");
        char[] arr = new char[14];
        String str;
//        char[] arr2 = new char[14];
        
        System.out.print("주민등록번호(-포함) : ");
        str = sc.next();
        for (int i = 0; i < arr.length; i++) {
            //int i = 0 ; i < str.length() ; i++) str.length() 은 length 뒤에 () 붙음.
            // 여깄는 length는 string 글자길이를 반환하는 함수니까
            arr[i] = str.charAt(i);
            if (i > 7) arr[i] = '*';
        }
//        arr = sc.next().toCharArray();
        for (char c : arr) {
            System.out.print(c);
        }
    }
    
    /**
     * '
     * 3 이상인 홀수를 입력 받아 배열의 중간까지는 1부터 1씩 증가하여 오름차순으로 값을 넣고,
     * 중간 이후부터 끝까지는 1씩 감소하여 내림차순으로 값을 넣어 출력하세요
     * 단, 입력한 정수가 홀수가 아니거나 3 미만일 경우 "다시 입력하세요"를 출력하고 다시 정수를 받도록 하세요
     */
    public void practice7 () {
        System.out.println("practice 7----------");
        int num;
        
        System.out.print("정수 : ");
        num = sc.nextInt();
        
        while (num % 2 == 0 || num < 3) {
            System.out.println("다시 입력하세요.");
            System.out.print("정수 : ");
            num = sc.nextInt();
        }
        
        int[] arr = new int[num];   // 배열 생성
        // 대입
        for (int i = 0; i < arr.length; i++) {
            if (i * 2 > arr.length) arr[i] = arr.length - i;  // i > arr.length/2
            else arr[i] = i + 1;
        }
        
        // 출력
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                System.out.print(arr[i]);
                continue;
            }
            System.out.print(", " + arr[i]);
        }


//        do{
//            System.out.print("정수 : ");
//            num = sc.nextInt();
//        }while(num % 2 == 0 || num <3);
        
    }
    
    /**
     * 10개의 값을 저장할 수 있는 정수형 배열을 선언 및 할당하고,
     * 1 ~ 10 사이의 난수를 발생시켜 배열에 초기화한 후 출력하세요.
     */
    public void practice8 () {
        int[] arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random()*10 + 1);
        }
        System.out.print("발생한 난수 : ");
        for(int num : arr) System.out.print(num + " ");
    }
    
    /**
     * 10개의 값을 저장할 수 있는 정수형 배열을 선언 및 할당하고,
     * 1 ~ 10 사이의 난수를 발생시켜 배열에 초기화 후
     * 배열 천제 값과 그 값 중에서 최대값과 최소값을 출력하세요
     */
    public void practice9 () {
        int[] arr = new int[10];
        int max=1,min=10;   // 배열 첫번째거 넣어도 되는데 코드가 너무 길어지는듯
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random()*10 + 1);
            if (max<arr[i]) max = arr[i];
            if (min>arr[i]) min = arr[i];
        }
        System.out.print("발생한 난수 : ");
        for(int num : arr) System.out.print(num + " ");
        System.out.println("\n최대값 : " + max);
        System.out.println("최소값 : " + min);
    }
    
    /**
     * 10개의 값을 저장할 수 있는 정수형 배열을 선언 및 할당하고
     * 1 ~ 10 사이의 난수를 발생시켜 중복된 값이 없게 배열에 초기화한 후 출력하세요
     */
    public void practice10 () {
        int[] arr = new int[10];
        boolean check = true;
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            do {
                check = false;
                arr[i] = (int) (Math.random() * 10 + 1);
                for (int k = 0; k < i; k++) {
                    if (arr[i] == arr[k]) check = true;
                }
            } while (check);
        }
        for (int num : arr) System.out.print(num + " ");
    }
    
    /**
     * 로또 번호 자동 생성기 프로그램을 만들기.
     * (중복 값 없이 오름차순으로 정렬하여 출력하세요.)
     */
    public void practice11 () {
        int[] arr = new int[10];
        boolean check = true;
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            do {
                check = false;
                arr[i] = (int) (Math.random() * 10 + 1);
                for (int k = 0; k < i; k++) {
                    if (arr[i] == arr[k]) check = true;
                }
            } while (check);
        }
        for (int num : arr) System.out.print(num + " ");
    }
    
    /**
     *
     */
    public void practice12 () {
    }
    
    /**
     *
     */
    public void practice13 () {
    }
    
    /**
     * 실행!
     */
    public void display () {
        System.out.print(" Ex2 (1~13) : ");
        switch (sc.nextInt()) {
            case 1:
                practice1();
                break;
            case 2:
                practice2();
                break;
            case 3:
                practice3();
                break;
            case 4:
                practice4();
                break;
            case 5:
                practice5();
                break;
            case 6:
                practice6();
                break;
            case 7:
                practice7();
                break;
            case 8:
                practice8();
                break;
            case 9:
                practice9();
                break;
            case 10:
                practice10();
                break;
            case 11:
                practice11();
                break;
            case 12:
                practice12();
                break;
            case 13:
                practice13();
                break;
            default:
                break;
        }
    }
    
}
