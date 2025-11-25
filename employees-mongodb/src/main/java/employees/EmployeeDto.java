package employees;

import jakarta.validation.constraints.NotBlank;

public record EmployeeDto(String id, @NotBlank String name) {
}
