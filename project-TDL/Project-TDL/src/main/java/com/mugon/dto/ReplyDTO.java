package com.mugon.dto;

import com.mugon.domain.Reply;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReplyDTO {

    private String content;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private static long count = 0;

    public Reply getReply(Reply reply) {
        count++;
        reply.setIdx(count);
        reply.setContent(reply.getContent());
        reply.setCreatedDate(LocalDateTime.now());

        return reply;
    }
}
