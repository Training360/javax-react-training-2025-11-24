package employees;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

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

    public Mono<EmployeeDto> save(Mono<EmployeeDto> employeeDto) {
        return employeeDto
                .map(EmployeeService::toEntity)
                .flatMap(repository::save)
                .map(EmployeeService::toDto);
    }

    public Mono<Void> deleteById(long id) {
        return repository.deleteById(id);
    }

    // MapStruct kéne használni
    private static EmployeeDto toDto(Employee employee) {
        return new EmployeeDto(employee.getId(), employee.getName());
    }

    private static Employee toEntity(EmployeeDto employeeDto) {
        return new Employee(employeeDto.id(), employeeDto.name());
    }
}
