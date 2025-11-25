package training.courseswebclient;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Flux;

@HttpExchange("/api/employees")
public interface EmployeesClient {

    @GetExchange
    Flux<Employee> findAll();
}
