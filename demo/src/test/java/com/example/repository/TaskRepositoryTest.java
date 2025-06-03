package com.example.repository;

import com.example.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void guardarYBuscarTarea_enBaseDeDatos() {
        // Arrange
        Task tarea = new Task("Test persistencia","Guardar en H2");


        // Act
        Task savedTask = taskRepository.save(tarea);
        Optional<Task> found = taskRepository.findById(savedTask.getId());

        // Assert
        assertTrue(found.isPresent());
        assertEquals("Test persistencia", found.get().getTitle());
        assertEquals(false, found.get().isCompleted());
    }
}
