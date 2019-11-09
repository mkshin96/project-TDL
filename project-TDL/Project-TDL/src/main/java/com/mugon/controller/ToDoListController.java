package com.mugon.controller;

import com.mugon.domain.Reply;
import com.mugon.domain.ToDoList;
import com.mugon.domain.User;
import com.mugon.dto.ReplyDTO;
import com.mugon.dto.ToDoListDto;
import com.mugon.repository.ReplyRepository;
import com.mugon.repository.ToDoListRepository;
import com.mugon.repository.UserRepository;
import com.mugon.service.ReplyService;
import com.mugon.service.ToDoListService;
import com.mugon.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/toDoList")
@Slf4j
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

    @PostMapping("/checkIdx")
    public ResponseEntity<?> checkIdx(@RequestBody ToDoList toDoList, Model model){
        log.info("버튼 누를 때 :" + toDoList);

        this.toDoList = replyService.checkTodo(toDoList.getIdx());

        System.out.println(this.toDoList);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public String list(Model model) {
        this.user = userRepository.findById(UserService.currentUserId());
        model.addAttribute("tdlList", toDoListService.findTdlListByUser(this.user));
        return "/toDoList/list";
    }

    @PostMapping("/postReply")
    public ResponseEntity<?> postReply(@RequestBody Reply reply){
        this.toDoList.addReply(reply);

        replyRepository.save(Reply.builder().content(reply.getContent()).createdDate(LocalDateTime.now()).
                toDoList(this.toDoList).build());

        ReplyDTO replyDTO = new ReplyDTO();

        return new ResponseEntity<>(replyDTO.getReply(reply), HttpStatus.CREATED);
    }

    //tdlList add
    @PostMapping
    public ResponseEntity<?> saveTDL(@RequestBody ToDoListDto toDoListDto){
        return this.toDoListService.saveTdl(toDoListDto, this.user);
    }

    //tdlList update
    @PutMapping("/{idx}")
    public ResponseEntity<?> putTDL(@PathVariable("idx")Long idx, @RequestBody ToDoListDto toDoListDto){
        return this.toDoListService.updateTDL(idx, toDoListDto);

    }

    //tdlList 완료여부 update
    @PutMapping("/status/{idx}")
    public ResponseEntity<?> putTDL(@PathVariable("idx")Long idx){
        return toDoListService.updateTdlStatus(idx);
    }

    //tdlList 전체삭제
    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteTDL(){
        return toDoListService.deleteAllTdl();
    }

    //tdlList 개별삭제
    @DeleteMapping("/{idx}")
    public ResponseEntity<?> delete(@PathVariable("idx") Long idx){
        return toDoListService.deleteTdl(idx);
    }

}
