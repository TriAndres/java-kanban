package ru.practicum.manage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.practicum.manage.FileBackedTaskManager.loadFromFile;

class FileBackedTaskManagerTest{
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
    public void correctlySaveAndLoad() {
        Task task = new Task("name", "description", Status.NEW, LocalDateTime.now(), 0L);
        manager.addNewTask(task);
        assertEquals(List.of(task), manager.getTasks());

        Epic epic = new Epic("name", "description", Status.NEW, LocalDateTime.now(), 0L);
        manager.addNewEpic(epic);
        assertEquals(List.of(epic), manager.getEpics());

        Subtask subtask = new Subtask("name", "description", Status.NEW, LocalDateTime.now(), 0L, epic.getId());
        manager.addNewSubtask(subtask);
        assertEquals(List.of(subtask), manager.getSubtasks());

        manager = loadFromFile(file);
    }

    @Test
    public void saveAndLoadEmptyTasksEpicsSubtasks() {
        FileBackedTaskManager fileManager = new FileBackedTaskManager(file);
        fileManager.save();
        fileManager.loadFromFile(file);
        assertEquals(Collections.EMPTY_LIST, manager.getTasks());
        assertEquals(Collections.EMPTY_LIST, manager.getEpics());
        assertEquals(Collections.EMPTY_LIST, manager.getSubtasks());
    }

    @Test
    public void shouldSaveAndLoadEmptyHistory() {
        FileBackedTaskManager fileManager = new FileBackedTaskManager(file);
        fileManager.save();
        fileManager.loadFromFile(file);
        assertEquals(Collections.EMPTY_LIST, manager.getHistory());
    }

}