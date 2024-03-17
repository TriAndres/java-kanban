package ru.practicum.manage.file;

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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.practicum.manage.file.FileBackedTaskManager.loadFromFile;

class FileBackedTaskManagerTest{
    File file = new File("src\\test\\java\\ru\\practicum\\manage\\file\\test2.csv");
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

    public Task addTask() {
        return new Task("name", "description", Status.NEW, LocalDateTime.now(), 0L);
    }

    public Epic addEpic() {
        return new Epic("name", "description", Status.NEW);
    }

    public Subtask addSubtask(Epic epic) {
        return new Subtask("name", "description", Status.NEW, LocalDateTime.now(), 0L, epic.getId());
    }
    @Test
    public void showAdd() {
        Task task = addTask();
        manager.addNewTask(task);
        assertEquals(List.of(task), manager.getTasks());

        Epic epic = addEpic();
        manager.addNewEpic(epic);
        assertEquals(List.of(epic), manager.getEpics());

        Subtask subtask = addSubtask(epic);
        manager.addNewSubtask(subtask);
        assertEquals(List.of(subtask), manager.getSubtasks());

        manager = loadFromFile(file);




    }
}