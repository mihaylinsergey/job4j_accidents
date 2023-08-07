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
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentServiceSD;
import static org.assertj.core.api.Assertions.assertThat;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccidentServiceSD service;

    @Test
    @WithMockUser
    public void shouldReturnCreateAccidentPage() throws Exception {
        this.mockMvc.perform(get("/accident/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/accident/createAccident"));
    }

    @Test
    @WithMockUser
    public void shouldReturnUpdateAccidentPage() throws Exception {
        when(service.findById(any(Integer.class))).thenReturn(Optional.of(new Accident()));
        this.mockMvc.perform(get("/accident/updateAccident").param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/accident/updateAccident"));
    }

    @Test
    @WithMockUser
    public void shouldReturnDeletePage() throws Exception {
        when(service.delete(any(Integer.class))).thenReturn(true);
        this.mockMvc.perform(get("/accident/delete").param("id", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/accident/index"));
    }

    @Test
    @WithMockUser
    public void whenTryToSave() throws Exception {
         this.mockMvc.perform(post("/accident/saveAccident")
                 .param("id", "1")
                 .param("name", "name")
                 .param("text", "text")
                 .param("address", "address")
                 .param("type.id", "1")
                 .param("rIds", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Accident> accidentArgumentCaptor = ArgumentCaptor.forClass(Accident.class);
        ArgumentCaptor<HttpServletRequest> ruleArgumentCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);
        verify(service).create(accidentArgumentCaptor.capture(), ruleArgumentCaptor.capture());
        assertThat(accidentArgumentCaptor.getValue().getId()).isEqualTo(1);
        assertThat(accidentArgumentCaptor.getValue().getName(), is("name"));
        assertThat(accidentArgumentCaptor.getValue().getText(), is("text"));
        assertThat(accidentArgumentCaptor.getValue().getAddress(), is("address"));
        assertThat(accidentArgumentCaptor.getValue().getType(), is(new AccidentType(1, null)));
        assertThat(ruleArgumentCaptor.getValue().getParameterValues("rIds"), is(new String[]{"1"}));
    }

    @Test
    @WithMockUser
    public void whenTryToUpdate() throws Exception {
        this.mockMvc.perform(post("/accident/updateAndSaveAccident")
                        .param("id", "1")
                        .param("name", "name")
                        .param("text", "text")
                        .param("address", "address")
                        .param("type.id", "1")
                        .param("rIds", "1"))
                .andDo(print())
                .andExpect(status().isOk());
        ArgumentCaptor<Accident> accidentArgumentCaptor = ArgumentCaptor.forClass(Accident.class);
        ArgumentCaptor<HttpServletRequest> ruleArgumentCaptor = ArgumentCaptor.forClass(HttpServletRequest.class);
        verify(service).update(accidentArgumentCaptor.capture(), ruleArgumentCaptor.capture());
        assertThat(accidentArgumentCaptor.getValue().getId()).isEqualTo(1);
        assertThat(accidentArgumentCaptor.getValue().getName(), is("name"));
        assertThat(accidentArgumentCaptor.getValue().getText(), is("text"));
        assertThat(accidentArgumentCaptor.getValue().getAddress(), is("address"));
        assertThat(accidentArgumentCaptor.getValue().getType(), is(new AccidentType(1, null)));
        assertThat(ruleArgumentCaptor.getValue().getParameterValues("rIds"), is(new String[]{"1"}));
    }
}