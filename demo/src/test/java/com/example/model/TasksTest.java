package com.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TasksTest {

    @Test
    public void shouldInstantiateTaskCorrectly() {
        Task task = new Task("Comprar pan", "Ir a la panadería");

        assertEquals("Comprar pan", task.getTitle());
        assertEquals("Ir a la panadería", task.getDescription());
        assertFalse(task.isCompleted());
    }

    @Test
    public void shouldThrowExceptionWhenTitleIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Task(null, "Descripción válida");
        });

        assertEquals("Title is required", exception.getMessage());
    }
}
