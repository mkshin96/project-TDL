package com.mugon.service;

import com.mugon.domain.Reply;
import com.mugon.domain.ToDoList;
import com.mugon.dto.ReplyDto;
import com.mugon.repository.ReplyRepository;
import com.mugon.repository.ToDoListRepository;
import com.mugon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    private final ToDoListRepository toDoListRepository;
    
    private final ModelMapper modelMapper;

    private ToDoList toDoList;

    public ResponseEntity<?> checkTodo(Long idx){
        this.toDoList = toDoListRepository.findToDoListByIdx(idx);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    public ResponseEntity<?> postReply(ReplyDto replyDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Reply reply = modelMapper.map(replyDto, Reply.class);
        reply.addTodo(this.toDoList);
        reply.setCreatedDate(LocalDateTime.now());
        Reply savedReply = replyRepository.save(reply);

        return new ResponseEntity<>(savedReply, HttpStatus.CREATED);
    }

    public ResponseEntity<?> putReply(Long idx, String modified, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

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
