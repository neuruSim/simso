package com.simso.global.config;


import com.simso.domain.user.repository.UserRepository;
import com.simso.domain.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;
    private final EntityManager em;
    private final UserRepository userRepository;


    public SpringConfig(DataSource dataSource, EntityManager em, UserRepository userRepository) {
        this.dataSource = dataSource;
        this.em = em;
        this.userRepository = userRepository;

    }


    @Bean
    public UserService userService() {
        return new UserService(userRepository);
    }


}
