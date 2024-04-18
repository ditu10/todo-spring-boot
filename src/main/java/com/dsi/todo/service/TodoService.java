package com.dsi.todo.service;

import com.dsi.todo.model.Todo;
import com.dsi.todo.repository.TodoRepository;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void exportJasperReport(HttpServletResponse response) throws JRException, IOException {
        List<Todo> address = todoRepository.findAll();
        //Get file and compile it
        File file = ResourceUtils.getFile("classpath:todos_01.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(address);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Simplifying Tech");
        //Fill Jasper report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        //Export report
        JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
    }
}
