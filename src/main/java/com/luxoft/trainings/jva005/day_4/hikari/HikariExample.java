package com.luxoft.trainings.jva005.day_4.hikari;

import com.luxoft.trainings.jva005.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class HikariExample {

    private static final Logger log = LoggerFactory.getLogger(HikariExample.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(HikariExample.class, args);

        UserService userService = ctx.getBean(UserService.class);

        log.info("All users = {}", userService.findAll());

        User newUser = userService.createUser("HIK_NAME_1", "HIK_ADDRESS_1", "HIK_USERNAME_1", "HIK_PASSWORD_1", 0, 0);

        log.info("Created user = {}", newUser);
    }
}
