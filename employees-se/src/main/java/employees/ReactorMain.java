package employees;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.stream.Stream;

public class ReactorMain {

    public static void main(String[] args) {
//        Stream.of(new Employee("John", 1980), new Employee("Jane", 1985))
//                        .forEach(System.out::println);

        // Non blocking backpressure = nem árasztjuk el a fogyasztót
        String name = Flux.just(new Employee("John", 1980), new Employee("Jane", 1985))
                .filter(employee -> employee.yearOfBirth() > 1982)
                .map(Employee::name)
//                .subscribe(System.out::println);
                // SOHA NEM HASZNÁLHATJUK!!
                .blockFirst();
        // Block metódusok Spring WebFlux alkalmazásban kivételt dobnak!!!!
        System.out.println(name);

        Flux.just(new Employee("John", 1980), new Employee("Jane", 1985))
                .filter(employee -> employee.yearOfBirth() > 1982)
                        .singleOrEmpty()
                                .subscribe(System.out::println);

//        Optional.of(new Employee("John", 1980)).stream().forEach(System.out::println);

        Mono.just(new Employee("John", 1980))
                .subscribe(System.out::println);



    }
}
