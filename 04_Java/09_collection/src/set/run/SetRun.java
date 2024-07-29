package set.run;

import set.service.SetService;

public class SetRun {
    public static void main(String[] args) {
        SetService setService = new SetService();
//        setService.test1();
//        setService.test2();
//        setService.test3();
//        setService.test4();
        setService.lottoNumberGenerator();
    }
}
