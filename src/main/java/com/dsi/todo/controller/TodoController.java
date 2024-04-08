package com.dsi.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TodoController {
    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("add-todo")
    public String addTodoPage() {
        return "todo/add";
    }
    @GetMapping("todos")
    public String listTodos() {
        return "todo/list";
    }
}
