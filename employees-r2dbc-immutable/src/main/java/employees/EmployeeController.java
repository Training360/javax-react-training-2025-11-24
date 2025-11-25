package employees;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

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

    @GetMapping("/{id}/short")
    public Mono<ResponseEntity<ShortEmployeeDto>> findShortById(@PathVariable long id) {
        return service
                .findShortById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<EmployeeDto>> save(@Valid @RequestBody Mono<EmployeeDto> employeeDto,
                                                  UriComponentsBuilder uriComponentsBuilder) {
        return service
                .save(employeeDto)
                .map(e -> ResponseEntity.created(uriComponentsBuilder.path("/api/employees/{id}")
                        .buildAndExpand(e.id()).toUri()).body(e));

    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<EmployeeDto>> update(@PathVariable long id, @Valid @RequestBody Mono<EmployeeDto> employeeDto) {
        return employeeDto
                .filter(e -> e.id() != null && e.id() == id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("ID mismatch: %d".formatted(id))))
                .flatMap(e -> service.save(Mono.just(e)))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<Void> deleteById(@PathVariable long id) {
        return service.deleteById(id);
    }
}
