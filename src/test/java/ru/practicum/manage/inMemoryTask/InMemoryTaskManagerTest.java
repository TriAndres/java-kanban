package ru.practicum.manage.inMemoryTask;



import org.junit.jupiter.api.Test;
import ru.practicum.model.Epic;
import ru.practicum.model.Task;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    //проверьте, что экземпляры класса Task равны друг другу, если равен их id;
    @Test
    public void equalsClassTaskId() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task = new Task("description");
        inMemoryTaskManager.addNewTask(task);
        final int idTask = inMemoryTaskManager.getTask(task.getId()).getId();

        assertEquals(task.getId(),idTask);
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