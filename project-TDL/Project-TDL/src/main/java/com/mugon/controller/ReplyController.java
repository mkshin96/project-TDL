package com.mugon.controller;

import com.mugon.domain.Reply;
import com.mugon.domain.ToDoList;
import com.mugon.domain.User;
import com.mugon.repository.ReplyRepository;
import com.mugon.repository.ToDoListRepository;
import com.mugon.repository.UserRepository;
import com.mugon.service.ReplyService;
import com.mugon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/reply")
public class ReplyController {

    private User user;
    private ToDoList toDoList;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ToDoListRepository toDoListRepository;

    @GetMapping("/reply")
    public String reply(Model model){

        this.user = userRepository.findById(UserService.currentUserId());
        this.toDoList = toDoListRepository.findByUser(this.user);

        model.addAttribute("reply", replyService.findReplyByToDoList(this.toDoList));

        return "/toDoList/list";
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putTDL(@PathVariable("idx")Long idx, @RequestBody String modified){

        Reply updateReply = replyRepository.getOne(idx);
        updateReply.update(modified);
        replyRepository.save(updateReply);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> delete(@PathVariable("idx") Long idx){
        replyRepository.deleteById(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);

    }

}
