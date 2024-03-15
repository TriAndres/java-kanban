package ru.practicum.manage.memory;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.manage.Managers;

import ru.practicum.manage.memory.task.InMemoryTaskManager;


import ru.practicum.manage.memory.task.TaskManagerTest;
import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    protected Task task1;
    protected Epic epic2;
    protected Subtask subtask3;
    @BeforeEach
    public void BeforeEach() {
        manager = new InMemoryTaskManager();
        task1 = new Task("name", "description", Status.NEW, LocalDateTime.now(), 0L);
        epic2 = new Epic("name", "description", Status.NEW, LocalDateTime.now(), 0L);
        subtask3 = new Subtask("name", "description", Status.NEW, LocalDateTime.now(), 0L, epic2.getId());
    }
    @Test
    public void addNewTask() {
        Task task = task1;
        manager.addNewTask(task);
        List<Task> taskList = manager.getTasks();
        assertNotNull(task.getStatus());
        assertEquals(Status.NEW, task.getStatus());
        assertEquals(List.of(task),taskList);
    }
}