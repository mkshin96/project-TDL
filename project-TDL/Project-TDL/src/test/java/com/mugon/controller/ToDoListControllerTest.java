package com.mugon.controller;

import com.mugon.commons.TestDescription;
import com.mugon.domain.ToDoList;
import com.mugon.dto.ToDoListDto;
import org.junit.Test;
import org.springframework.http.MediaType;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ToDoListControllerTest extends CreateUserBeforeTest {

    @Test
    @TestDescription("todolist 성공적으로 저장")
    public void createToDo() throws Exception {
        //GIVEN
        ToDoListDto toDoListDto = new ToDoListDto();
        toDoListDto.setDescription("해야 할 일1");

        //WHEN
        mockMvc.perform(post(todolistUrl)
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoListDto)))
                .andDo(print())
                .andExpect(status().isCreated());

        //THEN
        Optional<ToDoList> todo = toDoListRepository.findById(1L);
        assertThat(toDoListRepository.findAll().size()).isEqualTo(1);
        assertThat(todo.isPresent()).isTrue();
        assertThat(todo.get().getDescription()).isEqualTo("해야 할 일1");
        assertThat(todo.get().getUser().getId()).isEqualTo(appProperties.getUsername());
    }

    @Test
    @TestDescription("todolist 성공적으로 수정")
    public void updateTodo() throws Exception{
        //GIVEN
        ToDoListDto toDoListDto = new ToDoListDto();
        toDoListDto.setDescription("해야 할 일1");

        //WHEN & THEN
        mockMvc.perform(post(todolistUrl)
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoListDto)))
                .andDo(print())
                .andExpect(status().isCreated());

        assertThat(toDoListRepository.findById(1L)).isNotNull();

        //GIVEM
        toDoListDto.setDescription("수정한 해야 할 일1");

        //WHEN
        mockMvc.perform(put(todolistUrl + "/1")
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoListDto)))
                .andDo(print())
                .andExpect(status().isOk());

        //THEN
        Optional<ToDoList> updatedTodo = toDoListRepository.findById(1L);
        assertThat(updatedTodo.get().getDescription()).isEqualTo("수정한 해야 할 일1");
    }

    @Test
    @TestDescription("todolist status를 성공적으로 수정")
    public void updateTodo_status() throws Exception{
        //GIVEN
        ToDoListDto toDoListDto = new ToDoListDto();
        toDoListDto.setDescription("해야 할 일1");

        //WHEN & THEN
        mockMvc.perform(post(todolistUrl)
                .with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoListDto)))
                .andDo(print())
                .andExpect(status().isCreated());

        //기본값 status = true
        ToDoList todo = toDoListRepository.findById(1L).get();

        assertThat(todo.getStatus()).isTrue();

        //WHEN
        mockMvc.perform(put(todolistUrl + "/status/1")
                .with(user(userDetails)))
                .andDo(print())
                .andExpect(status().isOk());

        ToDoList todo2 = toDoListRepository.findById(1L).get(); //jpa 캐시 때문에 todo2 객체를 선언

        //THEN
        assertThat(todo2.getStatus()).isFalse();

        //WHEN
        mockMvc.perform(put(todolistUrl + "/status/1")
                .with(user(userDetails)))
                .andDo(print())
                .andExpect(status().isOk());

        ToDoList todo3 = toDoListRepository.findById(1L).get(); //jpa 캐시 때문에 todo3 객체를 선언

        //THEN
        assertThat(todo3.getStatus()).isTrue();
    }

    @Test
    @TestDescription("todolist를 성공적으로 전체 삭제")
    public void deleteAllTodo() throws Exception{
        IntStream.rangeClosed(1, 30).forEach(e -> {
            //GIVEN
            ToDoListDto toDoListDto = new ToDoListDto();
            toDoListDto.setDescription("해야 할 일" + e);
            //WHEN
            try {
                mockMvc.perform(post(todolistUrl)
                        .with(user(userDetails))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toDoListDto)))
                        .andDo(print())
                        .andExpect(status().isCreated());
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        });

        //THEN
        assertThat(toDoListRepository.findAll().size()).isEqualTo(30);

        //WHEN
        mockMvc.perform(delete(todolistUrl + "/deleteAll")
                .with(user(userDetails)))
                .andExpect(status().isOk());

        //THEN
        assertThat(toDoListRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @TestDescription("todolist를 성공적으로 삭제")
    public void deleteTodo() throws Exception{
        IntStream.rangeClosed(1, 30).forEach(e -> {
            //GIVEN
            ToDoListDto toDoListDto = new ToDoListDto();
            toDoListDto.setDescription("해야 할 일" + e);
            //WHEN
            try {
                mockMvc.perform(post(todolistUrl)
                        .with(user(userDetails))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toDoListDto)))
                        .andDo(print())
                        .andExpect(status().isCreated());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        //THEN
        assertThat(toDoListRepository.findAll().size()).isEqualTo(30);

        //WHEN
        mockMvc.perform(delete(todolistUrl + "/1")
                .with(user(userDetails)))
                .andExpect(status().isOk());

        //THEN
        assertThat(toDoListRepository.findAll().size()).isEqualTo(29);
        assertThat(toDoListRepository.findById(1L).isPresent()).isFalse();
    }
}