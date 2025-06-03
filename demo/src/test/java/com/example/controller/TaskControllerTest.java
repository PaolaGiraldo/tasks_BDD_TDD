package com.example.controller;

import com.example.model.Task;
import com.example.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.AbstractMockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearTarea_debeRetornar201YDatos() throws Exception {
        // Arrange
        Task inputTask = new Task("Tarea BDD","Descripción de la tarea" );

        Task savedTask = new Task(1L,"Tarea BDD","Descripción de la tarea", false);


        Mockito.when(taskService.createTask(Mockito.any(Task.class)))
                .thenReturn(savedTask);


        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputTask)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Tarea BDD"))
                .andExpect(jsonPath("$.description").value("Descripción de la tarea"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void soloAceptaPost_enTasks() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isMethodNotAllowed()); // 405

        mockMvc.perform(put("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isMethodNotAllowed()); // 405

        mockMvc.perform(delete("/tasks"))
                .andExpect(status().isMethodNotAllowed()); // 405

        // Y el POST debe responder 201 (puedes reusar un mock si quieres)
        Task inputTask = new Task("Test", "Desc");

        Task savedTask = new Task(1L,"Test", "Desc", false);

        Mockito.when(taskService.createTask(Mockito.any(Task.class))).thenReturn(savedTask);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputTask)))
                .andExpect(status().isCreated());
    }

}