package com.oocl.todoList.controller;

import com.oocl.todoList.model.Todo;
import com.oocl.todoList.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo/todoItem")
public class TodoController {
    public TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<Todo> getTodoList() {
        return todoService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo addTodoItem(@RequestBody Todo todoItem) {
        return todoService.addTodoItem(todoItem);
    }

    @PutMapping("/{id}")
    public Todo updateTodoItem(@PathVariable Integer id, @RequestBody Todo todoItem) {
        return todoService.updateTodoItem(id, todoItem);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodoItem(@PathVariable Integer id) {
        todoService.deleteTodoItem(id);
    }

    @GetMapping("/{id}")
    public Todo getTodoItemById(@PathVariable Integer id) {
        return todoService.getTodoItemById(id);
    }
}
