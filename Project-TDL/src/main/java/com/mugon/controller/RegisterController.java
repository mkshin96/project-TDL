package com.mugon.controller;

import com.mugon.domain.User;
import com.mugon.repository.UserRepository;
import com.mugon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping
    public String register() {
        return "/toDoList/register";
    }

    @PostMapping("/idCheck")
    public ResponseEntity<?> idCheck(@RequestBody User user){
        System.out.println("ID 중복검사 진입");

        User registerUser = userRepository.findById(user.getId());

        if(registerUser == null) {
            return new ResponseEntity<>("{}", HttpStatus.CREATED);
        }

        else{
            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping
    public ResponseEntity<?> postUser(@RequestBody User user){
        System.out.println("회원가입 진입");

//        User registerUser = userRepository.findById(user.getId());

//        if(registerUser == null) {
            userService.saveUser(user);
            return new ResponseEntity<>("{}", HttpStatus.CREATED);
//        }

//        else{
//            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
//        }
    }
}
