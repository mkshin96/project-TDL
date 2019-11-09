package com.mugon.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Getter @Setter @EqualsAndHashCode(of = "idx")
@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
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

    private static long count = 1;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "toDoList")
    @OrderBy("idx ASC")
    private List<Reply> replys = new ArrayList<>();

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
    }

}