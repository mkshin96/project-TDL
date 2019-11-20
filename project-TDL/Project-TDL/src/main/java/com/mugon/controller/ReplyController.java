package com.mugon.controller;

import com.mugon.domain.ToDoList;
import com.mugon.domain.User;
import com.mugon.dto.ReplyDto;
import com.mugon.repository.ReplyRepository;
import com.mugon.repository.ToDoListRepository;
import com.mugon.repository.UserRepository;
import com.mugon.service.ReplyService;
import com.mugon.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/reply")
@Slf4j
public class ReplyController {

    private User user;
    private ToDoList toDoList;

    private final ReplyService replyService;

    private final ReplyRepository replyRepository;

    private final UserRepository userRepository;

    private final ToDoListRepository toDoListRepository;

    public ReplyController(ReplyService replyService, ReplyRepository replyRepository, UserRepository userRepository, ToDoListRepository toDoListRepository) {
        this.replyService = replyService;
        this.replyRepository = replyRepository;
        this.userRepository = userRepository;
        this.toDoListRepository = toDoListRepository;
    }

    @PostMapping("/checkIdx")
    public ResponseEntity<?> checkIdx(@RequestBody ToDoList toDoList){
        replyService.checkTodo(toDoList.getIdx());
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<?> postReply(@Valid @RequestBody ReplyDto replyDto, Errors errors){
        return replyService.postReply(replyDto, errors);
    }

    @GetMapping("/reply")
    public String reply(Model model){
        this.user = userRepository.findById(UserService.currentUserId());
        this.toDoList = toDoListRepository.findByUser(this.user);
        model.addAttribute("reply", replyService.findReplyByToDoList(this.toDoList));
        return "/toDoList/list";
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putReply(@PathVariable("idx")Long idx, @RequestBody String modified){
        return replyService.putReply(idx, modified);

    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteReply(@PathVariable("idx") Long idx){
        return replyService.deleteReply(idx);
    }

}
