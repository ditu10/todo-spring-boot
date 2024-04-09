package com.dsi.todo.controller;

import com.dsi.todo.model.Todo;
import com.dsi.todo.repository.TodoRepository;
import com.dsi.todo.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TodoController {
    private final TodoRepository todoRepository;
    private final TodoService todoService;

    public TodoController(TodoRepository todoRepository, TodoService todoService) {
        this.todoRepository = todoRepository;
        this.todoService = todoService;
    }

    @ModelAttribute
    public void getTodos(Model model) {
        model.addAttribute("todos", todoRepository.findAll());
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Todo> todos = (List<Todo>) model.getAttribute("todos");
        if (todos != null) {
            int totalStarred = todos.stream().filter(Todo::isStarred).toList().size();
            int totalCompleted = todos.stream().filter(Todo::isCompleted).toList().size();
            model.addAttribute("totalStarred", totalStarred);
            model.addAttribute("totalCompleted", totalCompleted);
        }
        return "index";
    }

    @PostMapping("/add-todo")
    public String addTodoPage(@RequestParam String description, Model model) {
        todoService.addTodo(description);
        return "redirect:/todos";
    }

    @GetMapping("/todos/{id}/delete")
    public String deleteTodo(@PathVariable long id) {
        todoRepository.deleteById(id);
        return "redirect:/todos";
    }

    @GetMapping("/todos/{id}/starred")
    public String toggleStarTodo(@PathVariable long id) {
        Todo todo = todoRepository.findById(id).get();
        todo.setStarred(!todo.isStarred());
        todoRepository.save(todo);
        return "redirect:/todos";
    }

    @GetMapping("/todos/{id}/completed")
    public String toggleCompleteTodo(@PathVariable long id) {
        Todo todo = todoRepository.findById(id).get();
        todo.setCompleted(!todo.isCompleted());
        todoRepository.save(todo);
        return "redirect:/todos";
    }

    @GetMapping("/todos")
    public String listTodos() {
        return "todos";
    }
}
