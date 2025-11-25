package employees;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class TimeoutDemoMain {

    public static void main(String[] args) {
        Mono.fromCallable(TimeoutDemoMain::bokkingOperation)
                .subscribeOn(Schedulers.newParallel("p1"))
                .timeout(Duration.ofSeconds(2))
                // Interruptot is hív
                .onErrorResume(TimeoutException.class, t -> Mono.just(-1))
                .subscribe(System.out::println);
    }

    public static int bokkingOperation() {
        // Itt is kezelni kell a timeoutot
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            try {
                Random random = SecureRandom.getInstanceStrong();
                random.nextDouble();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
//        try {
//            Thread.sleep(Duration.ofSeconds(5));
//
//        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("Operation finished");
        return 110;
    }
}
