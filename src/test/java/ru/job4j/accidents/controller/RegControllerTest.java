package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.UserService;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class RegControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    @WithMockUser
    public void shouldReturnRegPage() throws Exception {
        this.mockMvc.perform(get("/accident/reg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/accident/reg"));
    }

    @Test
    @WithMockUser
    public void whenTryToReg() throws Exception {
        this.mockMvc.perform(post("/accident/reg")
                        .param("username", "username")
                        .param("password", "password"))
                .andDo(print())
                .andExpect(status().isOk());
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(service).save(userCaptor.capture());
        assertThat(userCaptor.getValue().getUsername(), is("username"));
    }

}