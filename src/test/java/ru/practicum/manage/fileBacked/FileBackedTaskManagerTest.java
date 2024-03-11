package ru.practicum.manage.fileBacked;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.manage.file.FileBackedTaskManager;
import ru.practicum.model.Epic;
import ru.practicum.model.Task;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {
    private FileBackedTaskManager manager;
    @BeforeEach
    public  void  BeforeEach() {
        File file = new File("src\\test\\java\\ru\\practicum\\manage\\file\\test2.csv");
        manager = FileBackedTaskManager.loadFromFile(file);
    }

    @Test
    public void equalsClassTaskId() {
        Task task = new Task("Задача 1");
        manager.addNewTask(task);
        final int idTask = manager.getTaskId(task.getId()).getId();
        System.out.println(idTask);

        Assertions.assertEquals(task.getId(),idTask);
    }
    @Test
    public void equalsClassEpicId() {
        Epic epic = new Epic("Эпик 1");
        manager.addNewEpic(epic);
        int idEpic = manager.getEpicId(epic.getId()).getId();
        System.out.println(idEpic);

        assertEquals(epic.getId(), idEpic);
    }
}