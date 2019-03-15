package com.kiseok.controller;

import com.kiseok.domain.ToDoList;
import com.kiseok.repository.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/save")
public class SaveController {

    @Autowired
    ToDoListRepository toDoListRepository;

    @PostMapping
    public ResponseEntity<?> saveTDL(@RequestBody String content){

        int a = content.indexOf(":");
        String realContent = content.substring(a+2, content.length()-2);

        toDoListRepository.save(ToDoList.builder().status(false).description(realContent).createdDate(LocalDateTime.now())
        .build());

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTDL(){

        toDoListRepository.deleteAll();

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }


}
