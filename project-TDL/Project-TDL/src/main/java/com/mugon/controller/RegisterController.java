package com.mugon.controller;

import com.mugon.domain.User;
import com.mugon.dto.UserDTO;
import com.mugon.repository.UserRepository;
import com.mugon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

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

    @PostMapping("/checkEmail")
    public ResponseEntity<?> emailCheck(@RequestBody User user){
        System.out.println("EMAIL 중복검사 진입");

        User emailCheckUser = userRepository.findByEmail(user.getEmail());

        if(emailCheckUser == null) {
            return new ResponseEntity<>("{}", HttpStatus.CREATED);
        }

        else{
            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> postUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult){
        System.out.println("회원가입 진입");

        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getAllErrors());
            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
        }
        else {
            userService.saveUser(userDTO);
            return new ResponseEntity<>("{}", HttpStatus.CREATED);
        }

    }
}
