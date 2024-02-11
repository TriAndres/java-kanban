package ru.practicum.manage;

import org.junit.jupiter.api.Test;
import ru.practicum.model.Epic;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.practicum.model.Status.NEW;

class InMemoryTaskManagerTest {
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();


    @Test
    void getListTask() {
    }

    @Test
    void getListEpic() {
    }

    @Test
    void getListSubtask() {
    }

    @Test
    void deleteTask() {
    }

    @Test
    void deleteEpic() {
    }

    @Test
    void deleteSubtask() {
    }

    @Test
    void getIdTask() {
    }

    @Test
    void getIdEpic() {
    }

    @Test
    void getIdSubtask() {
    }

    @Test
    void addTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW);
        final int taskId = inMemoryTaskManager.addNewTask(task).getId();

        final Task savedTask = inMemoryTaskManager.getTask(taskId);

        assertNotNull(savedTask);//"Задача не найдена."
        assertEquals(task, savedTask); //"Задачи не совпадают."

        final List<Task> tasks = inMemoryTaskManager.getTasks();

        assertNotNull(tasks);//"Задачи не возвращаются."
        Integer id = 1;
        assertEquals(id, tasks.size()); //"Неверное количество задач."
        assertEquals(task, tasks.get(0));//"Задачи не совпадают."
    }

    @Test
    void addEpic() {
        Epic epic = new Epic("Test addNewTask", "Test addNewTask description", NEW);
        final int epicId = inMemoryTaskManager.addNewTask(epic).getId();

        final Task savedTask = inMemoryTaskManager.getTask(epicId);

        assertNotNull(savedTask);//"Задача не найдена."
        assertEquals(epic, savedTask); //"Задачи не совпадают."

        final List<Task> tasks = inMemoryTaskManager.getTasks();

        assertNotNull(tasks);//"Задачи не возвращаются."
        Integer id = 1;
        assertEquals(id, tasks.size()); //"Неверное количество задач."
        assertEquals(epic, tasks.get(0));//"Задачи не совпадают."
    }

    @Test
    void addSubtask() {
        Subtask subtask = new Subtask("Test addNewTask", "Test addNewTask description", NEW);
        final int subtaskId = inMemoryTaskManager.addNewTask(subtask).getId();

        final Task savedTask = inMemoryTaskManager.getTask(subtaskId);

        assertNotNull(savedTask);//"Задача не найдена."
        assertEquals(subtask, savedTask); //"Задачи не совпадают."

        final List<Task> tasks = inMemoryTaskManager.getTasks();

        assertNotNull(tasks);//"Задачи не возвращаются."
        Integer id = 1;
        assertEquals(id, tasks.size()); //"Неверное количество задач."
        assertEquals(subtask, tasks.get(0));//"Задачи не совпадают."
    }

    @Test
    void updateTask() {
    }

    @Test
    void updateEpic() {
    }

    @Test
    void updateSubtask() {
    }

    @Test
    void deleteIdTask() {
    }

    @Test
    void deleteIdEpic() {
    }

    @Test
    void deleteIdSubtask() {
    }

    @Test
    void add() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW);
        task.setId(1);

        historyManager.add(task);
        historyManager.getHistory();
        List<Task> history = historyManager.getHistory();
        assertNotNull(history); //"История не пустая."
        Integer id = 1;
        assertEquals(id, history.size());//"История не пустая."
    }

    @Test
    void getHistory() {
    }

    @Test
    void getListSubtaskIdEpic() {
    }

    @Test
    void statusEpic() {
    }
}
