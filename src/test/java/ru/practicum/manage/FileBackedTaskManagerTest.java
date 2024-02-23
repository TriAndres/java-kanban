package ru.practicum.manage;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.Epic;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {
    private FileBackedTaskManager manager;

    @BeforeEach
    public  void  BeforeEach() {
        File file = new File("src\\test\\java\\ru\\practicum\\manage\\fileBacked\\test2.csv");
         manager = FileBackedTaskManager.loadFromFile(file);
    }

    @Test
    public void equalsClassTaskId() {
        Task task = new Task("Задача 1");
        manager.addNewTask(task);
        final int idTask = manager.getTask(task.getId()).getId();
        System.out.println();

        Assertions.assertEquals(task.getId(),idTask);
    }
    @Test
    public void equalsClassEpicId() {
        Epic epic = new Epic("Эпик 1");
        manager.addNewEpic(epic);
        int idEpic = manager.getEpic(epic.getId()).getId();
        System.out.println(idEpic);

        assertEquals(epic.getId(), idEpic);
    }

    @Test
    public void equalsClassSubtaskId() {
        Task task = new Task("Задача 1");
        manager.addNewTask(task);

        Epic epic = new Epic("Эпик 1");
        manager.addNewEpic(epic);

        Subtask subtask = new Subtask("Подзадача 1",1);
        manager.addNewSubtask(subtask);
        int idSubtask = manager.getEpic(subtask.getId()).getId();
        System.out.println(idSubtask);

        assertEquals(subtask.getIdEpic(), idSubtask);

    }
}