package com.kiseok.service;

import com.kiseok.domain.ToDoList;
import com.kiseok.repository.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoListService {

    @Autowired
    ToDoListRepository toDoListRepository;

    public List<ToDoList> findTdlList() {
        return toDoListRepository.findAll();

    }

    public ToDoList findTdlByIdx(Long idx)   {
        return toDoListRepository.findById(idx).orElse(new ToDoList());
    }

}
