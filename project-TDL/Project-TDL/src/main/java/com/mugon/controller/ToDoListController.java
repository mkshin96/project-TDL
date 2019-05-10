package com.mugon.controller;

import com.mugon.domain.Reply;
import com.mugon.domain.ToDoList;
import com.mugon.domain.User;
import com.mugon.dto.ReplyDTO;
import com.mugon.repository.ReplyRepository;
import com.mugon.repository.ToDoListRepository;
import com.mugon.repository.UserRepository;
import com.mugon.service.ReplyService;
import com.mugon.service.ToDoListService;
import com.mugon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    ReplyController replyController;

    @Autowired
    ReplyService replyService;

    private User user;

    private ToDoList toDoList;

    @GetMapping({"/{idx}"})
    public String board(@RequestParam(defaultValue = "0", value = "idx")Long idx, Model model) {
//        model.addAttribute("userId", userRepository.findById(this.user.getId()));


        model.addAttribute("tdlList", toDoListRepository.findByIdx(idx));

        return "/toDoList/form";
    }

    @PostMapping("/checkIdx")
    public ResponseEntity<?> checkIdx(@RequestBody ToDoList toDoList, Model model){
        System.out.println("버튼 누를 때 :" + toDoList);

        this.toDoList = replyService.checkTodo(toDoList.getIdx());

        System.out.println(this.toDoList);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public String list(Model model) {
        this.user = userRepository.findById(UserService.currentUserId());

        System.out.println("list : " + this.toDoList);
        model.addAttribute("tdlList", toDoListService.findTdlListByUser(this.user));
//        model.addAttribute("reply", replyService.findReplyByToDoList(this.toDoList));
        return "/toDoList/list";
    }

    @PostMapping("/postReply")
    public ResponseEntity<?> postReply(@RequestBody Reply reply){
        System.out.println("댓글 등록 컨트롤러 진입");
        replyRepository.save(Reply.builder().content(reply.getContent()).createdDate(LocalDateTime.now()).
                toDoList(this.toDoList).build());

        this.toDoList.add(reply);

        System.out.println("댓글 등록 리스트: "+ reply);
        ReplyDTO replyDTO = new ReplyDTO();

        return new ResponseEntity<>(replyDTO.getReply(reply), HttpStatus.CREATED);
    }

    //tdlList add
    @PostMapping
    public ResponseEntity<?> saveTDL(@RequestBody ToDoList toDoList){

        this.user.add1(toDoList);

        toDoListRepository.save(ToDoList.builder().status(false).description(toDoList.getDescription()).createdDate(LocalDateTime.now())
                .user(this.user).build());

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
