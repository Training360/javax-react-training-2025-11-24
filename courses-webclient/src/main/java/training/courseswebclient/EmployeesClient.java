package training.courseswebclient;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@HttpExchange("/api/employees")
public interface EmployeesClient {

    @GetExchange
    Flux<Employee> findAll();

    @GetExchange("/{id}")
    Mono<Employee> findById(@PathVariable long id);
}
