package employees;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;

    private final ReactiveRedisTemplate reactiveRedisTemplate;

    public Flux<EmployeeDto> findAll() {
        return repository.findDtoAll();
    }

    public Mono<EmployeeDto> findById(long id) {
        return reactiveRedisTemplate.opsForValue().get(id)
                .log()
                .switchIfEmpty(
                        repository
                                .findDtoById(id, EmployeeDto.class)
                                .flatMap(employeeDto ->
                                        reactiveRedisTemplate.opsForValue().set(id, employeeDto)
                                                .thenReturn(employeeDto))
                )
                .log();
    }

    public Mono<ShortEmployeeDto> findShortById(long id) {
        return repository.findDtoById(id, ShortEmployeeDto.class);
    }

    public Mono<EmployeeDto> save(Mono<EmployeeDto> employeeDto) {
        return employeeDto
                .map(EmployeeService::toEntity)
                .flatMap(repository::save)
                .map(EmployeeService::toDto)
                .flatMap(dto -> reactiveRedisTemplate.opsForValue().set(dto.id(), dto)
                        .thenReturn(dto))
                ;
    }

    public Mono<Void> deleteById(long id) {
        return repository.deleteById(id);
    }

    // MapStruct kéne használni
    private static EmployeeDto toDto(Employee employee) {
        return new EmployeeDto(employee.id(), employee.name());
    }

    private static Employee toEntity(EmployeeDto employeeDto) {
        return new Employee(employeeDto.id(), employeeDto.name());
    }
}
