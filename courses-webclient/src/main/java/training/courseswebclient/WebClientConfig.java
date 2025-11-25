package training.courseswebclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration(proxyBeanMethods = false)
public class WebClientConfig {

    @Bean
    public EmployeesClient employeesClient() {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder()
                .exchangeAdapter(WebClientAdapter.create(webClient))
                .build();
        return factory.createClient(EmployeesClient.class);
    }
}
