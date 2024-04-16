package com.dsi.todo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@Entity
@ToString
@Table(name = "todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Description cannot be empty")
    @Length(min = 3 , max = 5000, message = "Length must be between 3 to 5000 characters")
    @Column(length = 5000)
    private String description;

    @Column(name = "is_starred")
    private Boolean isStarred = false;

    @Column(name = "is_completed")
    private Boolean isCompleted = false;

    public Todo() {
    }

    public Todo(String description, boolean isCompleted, boolean isStarred) {
        this.description = description;
        this.isCompleted = isCompleted;
        this.isStarred = isStarred;
    }
    public Todo(long id,String description, boolean isCompleted, boolean isStarred) {
        this.id = id;
        this.description = description;
        this.isCompleted = isCompleted;
        this.isStarred = isStarred;
    }

}
