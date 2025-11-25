package employees;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class ReactorThreadMain {

    public static void main(String[] args) {

        Scheduler pool = Schedulers.newParallel("p1");

        Flux.just(new Employee("John Doe", 1970), new Employee("Jane Doe", 1980))
                .doOnNext(e -> System.out.println("Filtering: " + e.name() + " on thread " + Thread.currentThread().getName()))
                .filter(e -> e.yearOfBirth() > 1900)


                .doOnNext(e -> System.out.println("Mapping: " + e.name() + " on thread " + Thread.currentThread().getName()))
                .map(Employee::name)


                .doOnNext(e -> System.out.println("Uppercase: " + e + " on thread " + Thread.currentThread().getName()))
                .map(String::toUpperCase)

                .subscribeOn(pool)

                .subscribe(System.out::println);

    }
}
