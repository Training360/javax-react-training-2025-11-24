package employees;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @GetMapping
    public Flux<EmployeeDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<EmployeeDto>> findById(@PathVariable long id) {
        return service
                .findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<EmployeeDto> save(@RequestBody Mono<EmployeeDto> employeeDto) {
        return service.save(employeeDto);
    }

    @PutMapping("/{id}")
    public Mono<EmployeeDto> update(@PathVariable long id, @RequestBody Mono<EmployeeDto> employeeDto) {
        return service.save(employeeDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable long id) {
        return service.deleteById(id);
    }
}
