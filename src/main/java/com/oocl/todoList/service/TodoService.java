package com.oocl.todoList.service;

import com.oocl.todoList.Repository.TodoRepository;
import com.oocl.todoList.exception.TodoItemNotFoundException;
import com.oocl.todoList.model.Todo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    public Todo addTodoItem(Todo todoItem) {
        return todoRepository.save(todoItem);
    }

    public Todo updateTodoItem(Integer id, Todo todoItem) {
        todoRepository.findById(id).orElseThrow(TodoItemNotFoundException::new);
        return todoRepository.save(todoItem);
    }

    public void deleteTodoItem(Integer id) {
        Todo todo = todoRepository.findById(id).orElseThrow(TodoItemNotFoundException::new);
        todoRepository.delete(todo);
    }

    public Todo getTodoItemById(Integer id) {
        return todoRepository.findById(id).orElseThrow(TodoItemNotFoundException::new);
    }
}
