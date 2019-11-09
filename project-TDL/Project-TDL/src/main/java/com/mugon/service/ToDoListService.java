package com.mugon.service;

import com.mugon.domain.Reply;
import com.mugon.domain.ToDoList;
import com.mugon.domain.User;
import com.mugon.dto.ToDoListDto;
import com.mugon.repository.ToDoListRepository;
import com.mugon.repository.UserRepository;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ToDoListService {

    @Autowired
    ToDoListRepository toDoListRepository;

    @Autowired
    UserRepository userRepository;

    private User user;

    @Autowired
    ModelMapper modelMapper;

    //User가 자신의 tdlList만 보게하기 위한 기능 구현
    public List<ToDoList> findTdlListByUser(User user) {
        return toDoListRepository.findAllByUserOrderByIdx(user);
    }

    public ResponseEntity saveTdl(ToDoListDto toDoListDto, User user) {
        ToDoList createdTodo = modelMapper.map(toDoListDto, ToDoList.class);
        user.add1(createdTodo);
        createdTodo.addUser(user);
        setTdlDefaultValue(createdTodo);
        toDoListRepository.save(createdTodo);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateTDL(Long idx, ToDoListDto toDoListDto) {
        Optional<ToDoList> optionalToDoList = toDoListRepository.findById(idx);
        if (!optionalToDoList.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ToDoList toDoList = optionalToDoList.get();
        modelMapper.map(toDoListDto, toDoList);

        toDoListRepository.save(toDoList);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    public ResponseEntity<?> updateTdlStatus(Long idx) {
        Optional<ToDoList> optionalToDoList = toDoListRepository.findById(idx);
        if (!optionalToDoList.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        ToDoList updatedTDL = optionalToDoList.get();
        updatedTDL.updateStatus(updatedTDL.getStatus());
        toDoListRepository.save(updatedTDL);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
    public ResponseEntity<?> deleteAllTdl() {
        toDoListRepository.deleteAll();
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    public ResponseEntity<?> deleteTdl(Long idx) {
        Optional<ToDoList> deleteTdl = toDoListRepository.findById(idx);
        if (!deleteTdl.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        toDoListRepository.delete(deleteTdl.get());
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    private void setTdlDefaultValue(ToDoList createdTodo) {
        createdTodo.setCreatedDate(LocalDateTime.now());
        createdTodo.setStatus(true);
    }
}
