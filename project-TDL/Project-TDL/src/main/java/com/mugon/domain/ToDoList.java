package com.mugon.domain;

import com.mugon.commons.Description;
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
    @Description("true = 미완료, false = 완료")
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

    public void updateStatus(boolean status) {
        System.out.println("=============");
        System.out.println("this.status : " + this.status);
        System.out.println("stats : " + !status);
        System.out.println("=============");
        this.status = !status;
        this.completedDate = status ? null : LocalDateTime.now();
    }

    public void addReply(Reply reply) {
        getReplys().add(reply);
        System.out.println("List<Reply> : " + getReplys());
    }

    public void addUser(User user) {
        if (this.getUser() != null) {
            this.getUser().getToDoLists().remove(this);
        }
        this.setUser(user);
    }
}