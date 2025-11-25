package employees;

import jakarta.validation.constraints.NotBlank;

public record EmployeeDto(Long id, @NotBlank String name) {
}
