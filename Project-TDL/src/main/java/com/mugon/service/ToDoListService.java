package com.mugon.service;

import com.mugon.domain.ToDoList;
import com.mugon.repository.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoListService {

    @Autowired
    ToDoListRepository toDoListRepository;

    public List<ToDoList> findTdlList() {
        return toDoListRepository.findAllByOrderByIdx();

    }

    public ToDoList findTdlByIdx(Long idx)   {
        return toDoListRepository.findById(idx).orElse(new ToDoList());
    }

}
