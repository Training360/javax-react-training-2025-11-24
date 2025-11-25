package training.courseswebclient;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CoursesController {

//    private final EmployeeGateway employeeGateway;

    private final EmployeesClient employeesClient;

    private final CourseService courseService;

    @GetMapping("/employees")
    public Flux<Employee> findAll() {
//        return employeeGateway.findAll();
        return employeesClient.findAll();
    }

    @GetMapping("/attends")
    public Flux<CourseAttendDto> findAllCourseAttends() {
        return courseService
                .findAllCourseAttends()
                .flatMap(attend -> employeesClient.findById(attend.employeeId())
                        .map(e-> new CourseAttendDto(attend.courseCode(), attend.employeeId(), e.name())));

    }
}
