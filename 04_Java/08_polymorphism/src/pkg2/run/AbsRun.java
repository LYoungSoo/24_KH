package pkg2.run;

import pkg2.service.AbsService;

public class AbsRun {
    public static void main(String[] args) {
        AbsService service = new AbsService();
        service.test1();
        service.test2();
    }
}
