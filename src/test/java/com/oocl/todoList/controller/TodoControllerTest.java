package com.oocl.todoList.controller;

import com.oocl.todoList.Repository.TodoRepository;
import com.oocl.todoList.model.Todo;
import org.assertj.core.api.AssertionsForClassTypes;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
public class TodoControllerTest {
    @Autowired
    private MockMvc client;
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private JacksonTester<List<Todo>> todoListJacksonTester;
    @Autowired
    private JacksonTester<Todo> todoJacksonTester;
    private Todo todo1;
    private Todo todo2;

    @BeforeEach
    void setUp() {
        todoRepository.deleteAll();
        todoRepository.flush();

        todo1 = new Todo("test text 1");
        todo2 = new Todo("test text 2");
        todoRepository.save(todo1);
        todoRepository.save(todo2);
    }

    @Test
    void should_return_all_todos_when_get_all_given_todos_exist() throws Exception {
        // Given
        final List<Todo> givenTodos = todoRepository.findAll();
        // When
        final MvcResult result = client.perform(MockMvcRequestBuilders.get("/todo/todoItem")).andReturn();
        // Then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getResponse().getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        final List<Todo> fetchedTodos = todoListJacksonTester.parseObject(result.getResponse().getContentAsString());
        assertThat(fetchedTodos).hasSameSizeAs(givenTodos);
        assertThat(fetchedTodos)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(givenTodos);
    }

    @Test
    void should_return_todo_when_add_todo_given_todo() throws Exception {
        // Given
        final Todo givenTodo = new Todo("test text 3");
        Integer expectedId = todoRepository.findAll().size() + 1;
        // When
        client.perform(MockMvcRequestBuilders.post("/todo/todoItem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(todoJacksonTester.write(givenTodo).getJson()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value(givenTodo.getText()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.done").value(false));

        List<Todo> todos = todoRepository.findAll();
        AssertionsForInterfaceTypes.assertThat(todos).hasSize(3);
    }

    @Test
    void should_return_updated_todo_when_update_todo_given_todo() throws Exception {
        // Given
        final Todo givenTodo = new Todo(1,"text1 was done", true);
        // When
        client.perform(MockMvcRequestBuilders.put("/todo/todoItem/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(todoJacksonTester.write(givenTodo).getJson()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value(givenTodo.getText()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.done").value(givenTodo.isDone()));
    }
}
