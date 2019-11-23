package com.mugon.controller;

import com.mugon.commons.TestDescription;
import com.mugon.domain.Reply;
import com.mugon.domain.ToDoList;
import com.mugon.dto.ReplyDto;
import com.mugon.dto.ToDoListDto;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReplyControllerTest extends CreateUserBeforeTest {

    private final String replyUrl = "/reply";

    @Test
    @TestDescription("reply 정상 생성 테스트")
    public void postReply() throws Exception {
        createTodoAndSetCurrentTodo();

        //GIVEN
        IntStream.rangeClosed(1, 30).forEach(replyIndex -> {
            ReplyDto replyDto = new ReplyDto();
            replyDto.setContent("테스트 댓글" + replyIndex);
            try {
                this.mockMvc.perform(post(replyUrl)
                        .with(user(userDetails))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(replyDto)))
                        .andDo(print())
                        .andExpect(status().isCreated());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        ToDoList randomTodo = replyRepository.findAll().get(9).getToDoList(); //임의의 댓글의 todolist
        assertThat(randomTodo.getIdx()).isEqualTo(1L);
        assertThat(randomTodo.getReplys().size()).isEqualTo(30);
    }

    @Test
    @TestDescription("댓글 생성 시 댓글의 내용이 없을 때")
    public void postReply_bad_request_empty_input() throws Exception {
        createTodoAndSetCurrentTodo();

        //reply 생성
        ReplyDto replyDto = new ReplyDto();
        replyDto.setContent("");

        //응답 확인
        mockMvc.perform(post(replyUrl)
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(replyDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[*].field").exists())
                .andExpect(jsonPath("$[*].defaultMessage").value("필수 항목입니다."));

        assertThat(replyRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @TestDescription("댓글 정상 수정")
    public void updateReply() throws Exception {
        ReplyDto replyDto = createReply();

        Reply reply = replyRepository.findById(1L).orElseGet(() -> fail("Test Failed(Null Reply)"));
        assertThat(reply.getIdx()).isEqualTo(1L);

        replyDto.setContent("수정된 댓글");
        //댓글 수정
        this.mockMvc.perform(put(replyUrl+"/{idx}", reply.getIdx())
                        .with(user(userDetails))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(replyDto)))
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(replyRepository.findById(1L).get().getContent()).isEqualTo("수정된 댓글");
    }

    @Test
    @TestDescription("댓글 수정 시 빈 값이 들어왔을 때")
    public void updateReply_bad_request_empty_input() throws Exception {
        ReplyDto replyDto = createReply();

        Reply reply = replyRepository.findById(1L).orElseGet(() -> fail("Test Failed(Null Reply)"));
        assertThat(reply.getIdx()).isEqualTo(1L);

        replyDto.setContent("");
        //댓글 수정
        this.mockMvc.perform(put(replyUrl+"/{idx}", reply.getIdx())
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(replyDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        assertThat(replyRepository.findById(1L).get().getContent()).isEqualTo("해야 할 일");
    }

    @Test
    @TestDescription("댓글 삭제")
    public void deleteReply() throws Exception {
        createReply();

        Reply reply = replyRepository.findById(1L).orElseGet(() -> fail("Test Failed(Null Reply)"));
        assertThat(reply.getIdx()).isEqualTo(1L);

        mockMvc.perform(delete(replyUrl + "/{idx}", reply.getIdx())
                .with(user(userDetails)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private void createTodoAndSetCurrentTodo() throws Exception {
        ToDoListDto toDoListDto = new ToDoListDto();
        toDoListDto.setDescription("해야 할 일");
        //tdoo 생성
        this.mockMvc.perform(post(todolistUrl)
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoListDto)))
                .andDo(print())
                .andExpect(status().isCreated());

        assertThat(toDoListRepository.findAll().size()).isEqualTo(1);
        assertThat(toDoListRepository.findById(1L).get().getDescription()).isEqualTo("해야 할 일");

        ToDoList toDoList = toDoListRepository.findById(1L).orElseGet(() -> fail("Test Fail(ToDo isn't saved)"));

        this.mockMvc.perform(post(replyUrl + "/checkIdx")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoList)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    private ReplyDto createReply() throws Exception {
        createTodoAndSetCurrentTodo();

        ReplyDto replyDto = new ReplyDto();
        replyDto.setContent("해야 할 일");

        this.mockMvc.perform(post(replyUrl)
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(replyDto)))
                .andDo(print())
                .andExpect(status().isCreated());
        return replyDto;
    }
}