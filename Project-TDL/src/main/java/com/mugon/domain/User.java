package com.mugon.domain;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table
@Entity
@Setter

public class User implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String id;

    @Column
    private String password;

    @Column
    private String email;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<ToDoList> toDoLists = new ArrayList<>();

    public void add1(ToDoList toDoList){
        getToDoLists().add(toDoList);
    }


    /*List<ToDoList> toDoLists의 idx값과 db의 idx값이 달라 오류 발생
    public void delete1(Long idx) {
        int i = Math.toIntExact(idx);
        System.out.println(i-1);
        getToDoLists().remove(i - 1);
    }
    */

    @Builder
    public User(String id, String password, String email, List<ToDoList> toDoLists) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.toDoLists = toDoLists;
    }

}
