package ru.practicum.manage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.manage.file.FileBackedTaskManager;
import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBackedTaskManagerTest  extends InMemoryTaskManagerTest{
    public static File file = new File("src\\test\\java\\ru\\practicum\\manage\\test2.csv");
    FileBackedTaskManager manager;
    @BeforeEach
    public void beforeEach() {
        manager = new FileBackedTaskManager(file);
    }

    @AfterEach
    public void afterEach() {
        try {
            Files.delete(file.toPath());
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    public void saveTasksEpicsSubtasks() throws IOException {
        Task task = new Task("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
        manager.addNewTask(task);

        Epic epic = new Epic("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
        manager.addNewEpic(epic);

        Subtask subtask = new Subtask("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now().plusDays(1), epic.getId());
        manager.addNewSubtask(subtask);


        assertEquals(List.of(task), manager.getTasks());
        assertEquals(List.of(epic), manager.getEpics());
        assertEquals(List.of(subtask), manager.getSubtasks());

    }

    @Test
    public void emptyTasksEpicsSubtasks() {

        assertEquals(Collections.EMPTY_LIST, manager.getTasks());
        assertEquals(Collections.EMPTY_LIST, manager.getEpics());
        assertEquals(Collections.EMPTY_LIST, manager.getSubtasks());
    }

    @Test
    public void emptyHistory() {

        assertEquals(Collections.EMPTY_LIST, manager.getHistory());
    }
}