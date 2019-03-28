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
import java.util.List;

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

    private ToDoList toDoList;

    @GetMapping({"", "/"})
    public String board(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
//        model.addAttribute("userId", userRepository.findById(this.user.getId()));
        model.addAttribute("toDoList", toDoListService.findTdlByIdx(idx));
        return "/toDoList/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        //로그인되어있지 않을 경우 login화면으로 보냄
        if(this.user == null) {
            return "redirect:/toDoList/login";
        }

        //root계정은 모든 게시물을 볼 수 있음
        else if(this.user.getId().equals("root")){
            model.addAttribute("tdlList", toDoListService.findTdlList());
            return "/toDoList/list";
        }
        //로그인되어있을 경우 유저 자신의 화면으로 보냄
        else{
            model.addAttribute("tdlList", toDoListService.findTdlListByUser(this.user));
            return "/toDoList/list";
        }
    }

    @GetMapping("/login")
    public String login() {

        return "/toDoList/login";
    }

    @GetMapping("/register")
    public String register() {

        return "/toDoList/register";
    }

    //로그인 완료시 유저의 id를 받아와 현재 user에 저장
    @PostMapping("/check")
    public ResponseEntity<?> checkID(@RequestBody String id){

        this.user = userRepository.findById(id);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    //logout시 현재 user를 null로 만들고, login화면으로 보냄
    @GetMapping("/logout")
    public String logoutID(){
        this.user = null;

        return "redirect:/toDoList/login";
    }
//
    //tdlList add
    @PostMapping
    public ResponseEntity<?> saveTDL(@RequestBody ToDoList toDoList){

        toDoListRepository.save(ToDoList.builder().status(false).description(toDoList.getDescription()).createdDate(LocalDateTime.now())
                .user(this.user).build());

        this.user.add1(toDoList);
        System.out.println(user);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    //tdlList update
    @PutMapping("/{idx}")
    public ResponseEntity<?> putTDL(@PathVariable("idx")Long idx, @RequestBody String modified){

        ToDoList updateTDL = toDoListRepository.getOne(idx);

        updateTDL.update(modified);

        toDoListRepository.save(updateTDL);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    //tdlList 완료여부 update
    @PutMapping("/status/{idx}")
    public ResponseEntity<?> putTDL(@PathVariable("idx")Long idx){

        System.out.println("들어옴");
        ToDoList updateTDL = toDoListRepository.getOne(idx);

        updateTDL.update2(updateTDL.getStatus());

        toDoListRepository.save(updateTDL);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    //tdlList 전체삭제
    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteTDL(){

        toDoListRepository.deleteAll();

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    //tdlList 개별삭제
    @DeleteMapping("/{idx}")
    public ResponseEntity<?> delete(@PathVariable("idx") Long idx){

        toDoListRepository.deleteById(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);

    }

}
