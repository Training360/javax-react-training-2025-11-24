package training.courseswebclient;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CourseService {

    public Flux<CourseAttend> findAllCourseAttends() {
        return Flux.just(
                new CourseAttend("SPRING-BOOT", 1L),
                new CourseAttend("SPRING-BOOT", 2L),
                new CourseAttend("REACTOR", 1L)
        );
    }
}
