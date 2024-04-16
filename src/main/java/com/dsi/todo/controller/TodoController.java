package com.dsi.todo.controller;

import com.dsi.todo.model.Todo;
import com.dsi.todo.repository.TodoRepository;
import com.dsi.todo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

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

    @ModelAttribute
    public Todo getTodo() {
        return new Todo();
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Todo> todos = (List<Todo>) model.getAttribute("todos");
        if (todos != null) {
            int totalStarred = todos.stream().filter(Todo::getIsStarred).toList().size();
            int totalCompleted = todos.stream().filter(Todo::getIsCompleted).toList().size();
            model.addAttribute("totalStarred", totalStarred);
            model.addAttribute("totalCompleted", totalCompleted);
        }
        return "index";
    }

    @PostMapping("/add-todo")
    public String addTodoPage(@Valid @ModelAttribute Todo todo,
                              BindingResult result,
                              Model model,
                              RedirectAttributes attributes) {
        if(result.hasErrors()) {
            attributes.addFlashAttribute(BindingResult.class.getName() +".todo", result);
            attributes.addFlashAttribute("todo", todo);
            return "redirect:/todos";
        }
        try {
            todoRepository.save(todo);
        }
        catch(Exception e) {
            model.addAttribute("error", e);
            return "error";
        }
        return "redirect:/todos";
    }

    @GetMapping("/update-todo/{id}")
    public String updateTodoPage(@PathVariable long id,
                                 Model model) {
        try{
            Todo todo = todoRepository.findById(id).get();
            model.addAttribute("todo", todo);
        } catch (Exception e) {
            model.addAttribute("error", e);
            return "error";
        }

        return "updateTodoForm";
    }
    @PostMapping("/process-update")
    public String processUpdate(@Valid @ModelAttribute Todo todo,
                                BindingResult result,
                                RedirectAttributes attributes) {
        if(result.hasErrors()) {
            attributes.addFlashAttribute(BindingResult.class.getName()+".todo", result);
            attributes.addFlashAttribute("todo", todo);
            return "redirect:/update-todo/"+todo.getId();
        }
        todoRepository.save(todo);
        return "redirect:/todos";
    }

    @GetMapping("/todos/{id}/delete")
    public String deleteTodo(@PathVariable long id, Model model) {
        try {
            todoRepository.deleteById(id);
        }catch (Exception e) {
            model.addAttribute("error", e);
            return "error";
        }
        return "redirect:/todos";
    }

    @GetMapping("/todos/{id}/starred")
    public String toggleStarTodo(@PathVariable long id, Model model) {
        try{
            Todo todo = todoRepository.findById(id).get();
            todo.setIsStarred(!todo.getIsStarred());
            todoRepository.save(todo);
        } catch (Exception e) {
            model.addAttribute("error", e);
            return "error";
        }
        return "redirect:/todos";
    }

    @GetMapping("/todos/{id}/completed")
    public String toggleCompleteTodo(@PathVariable long id, Model model) {
        try {
            Todo todo = todoRepository.findById(id).get();
            todo.setIsCompleted(!todo.getIsCompleted());
            todoRepository.save(todo);
        } catch (Exception e) {
            model.addAttribute("error", e);
            return "error";
        }
        return "redirect:/todos";
    }

    @GetMapping("/todos")
    public String listTodos() {
        return "todos";
    }
}
