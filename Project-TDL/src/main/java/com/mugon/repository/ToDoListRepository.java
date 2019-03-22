package com.mugon.repository;

import com.mugon.domain.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoListRepository extends JpaRepository<ToDoList, Long> {
    List<ToDoList> findAllByOrderByIdx();
}
