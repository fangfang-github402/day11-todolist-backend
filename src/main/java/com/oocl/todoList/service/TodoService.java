package com.oocl.todoList.service;

import com.oocl.todoList.Repository.TodoRepository;
import com.oocl.todoList.exception.TodoItemNotFoundException;
import org.springframework.stereotype.Service;
import com.oocl.todoList.model.Todo;
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
        Todo todo = todoRepository.findById(id).orElse(null);
        if (todo != null) {
            return todoRepository.save(todoItem);
        } else {
            throw new TodoItemNotFoundException();
        }
    }

    public void deleteTodoItem(Integer id) {
        Todo todo = todoRepository.findById(id).orElse(null);
        if (todo != null) {
            todoRepository.delete(todo);
        } else {
            throw new TodoItemNotFoundException();
        }
    }

    public Todo getTodoItemById(Integer id) {
        return todoRepository.findById(id).orElse(null);
    }
}
