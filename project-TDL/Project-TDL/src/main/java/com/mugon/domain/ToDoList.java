package com.mugon.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idx")
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

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "toDoList")
    @OrderBy("idx ASC")
    private List<Reply> replys = new ArrayList<>();

    public void updateStatus(boolean status) {
        this.status = !status;
        this.completedDate = status ? LocalDateTime.now() : null;
    }

    public void addUser(User user) {
        if (this.user != null) {
            this.user.getToDoLists().remove(this);
        }
        this.user = user;
        this.user.getToDoLists().add(this);
    }
}