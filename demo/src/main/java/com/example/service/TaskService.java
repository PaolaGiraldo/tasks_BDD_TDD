package com.example.service;

import com.example.model.Task;
import com.example.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("El título es obligatorio");
        }

        task.setCompleted(false);
        return taskRepository.save(task);
    }
}