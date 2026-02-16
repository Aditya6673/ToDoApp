package com.example.todoapp.controller;

import com.example.todoapp.dto.TodoDto;
import com.example.todoapp.entity.User;
import com.example.todoapp.service.TodoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<List<TodoDto>> getTodos(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(todoService.getTodosForUserId(user.getId()));
    }

    @PostMapping
    public ResponseEntity<TodoDto> createTodo(@AuthenticationPrincipal User user, @Valid @RequestBody TodoDto request) {
        return ResponseEntity.ok(todoService.createTodo(user.getId(), request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodo(
        @AuthenticationPrincipal User user,
        @PathVariable String id,
        @Valid @RequestBody TodoDto request
    ) {
        return ResponseEntity.ok(todoService.updateTodo(user.getId(), id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@AuthenticationPrincipal User user, @PathVariable String id) {
        todoService.deleteTodo(user.getId(), id);
        return ResponseEntity.noContent().build();
    }
}
