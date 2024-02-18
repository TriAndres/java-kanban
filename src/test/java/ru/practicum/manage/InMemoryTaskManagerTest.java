package ru.practicum.manage;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import ru.practicum.model.Epic;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.practicum.model.Status.NEW;

public class InMemoryTaskManagerTest {
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    //проверьте, что экземпляры класса Task равны друг другу, если равен их id;
    @Test
    public void equalsClassTaskId() {
        Task task = new Task("title", "description", NEW);
        final int idTask = inMemoryTaskManager.addNewTask(task).getId();

        Assertions.assertEquals(idTask, inMemoryTaskManager.addNewTask(task).getId());
    }

    //*проверьте, что наследники класса Task равны друг другу, если равен их id;
    @Test
    public void equalsClassEpicId() {
        Epic epic = new Epic("title", "description", NEW);
        final int idEpic = inMemoryTaskManager.addNewEpic(epic).getId();

        Assertions.assertEquals(idEpic, inMemoryTaskManager.addNewEpic(epic).getId());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
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


}
