package employees;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.net.URI;

@ControllerAdvice
public class EmployeeErrorHandler {

    @ExceptionHandler
    public Mono<ProblemDetail> handle(IllegalArgumentException e) {
        return Mono.just(e)
                .map(EmployeeErrorHandler::toProblemDetail);
    }

    private static ProblemDetail toProblemDetail(IllegalArgumentException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setType(URI.create("employees/bad-request"));
        problemDetail.setTitle("Bad Request");
        return problemDetail;
    }

    @ExceptionHandler
    public Mono<ProblemDetail> handle(WebExchangeBindException e) {
        return Mono.just(e)
                .map(EmployeeErrorHandler::toProblemDetail);
    }

    private static ProblemDetail toProblemDetail(WebExchangeBindException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        problemDetail.setType(URI.create("employees/validation-error"));
        problemDetail.setTitle("Validation Error");
        problemDetail.setProperty("violations", exception.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage())).toList()
        );
        return problemDetail;
    }
}
