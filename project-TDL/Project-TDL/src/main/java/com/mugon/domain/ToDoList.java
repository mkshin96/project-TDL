package com.mugon.domain;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table
@Setter

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

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "toDoList")
    private List<Reply> replys = new ArrayList<>();

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

    public void update2(boolean status) {
        System.out.println(status);
        this.status = !status;
        this.completedDate = status ? null : LocalDateTime.now();
    }

    public void add(Reply reply) {
        System.out.println("add 진입!");
        getReplys().add(reply);
        System.out.println("List<Reply> : " + getReplys());

//        System.out.println(getReplys().get(0).getIdx());
//        System.out.println(getReplys().get(1));
//        System.out.println(getReplys().get(2));
//        System.out.println(getReplys().get(3));

    }
}