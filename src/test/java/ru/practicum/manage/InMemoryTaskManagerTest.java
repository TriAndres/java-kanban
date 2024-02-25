package ru.practicum.manage;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.manage.history.InMemoryHistoryManager;
import ru.practicum.manage.inMemoryTask.InMemoryTaskManager;
import ru.practicum.model.Epic;
import ru.practicum.model.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.practicum.model.Status.NEW;

public class InMemoryTaskManagerTest {


    //проверьте, что экземпляры класса Task равны друг другу, если равен их id;
    @Test
    public void equalsClassTaskId() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task = new Task("description");
        inMemoryTaskManager.addNewTask(task);
        final int idTask = inMemoryTaskManager.getTask(task.getId()).getId();

        Assertions.assertEquals(task.getId(),idTask);
    }

    //*проверьте, что наследники класса Task равны друг другу, если равен их id;
    @Test
    public void equalsClassEpicId() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Epic epic = new Epic("description");
        inMemoryTaskManager.addNewEpic(epic);
        int idEpic = inMemoryTaskManager.getEpic(epic.getId()).getId();
        System.out.println(idEpic);

        assertEquals(epic.getId(), idEpic);
    }


}