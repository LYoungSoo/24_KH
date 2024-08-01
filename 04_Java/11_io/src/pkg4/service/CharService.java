package pkg4.service;

/*  문자 기반 스트림
 * - 2 Byte 문자(Char) 단위로 입/출력 하는 스트림
 * - 문자만 작성된 파일(txt), 채팅, 인터넷 요청(주소) / 응답 (Html, Css)
 * - Reader(입력), Writer(출력) 최상위 인터페이스
 */

import java.io.*;

public class CharService {
    
    /**
     * 문자 기반 스트림을 이용해 출력하기 1
     * - 기반 스트림만 이용
     */
    public void fileOutput1() {
        
        // StringBuilder : String 불변성을 해결한 객체
        StringBuilder sb = new StringBuilder();
        
        sb.append("오늘은 8월 1일 입니다!\n");
        sb.append("아쉽게도 목요일이네요...\n");
        sb.append("아 벌써 당땡긴다\n");
        sb.append("HaRiBo");
        
        String str = sb.toString();
        System.out.println(str);
        
        // 문자 기반 스트림 참조 변수 선언
        FileWriter fw = null;
        
        try {
            // 예외가 발생할 것 같은 코드를 작성하는 구문
            
            // 해당 경로 폴더가 존재하지 않을 경우 폴더 생성하기
            File folder = new File("/io_test/char");
            if (!folder.exists()) folder.mkdirs();
            
            // 문자 기반 스트림 객체 생성
            fw = new FileWriter("/io_test/char/문자테스트.txt");
            
            // 문자열을 지정된 파일에 출력
            // ==> 자동으로 전달한 String을 한 글자씩 출력
            fw.write(str);
            
            // 스트림 밖으로 데이터를 흘려 보냄(flush)
//            fw.flush();
            System.out.println("[출력 완료]");
            
        } catch (Exception e) {
            // try에서 던져진 예외를 잡아서 처리하는 구문
            e.printStackTrace();
        } finally {
            // try에서 예외 발생 여부 관계 없이 무조건 수행하는 구문
            try {
                // java. io. Writer Closes the stream, flushing it first.
                // Once the stream has been closed, further write() or flush() invocations
                // will cause an IOException to be thrown.
                // Closing a previously closed stream has no effect.
                
                // close() : flush() 후 스트림 닫기
                if (fw != null) fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }   // fileOutput1
    
    /**
     *
     */
    public void fileOutput2() {
        
        StringBuilder sb = new StringBuilder();
        sb.append("문자 기반 보조 스트림\n");
        sb.append("BufferedWriter 를 이용하여\n");
        sb.append("출력한 결과 입니다!!!\n");
        
        String str = sb.toString();
        
        // 스트림 참조 변수 선언
        FileWriter fw = null;
        BufferedWriter bw = null;
        
        try {
            // 스트림 객체 생성
            fw = new FileWriter("/io_test/char/보조스트림출력.txt");
            bw = new BufferedWriter(fw);
            
            File folder = new File("/io_test/char");
            if (!folder.exists()) folder.mkdirs();
            
            // 보조 스트림을 이용해 출력
            bw.write(str);
            
            // 스트림에 존재하는 데이터 내보내기
            bw.flush();
            
            System.out.println("[출력 완료!!]");
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // - 보조 스트림을 닫으면 기반 스트림도 같이 닫힘
                // - close() 호출 시 자동으로 flush()도 호출 됨
                if (bw != null) bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } // fileOutput2()
    
    /**
     * 문자 기반 입력 스트림을 이용해 /io_test/char/문자테스트.txt 파일 내용을 읽어와 콘솔에 출력
     */
    public void fileInput1() {
        
        FileReader fr = null;
        StringBuilder sb = new StringBuilder();
        
        try {
            fr = new FileReader("/io_test/char/문자테스트.txt");
            
            int value = 0;
            
//            while ((value = fr.read()) != -1) // 이거 while로 돌리면 맨앞 날아가지 않나?
//            do - while로 묶어야 안전할듯?
            while(true) {
                // fr.read() : 문자 하나(2byte)를 읽어옴, 없으면 -1
                value = fr.read();
                
                // 더 이상 읽어올 문자가 없을 경우
                if (value == -1) break;
                
                sb.append((char) value);
            }
            
            System.out.println(sb.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }   // fileInput1()
    
    
    /**
     * 문자 기반 입력 스트림을 이용해 /io_test/char/문자테스트.txt 파일 내용을 읽어와 콘솔에 출력
     */
    public void fileInput1_1() {
        
        // 스트림 참조 변수 선언
        FileReader fr = null;
        BufferedReader br = null;
        
        StringBuilder sb = new StringBuilder();
        
        try {
            // 스트림 객체 생성
            fr = new FileReader("/io_test/char/문자테스트.txt");
            br = new BufferedReader(fr);
            
            while (br.ready()) {
                sb.append(br.readLine());
                sb.append("\n");
            }
            
            System.out.println(sb.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }   // fileInput1_1()
    
    /**
     * 문자 기반 스트림을 이용해 입력 받기 2
     * - 기반 + 보조 스트림
     */
    public void fileInput2() {
        
        // 스트림 참조 변수 선언
        FileReader fr = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        
        try {
            // 스트림 객체 생성
            fr = new FileReader("/io_test/char/문자테스트.txt");
            br = new BufferedReader(fr);
            
            // String br.readLine() ==> 한 줄을 읽어옴, 없으면 null 반환, \n은 읽어오지 않음!
            String line = null; // 읽어온 한 줄을 저장할 변수
            
            while (true) {
                line = br.readLine();
                // 읽어온 내용이 없다면 반복 종료
                if (line == null) break;
                
                sb.append(line);
                sb.append("\n");
            }
            
            System.out.println(sb.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }   // fileInput2()
    
}
/*
    public void fileOutput3() {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        
        FileWriter fw = null;
        BufferedWriter bw = null;
        
        try {
            fw = new FileWriter("/io_test/char/.txt");
            
            File folder = new File("/io_test/char");
            
            if (!folder.exists()) folder.mkdirs();
            bw = new BufferedWriter(fw);
        } catch (Exception e) {
            e.printStackTrace();
            
        } finally {
            try {
                if(fw != null) fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }   // fileOutput3()
 */