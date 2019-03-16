package com.mugon.repository;

import com.mugon.domain.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoListRepository extends JpaRepository<ToDoList, Long> {
}
