package com.mugon.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Getter @Setter @ToString @EqualsAndHashCode(of = "idx")
@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
public class Reply implements Serializable, Comparable<Reply> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String content;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime modifiedDate;

    @ManyToOne
    private ToDoList toDoList;

    @Override
    public int compareTo(Reply o) {
        if(this.idx > o.idx){
            return 1;
        }
        else if(this.idx < o.idx){
            return -1;
        }
        else{
            return 0;
        }
    }
    public void update(String modified) {
        this.content = modified;
        this.modifiedDate = LocalDateTime.now();
    }

    public void addTodo(ToDoList toDoList) {
        if (this.toDoList != null) {
            this.toDoList.getReplys().remove(this);
        }

        this.toDoList = toDoList;
        this.toDoList.getReplys().add(this);
    }
}
