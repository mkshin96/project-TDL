package com.mugon.domain;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@Table
@Entity
@Setter
@ToString
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

    @Builder
    public User(String id, String password, String email) {
        this.id = id;
        this.password = password;
        this.email = email;
    }
}
