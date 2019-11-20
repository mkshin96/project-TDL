package com.mugon.service;

import com.mugon.domain.Reply;
import com.mugon.domain.ToDoList;
import com.mugon.dto.ReplyDto;
import com.mugon.repository.ReplyRepository;
import com.mugon.repository.ToDoListRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReplyService {

    private final ReplyRepository replyRepository;

    private final ToDoListRepository toDoListRepository;
    
    private final ModelMapper modelMapper;

    private ToDoList toDoList;

    @Autowired
    public ReplyService(ModelMapper modelMapper, ReplyRepository replyRepository, ToDoListRepository toDoListRepository) {
        this.modelMapper = modelMapper;
        this.replyRepository = replyRepository;
        this.toDoListRepository = toDoListRepository;
    }

    public List<Reply> findReplyByToDoList(ToDoList toDoList) {

        System.out.println("service :" +toDoList);

        return replyRepository.findAllByToDoListOrderByIdx(toDoList);

    }

    public void checkTodo(Long idx){
        this.toDoList = toDoListRepository.findToDoListByIdx(idx);
    }

    public ResponseEntity<?> postReply(ReplyDto replyDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Reply reply = modelMapper.map(replyDto, Reply.class);
        reply.addTodo(this.toDoList);
        reply.setCreatedDate(LocalDateTime.now());
        Reply savedReply = replyRepository.save(reply);

        return new ResponseEntity<>(savedReply, HttpStatus.CREATED);
    }

    public ResponseEntity<?> putReply(Long idx, String modified) {
        Optional<Reply> replyById = replyRepository.findById(idx);
        if (!replyById.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Reply reply = replyById.get();
        reply.update(modified);
        replyRepository.save(reply);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    public ResponseEntity<?> deleteReply(Long idx) {
        if (!replyRepository.findById(idx).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        replyRepository.deleteById(idx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
