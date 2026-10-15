package com.app.cinemabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CinemaBackendApplication {

    void main(String[] args) {
        SpringApplication.run(CinemaBackendApplication.class, args);
    }

}
