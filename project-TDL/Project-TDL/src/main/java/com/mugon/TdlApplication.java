package com.mugon;

import com.mugon.domain.ToDoList;
import com.mugon.domain.User;
import com.mugon.repository.ToDoListRepository;
import com.mugon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootApplication
public class TdlApplication {

    public static void main(String[] args) {
        SpringApplication.run(TdlApplication.class, args);
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner runner(UserRepository userRepository, ToDoListRepository toDoListRepository) throws Exception    {

        return (args) -> {
            userRepository.save(User.builder().id("test").password(passwordEncoder.encode("test")).email("test@gmail.com").build());
        };
    }


}