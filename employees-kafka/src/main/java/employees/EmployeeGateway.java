package employees;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class EmployeeGateway {

    @Bean
    public Consumer<Flux<EmployeeDto>> employeeEventsHandler() {
        return employee -> employee.subscribe(e -> log.info("Received events: {}", e));
    }
}
