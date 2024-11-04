package com.wazzups.ftwitterbackend;

import com.wazzups.ftwitterbackend.models.ApplicationUser;
import com.wazzups.ftwitterbackend.models.Role;
import com.wazzups.ftwitterbackend.repositories.RoleRepository;
import com.wazzups.ftwitterbackend.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class  FtwitterBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FtwitterBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserService userService) {
        return args -> {
            roleRepository.save(new Role(1, "USER"));
        };
    }
}
