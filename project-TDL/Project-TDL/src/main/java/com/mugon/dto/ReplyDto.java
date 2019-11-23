package com.mugon.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ReplyDto {

    @NotEmpty(message = "필수 항목입니다.")
    private String content;

}
