package employees;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee, Long> {

    @Query("select id, name from employees")
    Flux<EmployeeDto> findDtoAll() ;

    <T> Mono<T> findDtoById(Long id, Class<T> clazz);

}
