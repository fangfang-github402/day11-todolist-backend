package com.oocl.todoList.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TodoItemNotFoundException extends RuntimeException {

    public static final String TODO_ITEM_NOT_FOUND = "Todo item not found";

    public TodoItemNotFoundException() {
        super(TODO_ITEM_NOT_FOUND);
    }
}