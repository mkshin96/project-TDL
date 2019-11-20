package com.mugon.repository;

import com.mugon.domain.Reply;
import com.mugon.domain.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findAllByToDoListOrderByIdx(ToDoList toDoList);

}
