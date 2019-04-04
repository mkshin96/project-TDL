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
public class UserController {

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess() {
        System.out.println("로그인 진입");
        return "redirect:/toDoList/list";
    }

//    @PostMapping
//    public String loginSuccess(){
//        return "redirect:/toDoList/list";
//    }

//    //회원가입 시 중복id 방지
//    @PostMapping
//    public ResponseEntity<?> postUser(@RequestBody User user){
//
//        User overLapId = userRepository.findById(user.getId());
//
//        System.out.println("회원가입 user : " +  user);
//
//        if(overLapId == null) {
//            userRepository.save(user);
//            return new ResponseEntity<>("{}", HttpStatus.CREATED);
//        }
//
//        else return null;
//
//
//    }

    //login기능 구현
//    @PostMapping("/login")
//    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> user){
//        String id = user.get("id");
//        String password = user.get("password");
//
//        User collectUser = userRepository.findById(id);
//
//        //id가 db에 없는 경우
//        if(collectUser == null){
//            return null;
//        }
//
//        else {
//            //id와 password가 db에 있는 값과 일치하는 경우
//            if(password.equals(collectUser.getPassword())){
//                return new ResponseEntity<>("{}", HttpStatus.OK);
//            }
//
//            //id가 db에 있지만 password가 틀린 경우
//            else{
//                return null;
//            }
//        }

//    }


}
