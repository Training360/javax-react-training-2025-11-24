package employees;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;

    public Flux<EmployeeDto> findAll() {
        return repository.findAll().map(EmployeeService::toDto);
    }

    public Mono<EmployeeDto> findById(long id) {
        return repository.findById(id).map(EmployeeService::toDto);
    }

    // MapStruct kéne használni
    private static EmployeeDto toDto(Employee employee) {
        return new EmployeeDto(employee.getId(), employee.getName());
    }
}
