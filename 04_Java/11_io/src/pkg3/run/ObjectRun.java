package pkg3.run;

import pkg3.service.ObjectService;

public class ObjectRun {
    public static void main(String[] args) {
        ObjectService service = new ObjectService();
//        service.objectOutput();
        service.objectInput();
    }
}
