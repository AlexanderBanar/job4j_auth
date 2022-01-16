package ru.job4j_auth.controller;

import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.job4j_auth.Job4jAuthApplication;
import ru.job4j_auth.domain.Person;
import ru.job4j_auth.service.PersonService;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest(classes = Job4jAuthApplication.class)
@AutoConfigureMockMvc
public class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService service;

    @Test
    @WithMockUser
    public void shouldReturnAllPersons() throws Exception {
        List<Person> list = List.of(
                Person.of(1, "Alex", "123"),
                Person.of(2, "Bill", "000"),
                Person.of(3, "Petr", "abc"));
        Mockito.when(service.getAllPersons()).thenReturn(list);
        mockMvc.perform(get("/person/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].password", is("123")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].login", is("ivan")));
    }

    @Test
    @WithMockUser
    public void shouldReturnPersonById() throws Exception {
        Person person = Person.of(1, "Alex", "123");
        Mockito.when(service.findPersonById(1)).thenReturn(Optional.of(person));
        mockMvc.perform(get("/person/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.login", is("parsentev")));
    }
}