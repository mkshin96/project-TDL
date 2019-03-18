package com.mugon.controller;

import com.mugon.domain.ToDoList;
import com.mugon.repository.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delete")
public class DeleteController {

    @Autowired
    ToDoListRepository toDoListRepository;

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> delete(@PathVariable("idx") Long idx){

        toDoListRepository.deleteById(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);

    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putTDL(@PathVariable("idx")Long idx){

        ToDoList updateTDL = toDoListRepository.getOne(idx);

        updateTDL.update2();

        toDoListRepository.save(updateTDL);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }


}
