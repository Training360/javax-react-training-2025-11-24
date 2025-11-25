package employees;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee, String> {

    Flux<EmployeeDto> findAllBy() ;

    <T> Mono<T> findDtoById(String id, Class<T> clazz);

//    @Query("""
//        { 'name' : ?0, 'year_of_birth' : {$gte: ?#{#name}} }
//        """)
    Flux<EmployeeDto> findByName(String name);

}
