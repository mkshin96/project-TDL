package com.mugon.controller;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.mugon.domain.User;
import com.mugon.dto.UserDTO;
import com.mugon.repository.UserRepository;
import com.mugon.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/register")
@Slf4j
public class RegisterController {

    private final UserRepository userRepository;

    private final UserService userService;

    @Autowired
    public RegisterController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping
    public String register() {
        return "/toDoList/register";
    }

    @PostMapping("/idCheck")
    public ResponseEntity<?> idCheck(@RequestBody User user){
        User registerUser = userRepository.findById(user.getId());

        if(registerUser == null) {
            return new ResponseEntity<>("{}", HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>("id가 중복입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/checkEmail")
    public ResponseEntity<?> emailCheck(@RequestBody User user){
        log.info("EMAIL 중복검사 진입");

        User emailCheckUser = userRepository.findByEmail(user.getEmail());

        if(emailCheckUser == null) {
            return new ResponseEntity<>("{}", HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>("email이 중복입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> postUser(@Valid @RequestBody UserDTO userDTO, Errors errors){
        log.info("회원가입 진입");
        if(errors.hasErrors()){
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        else {
            userService.saveUser(userDTO);
            return new ResponseEntity<>("{}", HttpStatus.CREATED);
        }

    }
}
