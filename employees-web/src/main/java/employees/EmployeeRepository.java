package employees;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EmployeeRepository {

    private final AtomicLong sequenceGenerator = new AtomicLong();

    private final List<Employee> employees = new ArrayList<>(
        List.of(new Employee(sequenceGenerator.incrementAndGet(), "John"), new Employee(sequenceGenerator.incrementAndGet(), "Jane")));

    public Flux<Employee> findAll() {
        return Flux.fromIterable(employees);
    }

    public Mono<Employee> findById(long id) {
        return Flux.fromIterable(employees)
                .filter(e -> e.getId().equals(id))
                .singleOrEmpty();
    }

    public void testPeek() {
        employees.stream().peek(e -> e.setName("Test")).count();
    }

    public Mono<Employee> save(Employee employee) {
        if (employee.getId() == null) {
            Employee newEmployee = new Employee(sequenceGenerator.incrementAndGet(), employee.getName());
            employees.add(newEmployee);
            return Mono.just(newEmployee);
        } else {
            return findById(employee.getId())
                    // Side effect - mellékhatás - SOHA, DE SOHA, DE SOHA NEM CSINÁLJUNK ILYET!
                    // Ez az operátor Java 8-ban a peek()
                    .doOnNext(e -> e.setName(employee.getName()))
                    .map(e -> new Employee(e.getId(), e.getName()));
        }
    }

    public Mono<Void> deleteById(long id) {
        boolean success = employees.removeIf(e -> e.getId().equals(id));
        if (success) {
            return Mono.empty();
        } else {
            return Mono.error(new IllegalArgumentException("Employee not found: %d".formatted(id)));
        }
    }
}
