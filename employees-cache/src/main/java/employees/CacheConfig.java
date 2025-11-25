package employees;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.*;
import tools.jackson.databind.ObjectMapper;

@Configuration(proxyBeanMethods = false)
public class CacheConfig {

    @Bean
    public ReactiveRedisTemplate<Long, EmployeeDto> reactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory) {
        ObjectMapper objectMapper = new ObjectMapper();
        RedisSerializer<EmployeeDto> redisSerializer = new JacksonJsonRedisSerializer<>(objectMapper, EmployeeDto.class);
        RedisSerializationContext<Long, EmployeeDto> context = RedisSerializationContext
                .<Long, EmployeeDto>newSerializationContext(RedisSerializer.string())
                .key(new GenericToStringSerializer<>(Long.class))
                .hashKey(new JacksonJsonRedisSerializer<>(Long.class))
                .value(redisSerializer)
                .hashValue(redisSerializer)
                .build();
        return new ReactiveRedisTemplate<>(connectionFactory, context);
    }
}
