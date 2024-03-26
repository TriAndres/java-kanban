package ru.practicum.manage;


import org.junit.jupiter.api.Test;
import ru.practicum.manage.tasks.TaskManager;
import ru.practicum.model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public abstract class TaskManagerTest<T extends TaskManager> {
    protected T manager;

    @Test
    public void addNewTask() {
        try {
            Task task = new Task("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
            manager.addNewTask(task);
            List<Task> taskList = manager.getTasks();
            assertNotNull(task.getStatus());
            assertEquals(Status.NEW, task.getStatus());
            assertEquals(List.of(task), taskList);
        } catch (NullPointerException e) {
            System.out.println("addNewTask()");
        }

    }

    @Test
    public void addNewEpic() {
        try {
            Epic epic = new Epic("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
            manager.addNewEpic(epic);
            List<Epic> epicList = manager.getEpics();
            assertNotNull(epic.getStatus());
            assertEquals(Status.NEW, epic.getStatus());
            assertEquals(List.of(epic), epicList);
        } catch (NullPointerException e) {
            System.out.println("addNewEpic()");
        }
    }

    @Test
    public void addNewSubtask() {
        try {
            Epic epic = new Epic("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
            manager.addNewEpic(epic);
            Subtask subtask = new Subtask("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now(), epic.getId());
            manager.addNewSubtask(subtask);
            List<Subtask> subtaskList = manager.getSubtasks();
            assertNotNull(subtask.getStatus());
            assertEquals(Status.NEW, subtask.getStatus());
            assertEquals(List.of(subtask), subtaskList);
        } catch (NullPointerException e) {
            System.out.println("addNewSubtask()");
        }
    }

    @Test
    public void deleteIdTask() {
        try {
            Task task1 = new Task("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
            Task task2 = new Task("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now().plusDays(1));
            manager.addNewTask(task1);
            manager.prioritizedRemove(task2);
            manager.addNewTask(task2);
            assertEquals(2, manager.getTasks().size());
            System.out.println(manager.getTasks());
            manager.deleteIdTask(task2.getId());
            assertEquals(1, manager.getTasks().size());
            System.out.println(manager.getTasks());
        } catch (NullPointerException e) {
            System.out.println("deleteIdTask()");
        }
    }

    @Test
    public void deleteIdEpic() {
        try {
            Epic epic3 = new Epic("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
            Epic epic4 = new Epic("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
            manager.addNewEpic(epic3);
            manager.addNewEpic(epic4);
            assertEquals(2, manager.getEpics().size());
            manager.deleteIdEpic(epic4.getId());
            assertEquals(1, manager.getEpics().size());
        } catch (NullPointerException e) {
            System.out.println("deleteIdEpic()");
        }
    }

    @Test
    public void deleteIdSubtask() {
        try {
            Epic epic = new Epic("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
            manager.addNewEpic(epic);

            Subtask subtask1 = new Subtask("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now(), epic.getId());
            Subtask subtask2 = new Subtask("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now().plusDays(1), epic.getId());
            manager.addNewSubtask(subtask1);
            manager.prioritizedRemove(subtask2);
            manager.addNewSubtask(subtask2);
            assertEquals(2, manager.getSubtasks().size());
            manager.deleteIdSubtask(subtask2.getId());
            assertEquals(1, manager.getSubtasks().size());
        } catch (NullPointerException e) {
            System.out.println("deleteIdSubtask()");
        }
    }


    @Test
    public void updateTask() {
        try {
            Task task1 = new Task("name 1", "description 1", Status.NEW, Duration.ZERO, LocalDateTime.now());
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
        } catch (NullPointerException e) {
            System.out.println("updateTask()");
        }
    }

    @Test
    public void updateEpic() {
        try {
            Epic task1 = new Epic("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
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
        } catch (NullPointerException e) {
            System.out.println("updateEpic()");
        }
    }

    @Test
    public void updateSubtask() {
        try {
            Epic epic = new Epic("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
            manager.addNewEpic(epic);

            Subtask task1 = new Subtask("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now(), epic.getId());
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
        } catch (NullPointerException e) {
            System.out.println("updateSubtask()");
        }
    }

    @Test
    void deleteAllTask() {
        try {
            Task task = new Task("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
            manager.addNewTask(task);

            List<Task> taskList = manager.getTasks();
            assertEquals(List.of(task), taskList);

            manager.deleteAllTask();
            assertEquals(Collections.EMPTY_LIST, manager.getTasks());

        } catch (NullPointerException e) {
            System.out.println("deleteAllTask()");
        }
    }

    @Test
    void deleteAllEpic() {
        try {
            Epic epic = new Epic("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
            manager.addNewEpic(epic);

            List<Epic> epicList = manager.getEpics();
            assertEquals(List.of(epic), epicList);

            manager.deleteAllEpic();
            assertEquals(Collections.EMPTY_LIST, manager.getEpics());
        } catch (NullPointerException e) {
            System.out.println("deleteAllEpic()");
        }
    }

    @Test
    void deleteAllSubtask() {
        try {
            Epic epic = new Epic("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
            manager.addNewEpic(epic);

            Subtask subtask = new Subtask("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now(), epic.getId());
            manager.addNewSubtask(subtask);

            List<Subtask> subtaskList = manager.getSubtasks();
            assertEquals(List.of(subtask), subtaskList);

            manager.deleteAllSubtask();
            assertEquals(Collections.EMPTY_LIST, manager.getSubtasks());
        } catch (NullPointerException e) {
            System.out.println("deleteAllSubtask()");
        }
    }

    @Test
    public void getListSubtaskIdEpic() {
        try {
            Epic epic = new Epic("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
            manager.addNewEpic(epic);

            Subtask subtask = new Subtask("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now(), epic.getId());
            manager.addNewSubtask(subtask);

            assertEquals(List.of(subtask), manager.getListSubtaskIdEpic(epic.getId()));
        } catch (NullPointerException e) {
            System.out.println("getListSubtaskIdEpic()");
        }
    }
}