package com.mugon.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ReplyDto {

    @Size(min = 1)
    private String content;

}
