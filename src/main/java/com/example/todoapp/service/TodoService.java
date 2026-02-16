package com.example.todoapp.service;

import com.example.todoapp.dto.TodoDto;
import com.example.todoapp.entity.Todo;
import com.example.todoapp.repository.TodoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoDto> getTodosForUserId(String userId) {
        return todoRepository.findByUserId(userId)
            .stream()
            .map(this::toDto)
            .toList();
    }

    public TodoDto createTodo(String userId, TodoDto request) {
        Todo todo = new Todo();
        todo.setTitle(request.title());
        todo.setDescription(request.description());
        todo.setCompleted(request.completed());
        todo.setUserId(userId);

        return toDto(todoRepository.save(todo));
    }

    public TodoDto updateTodo(String userId, String id, TodoDto request) {
        Todo todo = todoRepository.findByIdAndUserId(id, userId)
            .orElseThrow(() -> new IllegalArgumentException("Todo not found"));

        todo.setTitle(request.title());
        todo.setDescription(request.description());
        todo.setCompleted(request.completed());

        return toDto(todoRepository.save(todo));
    }

    public void deleteTodo(String userId, String id) {
        Todo todo = todoRepository.findByIdAndUserId(id, userId)
            .orElseThrow(() -> new IllegalArgumentException("Todo not found"));

        todoRepository.delete(todo);
    }

    private TodoDto toDto(Todo todo) {
        return new TodoDto(
            todo.getId(),
            todo.getTitle(),
            todo.getDescription(),
            todo.isCompleted()
        );
    }
}
