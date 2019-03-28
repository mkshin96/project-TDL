package com.mugon.service;

import com.mugon.domain.ToDoList;
import com.mugon.domain.User;
import com.mugon.repository.ToDoListRepository;
import com.mugon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoListService {

    @Autowired
    ToDoListRepository toDoListRepository;

    @Autowired
    UserRepository userRepository;

    public List<ToDoList> findTdlList() {
        return toDoListRepository.findAllByOrderByIdx();
    }


    //User가 자신의 tdlList만 보게하기 위한 기능 구현
    public List<ToDoList> findTdlListByUser(User user) {
        return toDoListRepository.findAllByUserOrderByIdx(user);
    }

    public ToDoList findTdlByIdx(Long idx)   {
        return toDoListRepository.findById(idx).orElse(new ToDoList());
    }

}
