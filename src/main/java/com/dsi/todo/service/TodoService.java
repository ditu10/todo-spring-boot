package com.dsi.todo.service;

import com.dsi.todo.model.Todo;
import com.dsi.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public void addTodo(String description) {
        Todo todo = new Todo(description, false, false);
        todoRepository.save(todo);
    }
}
