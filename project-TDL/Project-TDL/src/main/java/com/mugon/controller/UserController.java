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

    @PostMapping("/loginSuccess")
    public String loginSuccess() {
        System.out.println("로그인 진입");
        return "redirect:/toDoList/list";
    }

}