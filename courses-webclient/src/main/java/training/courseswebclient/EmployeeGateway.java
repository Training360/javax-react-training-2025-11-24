package training.courseswebclient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class EmployeeGateway {

    public Flux<Employee> findAll() {
        return WebClient
                .create("http://localhost:8080/api/employees")
                .get()
                .retrieve()
                .bodyToFlux(Employee.class);
    }
}
