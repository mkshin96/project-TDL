package com.mugon;

import com.mugon.domain.ToDoList;
import com.mugon.domain.User;
import com.mugon.repository.ToDoListRepository;
import com.mugon.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootApplication
public class TdlApplication {

    public static void main(String[] args) {
        SpringApplication.run(TdlApplication.class, args);
    }
//    @Bean
//    public CommandLineRunner runner(UserRepository userRepository, ToDoListRepository toDoListRepository) throws Exception    {
//        return (args) -> {
//            userRepository.save(User.builder().id("root").password("1234").email("test@gmail.com").build());
//        };
//    }
//
//    @Bean
//    public FilterRegistrationBean filterRegistrationBean() {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//        characterEncodingFilter.setEncoding("UTF-8");
//        registrationBean.setFilter(characterEncodingFilter);
//        return registrationBean;
//    }

}