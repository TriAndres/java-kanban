package ru.practicum.manage.memory;

import org.junit.jupiter.api.Test;
import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InMemoryTaskManagerTest {
    InMemoryTaskManager manager = new InMemoryTaskManager();

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
    public void addNewTask() {
        Task task = addTask();
        manager.addNewTask(task);
        List<Task> taskList = manager.getTasks();
        assertNotNull(task.getStatus());
        assertEquals(Status.NEW, task.getStatus());
        assertEquals(List.of(task), taskList);
    }

    @Test
    public void addNewEpic() {
        Epic epic = addEpic();
        manager.addNewEpic(epic);
        List<Epic> epicList = manager.getEpics();
        assertNotNull(epic.getStatus());
        assertEquals(Status.NEW, epic.getStatus());
        assertEquals(List.of(epic), epicList);
    }

    @Test
    public void addNewSubtask() {
        Epic epic = addEpic();
        manager.addNewEpic(epic);
        Subtask subtask = addSubtask(epic);
        manager.addNewSubtask(subtask);
        List<Subtask> subtaskList = manager.getSubtasks();
        assertNotNull(subtask.getStatus());
        assertEquals(Status.NEW, subtask.getStatus());
        assertEquals(List.of(subtask), subtaskList);
    }

    @Test
    public void deleteIdTask() {
        Task task1 = addTask();
        Task task2 = addTask();
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        assertEquals(2, manager.getTasks().size());
        System.out.println(manager.getTasks());
        manager.deleteIdTask(task2.getId());
        assertEquals(1, manager.getTasks().size());
        System.out.println(manager.getTasks());

    }

    @Test
    public void deleteIdEpic() {
        Epic epic3 = addEpic();
        Epic epic4 = addEpic();
        manager.addNewEpic(epic3);
        manager.addNewEpic(epic4);
        assertEquals(2, manager.getEpics().size());
        manager.deleteIdEpic(epic4.getId());
        assertEquals(1, manager.getEpics().size());
    }

    @Test
    public void deleteIdSubtask() {
        Epic epic = addEpic();
        manager.addNewEpic(epic);

        Subtask subtask1 = addSubtask(epic);
        Subtask subtask2 = addSubtask(epic);
        manager.addNewSubtask(subtask1);
        manager.addNewSubtask(subtask2);
        assertEquals(2, manager.getSubtasks().size());
        manager.deleteIdSubtask(subtask2.getId());
        assertEquals(1, manager.getSubtasks().size());
    }

    @Test
    public void updateTask() {
        Task task1 = new Task("name 1", "description 1", Status.NEW, LocalDateTime.now(), 0L);
        manager.addNewTask(task1);
        Task task2 = manager.getTaskId(task1.getId());
        System.out.println(task2);
        assertEquals(task1, task2);
        task2.setName("name 2");
        task2.setDescription("description 2");
        task2.setStatus(Status.IN_PROGRESS);
        manager.updateTask(task2);
        task2 = manager.getTaskId(task1.getId());
        assertEquals(task1, task2);
        System.out.println(task1);
        assertEquals(1, manager.getTasks().size());
    }

    @Test
    public void updateEpic() {
        Epic task1 = new Epic("name", "description", Status.NEW);
        manager.addNewEpic(task1);
        Epic task2 = manager.getEpicId(task1.getId());
        System.out.println(task2);
        assertEquals(task1, task2);
        task2.setName("name 2");
        task2.setDescription("description 2");
        task2.setStatus(Status.IN_PROGRESS);
        manager.updateTask(task2);
        task2 = manager.getEpicId(task1.getId());
        assertEquals(task1, task2);
        System.out.println(task1);
        assertEquals(1, manager.getEpics().size());
    }

    @Test
    public void updateSubtask() {
        Epic epic = addEpic();
        manager.addNewEpic(epic);

        Subtask task1 = new Subtask("name", "description", Status.NEW, LocalDateTime.now(), 0L, epic.getId());
        manager.addNewSubtask(task1);
        Subtask task2 = manager.getSubtaskId(task1.getId());
        System.out.println(task2);
        assertEquals(task1, task2);
        task2.setName("name 2");
        task2.setDescription("description 2");
        task2.setStatus(Status.IN_PROGRESS);
        manager.updateSubtask(task2);
        task2 = manager.getSubtaskId(task1.getId());
        assertEquals(task1, task2);
        System.out.println(task1);
        assertEquals(1, manager.getSubtasks().size());
        System.out.println(manager.getSubtasks());
    }
}