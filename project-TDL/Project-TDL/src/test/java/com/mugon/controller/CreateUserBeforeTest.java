package com.mugon.controller;

import com.mugon.dto.UserDTO;
import com.mugon.repository.ReplyRepository;
import com.mugon.repository.ToDoListRepository;
import com.mugon.repository.UserRepository;
import com.mugon.service.UserService;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CreateUserBeforeTest extends BaseControllerTest{

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ToDoListRepository toDoListRepository;

    @Autowired
    ReplyRepository replyRepository;

    UserDetails userDetails;

    final String todolistUrl = "/toDoList";

    @Before
    public void setUp() throws Exception {
        replyRepository.deleteAll();
        toDoListRepository.deleteAll();
        userRepository.deleteAll();
        //GIVEN
        UserDTO userDTO = new UserDTO();
        userDTO.setId(appProperties.getUsername());
        userDTO.setPassword(appProperties.getPassword());
        userDTO.setEmail(appProperties.getEmail());

        userService.saveUser(userDTO);

        assertThat(userRepository.findById(appProperties.getUsername())).isNotNull();

        this.userDetails = userService.loadUserByUsername(appProperties.getUsername());

        this.mockMvc.perform(get(todolistUrl + "/list")
                .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(authenticated());
    }
}
