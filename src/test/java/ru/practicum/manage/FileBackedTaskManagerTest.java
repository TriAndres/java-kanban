package ru.practicum.manage;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.Epic;
import ru.practicum.model.Task;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackedTaskManagerTest {
    private FileBackedTaskManager manager;

    @BeforeEach
    public  void  BeforeEach() {
        File file = new File("src\\test\\java\\ru\\practicum\\manage\\fileBacked\\test2.csv");
         manager = FileBackedTaskManager.loadFromFile(file);
    }

    @Test
    public void equalsClassTaskId() {
        Task task = new Task(1,"Задача 1");
        manager.updateTask(task);
        final int idTask = manager.getTask(task.getId()).getId();
        System.out.println(idTask);

        Assertions.assertEquals(task.getId(),idTask);
    }
    @Test
    public void equalsClassEpicId() {
        Epic epic = new Epic(1,"Эпик 1");
        manager.updateEpic(epic);
        int idEpic = manager.getEpic(epic.getId()).getId();
        System.out.println(idEpic);

        assertEquals(epic.getId(), idEpic);
    }
}