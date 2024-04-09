package com.dsi.todo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    private String title;

    @Column(length = 5000)
    private String description;

    @Column(name = "is_starred")
    private boolean isStarred;

    @Column(name = "is_completed")
    private boolean isCompleted;

    public Todo() {
    }

    public Todo(String description, boolean isCompleted, boolean isStarred) {
        this.description = description;
        this.isCompleted = isCompleted;
        this.isStarred = isStarred;
    }

}
