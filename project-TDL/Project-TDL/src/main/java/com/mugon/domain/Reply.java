package com.mugon.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table
@ToString
@Setter
public class Reply implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String content;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime modifiedDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private ToDoList toDoList;

    @Builder
    public Reply(String content, LocalDateTime createdDate, LocalDateTime modifiedDate, ToDoList toDoList) {
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.toDoList = toDoList;
    }

    public void update(String modified) {
        this.content = modified;
        this.modifiedDate = LocalDateTime.now();
    }
}
