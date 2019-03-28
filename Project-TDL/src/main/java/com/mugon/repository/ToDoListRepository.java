package com.mugon.repository;

import com.mugon.domain.ToDoList;
import com.mugon.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoListRepository extends JpaRepository<ToDoList, Long> {
    List<ToDoList> findAllByOrderByIdx();
    List<ToDoList> findAllByUserOrderByIdx(User user);

    ToDoList findByUser(User user);

}
