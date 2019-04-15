package com.mugon.dto;

import com.mugon.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class UserDTO implements Serializable {

    @NotBlank(message = "아이디를 입력하세요.")
    private String id;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "이메일 양식을 지키세요.")
    private String email;


    public User saveUser(){
        User user = new User();

        user.setId(this.id);
        user.setPassword(this.password);
        user.setEmail(this.email);

        return user;
    }

}
