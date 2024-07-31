package pkg1.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class FileService {
    /* File 클래스
     *
     * - java.io 패키지
     *
     * - 파일/디렉토리(폴더)를 관리하는 클래스
     *   (파일/디렉토리 존재 유무 관계 없음)
     *
     * - 파일 생성, 제거, 이름, 크기, 마지막 수정일, 존재여부 등의 기능 제공
     *
     * - File 클래스 메서드
     *
     * boolean  mkdir()          : 최 하위 디렉토리 생성
     * boolean  mkdirs()         : 최 하위 디렉토리를 비롯한 경로상의 모든 디렉토리 생성
     * boolean  createNewFile()  : 파일 생성
     * boolean  delete()         : 파일/디렉토리 삭제
     * String   getName()        : 파일 이름 반환
     * String   parent()         : 파일이 저장된 디렉토리 반환
     * String   getPath()        : 전체 경로 반환
     * boolean  isFile()         : 현재 File 객체가 관리하는게 파일이면 true
     * boolean  isDirectory()    : 현재 File 객체가 관리하는게 디렉토리 true
     * boolean  exists()         : 파일/디렉토리가 존재하면 true, 아님 false
     * long     length()         : 파일 크기 반환
     * long     lastModified()   : 파일 마지막 수정일 (1970.01.01 09:00 부터 현재까지 지난 시간을 ms 단위로 반환)
     * String[] list()          : 디렉토리 내 파일 목록을 String[] 배열로 반환
     * File[]   listFiles()     : 디렉토리 내 파일 목록을 File[] 배열로 반환
     */
    
    
    /* [ 개발자 상식! ]
     * OS : 운영체제
     *
     * Windows : 폴더 == Linux/Max : 디렉토리
     *
     * 경로 표기 방법(하위 폴더)
     * Windows   : \ (백슬래시)
     * Linux/Mac : / (슬래시)
     *
     * ==> Java는 모든 운영체제가 호환 되기 때문에 경로 표기 방법도 모두 호환된다!!
     *
     * 최상위 경로(root) : / 또는 \
     * - Windows : C:\\ , D:\\
     * - Linux/Max : /, /Users, /매킨토시도 되나?
     *
     * 상대 경로
     * 절대 경로
     *
     */
    
    /**
     * File 클래스로 객체 생성 + 폴더 관리
     */
    public void test1() {
        
        // directory == folder
//        File directory = new File("C:\\workspace\\04_Java\\11_io\\src\\ ~~ ");
//        File directory = new File("../../io_test/20240731");
        File directory = new File("/io_test/20240731");
        System.out.println("폴더 존재 유무 : " + directory.exists());
        
        // 지정된 경로의 폴더가 존재하지 않는 경우
        if(!directory.exists()) {
            directory.mkdirs();
            if(directory.exists()) System.out.println("폴더가 생성되었습니다");
        }
    }
    
    /**
     * File 객체를 이용해서 지정된 경로에 파일 생성하기
     */
    public void test2() {
        // 경로 표기 방법 : \ 또는 /
        // '\'는 문자열(String)에서 Escape 문자(\n, \t ...)를 위한 키워드이기 때문에
        // '\' 문자열을 나타내고 싶으면 "\\" 두번 작성해야 한다!!
        // "\\" ==> '\' (출력)
        File file = new File("\\io_test\\20240731\\파일생성.txt");
        System.out.println("\\io_test\\20240731\\파일생성.txt");
        
        // 지정된 경로의 파일이 존재하지 않는다면
        if(!file.exists()) {
            
            try {   // 예외가 발생할 것 같은 구문을 내부에 작성 후 시도
                // 파일 생성이 성공한 경우
                if (file.createNewFile()) {
                    System.out.println(file.getName() + " 파일이 성공적으로! 생성 되었습니다");
                }
            } catch (IOException e) {
                System.out.println("---- 파일 생성 중 예외 발생 ----");
                e.printStackTrace();    // 예외 종류, 메세지, 예외가 발생한 위치 출력
                // java.io.IOException: 지정된 경로를 찾을 수 없습니다
            }
        }
    }
    
    /**
     * File 클래스 제공 메서드 활용
     */
    public void test3() {
        
        // File 객체를 생성하여 현재 프로젝트 폴더를 관리
        
//        File directory = new File("../../io_test/20240731");
//        File directory = new File("C:\\workspace\\04_Java\\11_io\\src");
        
        // C:\workspace\04_Java\11_io
//        File project = new File("\\workspace\\04_Java\\11_io");
        File project = new File("../../io_test/20240731");
        
        
        // File[] listFiles()
        // : 디렉토리 내 파일 목록을 File[] 배열로 반환
        File[] files = project.listFiles();
        
        for (File f : files) {
            
            // 해당 위치에 존재하는 모든 파일의 모든 경로(절대 경로)를 출력
//            System.out.println(f.toString());
            
            // 파일명
            String fileName = f.getName();
            
            // 마지막으로 수정한 날짜
            // ==> 1970.01.01 09:00:00.00 기준으로 지난 시간 만큼을 ms 단위로 반환
            long lastModified = f.lastModified();
            
            // SimpleDateFormat : 간단히 날짜 형식을 지정할 수 있는 기능을 제공하는 클래스
            // y : year   (연도) (2(yy), 4(yyyy) 글자)
            // M : month  (월)
            // d : day    (일)
            // a : am/pm  (오전, 오후)
            // h : hour   (시)   /   HH 도 있던데?
            // m : minute (분)
            // s : second
            // DayOfWeek의 getValue() 로 요일 (월(1) ~ 일(7))
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd a h:mm:ss");
            
            // 시간을 지정된 형식으로 변환
            String date = sdf.format(lastModified);
            
            // 파일 유형
            String type = null;
            if(f.isFile()) type = "파일";
            else           type = "폴더";
            
            String kb = null;
            if(f.isFile()) {
                
                // 파일 크기
                long fileSize = f.length(); // byte 단위
                
                // Byte ==> KB 변경
                long size = fileSize / 1024;
                if (size < 1) size = 1; // 1KB 미만은 1로 표시
                
                kb = size + "KB";
                if (size == 1) kb += " (" + f.length() + " Byte)";
            }
            
            // 출력할 문자열
            String result = String.format("%-20s %-20s %-5s %-10s",
                    fileName, date, type, kb == null ? "-" : kb);
            System.out.println(result);
        }
        
    
    }
    
}
