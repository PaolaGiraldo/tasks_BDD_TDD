package com.example.service;

import com.example.model.Task;
import com.example.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    private TaskRepository taskRepository;
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        taskService = new TaskService(taskRepository);
    }

    @Test
    void crearTarea_debeGuardarConCompletedEnFalse() {
        // Arrange
        Task nueva = new Task("Test Tarea", "Descripción");


        Task guardada = new Task(1L,"Test Tarea","Descripción", false);


        when(taskRepository.save(any(Task.class))).thenReturn(guardada);

        // Act
        Task result = taskService.createTask(nueva);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Tarea", result.getTitle());
        assertFalse(result.isCompleted());

        // Validar que el repositorio fue llamado con el objeto adecuado
        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(captor.capture());
        Task savedTask = captor.getValue();
        assertFalse(savedTask.isCompleted());
    }

    @Test
    void crearTarea_sinTitulo_debeLanzarExcepcion() {
        // Arrange
        Task sinTitulo = new Task();
        sinTitulo.setDescription("Desc");

        // Act & Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            taskService.createTask(sinTitulo);
        });

        assertEquals("El título es obligatorio", ex.getMessage());
        verify(taskRepository, never()).save(any());
    }
}
