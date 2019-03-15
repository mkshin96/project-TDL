package com.kiseok.controller;

import com.kiseok.repository.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/delete")
public class DeleteController {

    @Autowired
    ToDoListRepository toDoListRepository;

    @DeleteMapping("{idx}")
    public ResponseEntity<?> delete(@PathVariable("idx") Long idx){

        System.out.println("들어옴");

        toDoListRepository.deleteById(idx);


        return new ResponseEntity<>("{}", HttpStatus.OK);

    }


}
