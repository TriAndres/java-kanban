package ru.practicum.manage;

import org.junit.jupiter.api.Test;
import ru.practicum.model.Epic;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.util.List;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;
import static ru.practicum.model.Status.NEW;

public class InMemoryTaskManagerTest {
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    @Test
    public void getListTask() {
    }

    @Test
    public void getListEpic() {
    }

    @Test
    public void getListSubtask() {
    }

    @Test
    public void deleteTask() {
    }

    @Test
    public void deleteEpic() {
    }

    @Test
    public void deleteSubtask() {
    }

    @Test
    public void getIdTask() {
    }

    @Test
    public void getIdEpic() {
    }

    @Test
    public void getIdSubtask() {
    }

    @Test
    public void addTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW);
        final int taskId = inMemoryTaskManager.addNewTask(task).getId();

        final Task savedTask = inMemoryTaskManager.getTask(taskId);

        assertNotNull(savedTask);//"Задача не найдена."
        assertEquals(task, savedTask); //"Задачи не совпадают."

        final List<Task> tasks = inMemoryTaskManager.getTasks();

        assertNotNull(tasks);//"Задачи не возвращаются."
        assertEquals(1, tasks.size());//Неверное количество задач
        assertEquals(task, tasks.get(0));//"Задачи не совпадают."
    }

    @Test
    public void addEpic() {
        Epic epic = new Epic("Test addNewTask", "Test addNewTask description", NEW);
        final int epicId = inMemoryTaskManager.addNewTask(epic).getId();

        final Task savedTask = inMemoryTaskManager.getTask(epicId);

        assertNotNull(savedTask);//"Задача не найдена."
        assertEquals(epic, savedTask); //"Задачи не совпадают."

        final List<Task> tasks = inMemoryTaskManager.getTasks();

        assertNotNull(tasks);//"Задачи не возвращаются."
        assertEquals(1, tasks.size()); //"Неверное количество задач."
        assertEquals(epic, tasks.get(0));//"Задачи не совпадают."
    }

    @Test
    public void addSubtask() {
        Subtask subtask = new Subtask("Test addNewTask", "Test addNewTask description", NEW);
        final int subtaskId = inMemoryTaskManager.addNewTask(subtask).getId();

        final Task savedTask = inMemoryTaskManager.getTask(subtaskId);

        assertNotNull(savedTask);//"Задача не найдена."
        assertEquals(subtask, savedTask); //"Задачи не совпадают."

        final List<Task> tasks = inMemoryTaskManager.getTasks();

        assertNotNull(tasks);//"Задачи не возвращаются."
        assertEquals(1, tasks.size()); //"Неверное количество задач."
        assertEquals(subtask, tasks.get(0));//"Задачи не совпадают."
    }

    @Test
    public void updateTask() {
    }

    @Test
    public void updateEpic() {
    }

    @Test
    public void updateSubtask() {
    }

    @Test
    public void deleteIdTask() {
    }

    @Test
    public void deleteIdEpic() {
    }

    @Test
    public void deleteIdSubtask() {
    }

    @Test
    public void add() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW);
        task.setId(1);

        historyManager.add(task);
        historyManager.getHistory();
        List<Task> history = historyManager.getHistory();
        assertNotNull(history); //"История не пустая."
        assertEquals(1, history.size());//"История не пустая."
    }

    @Test
    public void getHistory() {
    }

    @Test
    public void getListSubtaskIdEpic() {
    }

    @Test
    public void statusEpic() {
    }
}
