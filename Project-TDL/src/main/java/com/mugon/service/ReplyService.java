package com.mugon.service;

import com.mugon.domain.Reply;
import com.mugon.domain.ToDoList;
import com.mugon.domain.User;
import com.mugon.repository.ReplyRepository;
import com.mugon.repository.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {


    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    ToDoListRepository toDoListRepository;

    public List<Reply> findReplyByToDoList(ToDoList toDoList) {

        System.out.println("service :" +toDoList);

        return replyRepository.findAllByToDoListOrderByIdx(toDoList);

    }

    public ToDoList checkTodo(Long idx){
        return toDoListRepository.findToDoListByIdx(idx);
    }
}
