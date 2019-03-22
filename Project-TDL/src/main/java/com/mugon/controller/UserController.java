package com.mugon.controller;

import com.mugon.domain.User;
import com.mugon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    private User user;

    @PostMapping
    public ResponseEntity<?> postUser(@RequestBody User user){

        userRepository.save(user);

        return new ResponseEntity<>("{}", HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> user){
        String id = user.get("id");
        String password = user.get("password");

        User collectUser = userRepository.findById(id);

        if(collectUser == null){
            return null;
        }

        else {
            if(password.equals(collectUser.getPassword())){
                return new ResponseEntity<>("{}", HttpStatus.OK);
            }

            else{
                return null;
            }

        }


//        if(id.equals(collectUser.getId())){
//            if(password.equals(collectUser.getPassword())){
//                return new ResponseEntity<>("{}", HttpStatus.OK);
//            }
//
//        }

    }
}
