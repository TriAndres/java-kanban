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


    //проверьте, что экземпляры класса Task равны друг другу, если равен их id;
    @Test
    public void equalsClassTaskId() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task = new Task(1,"title", "description", NEW);
        inMemoryTaskManager.addNewTask(task);
        final int idTask = inMemoryTaskManager.getTask(task.getId()).getId();

        Assertions.assertEquals(task.getId(),idTask);
    }

    //*проверьте, что наследники класса Task равны друг другу, если равен их id;
    @Test
    public void equalsClassEpicId() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        //Epic epic = new Epic("title", "description", NEW);
        Epic epic = new Epic("Эпик 1");
        inMemoryTaskManager.addNewTask(epic);
        int idEpic = inMemoryTaskManager.getEpic(epic.getId()).getId();
        System.out.println(idEpic);

        assertEquals(epic.getId(), idEpic);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void addTask() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW);
        inMemoryTaskManager.addNewTask(task);
        final int taskId = inMemoryTaskManager.getTask(task.getId()).getId();

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
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Epic epic = new Epic("Test addNewTask", "Test addNewTask description", NEW);
        inMemoryTaskManager.addNewEpic(epic);
        final int epicId = inMemoryTaskManager.getEpic(epic.getId()).getId();

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
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Subtask subtask = new Subtask("Test addNewTask", "Test addNewTask description", NEW);
        inMemoryTaskManager.addNewTask(subtask);
        final int subtaskId = inMemoryTaskManager.getTask(subtask.getId()).getId();

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