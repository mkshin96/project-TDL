package com.mugon.controller;

import com.mugon.commons.TestDescription;
import com.mugon.domain.User;
import com.mugon.dto.UserDTO;
import com.mugon.repository.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseControllerTests{

    @Autowired
    UserRepository userRepository;

    @Test
    @TestDescription("정상적으로 유저를 생성")
    public void createUser() throws Exception{
        //GIVEN
        UserDTO userDTO = new UserDTO();
        userDTO.setId(appProperties.getUsername());
        userDTO.setPassword(appProperties.getPassword());
        userDTO.setEmail(appProperties.getEmail());

        //WHEN
        this.mockMvc.perform(post("/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userDTO)))
                .andDo(print())
                .andExpect(status().isCreated());

        //THEN
        User createdUser = userRepository.findById("testId");
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isEqualTo("testId");
    }

    @Test
    @TestDescription("프로터피 중 하나라도 빈 값으로 들어올 경우")
    public void createUser_Bad_Request_Empty_Input() throws Exception{
        //GIVEN --> ID가 빈 값
        UserDTO userDTO1 = new UserDTO();
        userDTO1.setPassword(appProperties.getPassword());
        userDTO1.setEmail(appProperties.getEmail());

        //WHEN & THEN
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO1)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        //GIVEN --> PASSWORD가 빈 값
        UserDTO userDTO2 = new UserDTO();
        userDTO2.setId(appProperties.getUsername());
        userDTO2.setEmail(appProperties.getEmail());

        //WHEN & THEN
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO2)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        //GIVEN --> EMAIL이 빈 값
        UserDTO userDTO3 = new UserDTO();
        userDTO3.setId(appProperties.getUsername());
        userDTO3.setPassword(appProperties.getPassword());

        //WHEN & THEN
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO3)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @TestDescription("프로퍼티 중 하나라도 잘못된 값으로 입력될 경우")
    public void createUser_Bad_Request_Wrong_Input() throws Exception{
        //GIVEN --> ID 길이가 최소 길이를 만족하지 못함
        UserDTO userDTO1 = new UserDTO();
        userDTO1.setId("tes");
        userDTO1.setPassword(appProperties.getPassword());
        userDTO1.setEmail(appProperties.getEmail());

        //WHEN & THEN
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO1)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        //GIVEN --> PASSWORD 길이가 최소 길이를 만족하지 못함
        UserDTO userDTO2 = new UserDTO();
        userDTO2.setId(appProperties.getUsername());
        userDTO2.setPassword("test");
        userDTO2.setEmail(appProperties.getEmail());

        //WHEN & THEN
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO2)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        //GIVEN --> EMAIL양식이 올바르지 못함
        UserDTO userDTO3 = new UserDTO();
        userDTO3.setId(appProperties.getUsername());
        userDTO3.setPassword(appProperties.getPassword());
        userDTO3.setEmail("testemail@gmail");

        //WHEN & THEN
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO3)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}