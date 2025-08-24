package xhosh.dev.taskle.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import xhosh.dev.taskle.mapper.UserCreateEditDtoMapper;
import xhosh.dev.taskle.mapper.UserReadDtoMapper;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    public UserCreateEditDtoMapper userCreateEditDtoMapper() {
        return new UserCreateEditDtoMapper();
    }

    @Bean
    public UserReadDtoMapper userReadDtoMapper() {
        return new UserReadDtoMapper();
    }
}
