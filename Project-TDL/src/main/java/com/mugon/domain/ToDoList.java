package com.mugon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table
public class ToDoList implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String description;

    @Column
    private Boolean status;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime completedDate;

    @OneToOne
    private User user;

    @Builder
    public ToDoList(String description, Boolean status, LocalDateTime createdDate, LocalDateTime completedDate, User user) {
        this.description = description;
        this.status = status;
        this.createdDate = createdDate;
        this.completedDate = completedDate;
        this.user = user;
    }

    public void update(String modified) {
        this.description = modified;
    }

    public void update2() {
        this.status = true;
        this.completedDate = LocalDateTime.now();
    }

    public void update3() {
        this.status = false;
        this.completedDate = null;
    }
}