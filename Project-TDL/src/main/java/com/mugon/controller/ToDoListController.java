package com.mugon.controller;

import com.mugon.domain.ToDoList;
import com.mugon.domain.User;
import com.mugon.repository.ToDoListRepository;
import com.mugon.repository.UserRepository;
import com.mugon.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/toDoList")
public class ToDoListController {

    @Autowired
    ToDoListService toDoListService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ToDoListRepository toDoListRepository;

    private User user;

    @GetMapping({"", "/"})
    public String board(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        model.addAttribute("toDoList", toDoListService.findTdlByIdx(idx));
        return "/toDoList/list";
    }


    @GetMapping("/list")
    public String list(Model model) {
        this.user = userRepository.getOne(1L);

        model.addAttribute("tdlList", toDoListService.findTdlList());
        return "/toDoList/list";
    }

    @GetMapping("/login")
    public String login() {

        return "/toDoList/login";
    }

    @GetMapping("/register")
    public String register() {

        return "/toDoList/register";
    }

    @PostMapping
    public ResponseEntity<?> saveTDL(@RequestBody String content){
        int a = content.indexOf(":");
        String realContent = content.substring(a+2, content.length()-2);

//        User saveUser = userRepository.getOne(1L);

        toDoListRepository.save(ToDoList.builder().status(false).description(realContent).createdDate(LocalDateTime.now())
                .user(this.user).build());

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putTDL(@PathVariable("idx")Long idx, @RequestBody String modified){

        ToDoList updateTDL = toDoListRepository.getOne(idx);

        updateTDL.update(modified);

        toDoListRepository.save(updateTDL);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PutMapping("/status/{idx}")
    public ResponseEntity<?> putTDL(@PathVariable("idx")Long idx){


        ToDoList updateTDL = toDoListRepository.getOne(idx);

        System.out.println(updateTDL.getStatus());
        if(updateTDL.getStatus() == false) updateTDL.update2();

        else updateTDL.update3();

        toDoListRepository.save(updateTDL);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }


    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteTDL(){

        toDoListRepository.deleteAll();

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> delete(@PathVariable("idx") Long idx){

        toDoListRepository.deleteById(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);

    }

}
