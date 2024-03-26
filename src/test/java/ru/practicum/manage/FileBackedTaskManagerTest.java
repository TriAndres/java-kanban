package ru.practicum.manage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.manage.file.FileBackedTaskManager;
import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileBackedTaskManagerTest  {
    public static File file = new File("src\\test\\java\\ru\\practicum\\manage\\test2.csv");
    FileBackedTaskManager manager;
    @BeforeEach
    public void beforeEach() {
        manager = new FileBackedTaskManager(file);
    }

    @AfterEach
    public void afterEach() {
        try {
            Files.delete(file.toPath());
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    public void saveTasksEpicsSubtasks() throws IOException {
        Task task = new Task("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
        manager.addNewTask(task);

        Epic epic = new Epic("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
        manager.addNewEpic(epic);

        Subtask subtask = new Subtask("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now().plusDays(1), epic.getId());
        manager.addNewSubtask(subtask);


        assertEquals(List.of(task), manager.getTasks());
        assertEquals(List.of(epic), manager.getEpics());
        assertEquals(List.of(subtask), manager.getSubtasks());

    }

    @Test
    public void emptyTasksEpicsSubtasks() {

        assertEquals(Collections.EMPTY_LIST, manager.getTasks());
        assertEquals(Collections.EMPTY_LIST, manager.getEpics());
        assertEquals(Collections.EMPTY_LIST, manager.getSubtasks());
    }

    @Test
    public void emptyHistory() {

        assertEquals(Collections.EMPTY_LIST, manager.getHistory());
    }

    @Test
    public void addNewTask() {
        Task task = new Task("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
        manager.addNewTask(task);
        List<Task> taskList = manager.getTasks();
        assertNotNull(task.getStatus());
        assertEquals(Status.NEW, task.getStatus());
        assertEquals(List.of(task), taskList);
    }

    @Test
    public void addNewEpic() {
        Epic epic = new Epic("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
        manager.addNewEpic(epic);
        List<Epic> epicList = manager.getEpics();
        assertNotNull(epic.getStatus());
        assertEquals(Status.NEW, epic.getStatus());
        assertEquals(List.of(epic), epicList);
    }

    @Test
    public void addNewSubtask() {
        Epic epic = new Epic("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
        manager.addNewEpic(epic);
        Subtask subtask = new Subtask("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now(), epic.getId());
        manager.addNewSubtask(subtask);
        List<Subtask> subtaskList = manager.getSubtasks();
        assertNotNull(subtask.getStatus());
        assertEquals(Status.NEW, subtask.getStatus());
        assertEquals(List.of(subtask), subtaskList);
    }

    @Test
    public void deleteIdTask() {
        Task task1 = new Task("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
        Task task2 = new Task("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now().plusDays(1));
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
        Epic epic3 = new Epic("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
        Epic epic4 = new Epic("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
        manager.addNewEpic(epic3);
        manager.addNewEpic(epic4);
        assertEquals(2, manager.getEpics().size());
        manager.deleteIdEpic(epic4.getId());
        assertEquals(1, manager.getEpics().size());
    }

    @Test
    public void deleteIdSubtask() {
        Epic epic = new Epic("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
        manager.addNewEpic(epic);

        Subtask subtask1 = new Subtask("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now(), epic.getId());
        Subtask subtask2 = new Subtask("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now().plusDays(1), epic.getId());
        manager.addNewSubtask(subtask1);
        manager.addNewSubtask(subtask2);
        assertEquals(2, manager.getSubtasks().size());
        manager.deleteIdSubtask(subtask2.getId());
        assertEquals(1, manager.getSubtasks().size());
    }

    @Test
    public void updateTask() {
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
    }

    @Test
    public void updateEpic() {
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
    }

    @Test
    public void updateSubtask() {
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
    }

    @Test
    void deleteAllTask() {
        Task task = new Task("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
        manager.addNewTask(task);

        List<Task> taskList = manager.getTasks();
        assertEquals(List.of(task), taskList);

        manager.deleteAllTask();
        assertEquals(Collections.EMPTY_LIST, manager.getTasks());

    }

    @Test
    void deleteAllEpic() {
        Epic epic = new Epic("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
        manager.addNewEpic(epic);

        List<Epic> epicList = manager.getEpics();
        assertEquals(List.of(epic), epicList);

        manager.deleteAllEpic();
        assertEquals(Collections.EMPTY_LIST, manager.getEpics());
    }

    @Test
    void deleteAllSubtask() {
        Epic epic = new Epic("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
        manager.addNewEpic(epic);

        Subtask subtask = new Subtask("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now(), epic.getId());
        manager.addNewSubtask(subtask);

        List<Subtask> subtaskList = manager.getSubtasks();
        assertEquals(List.of(subtask), subtaskList);

        manager.deleteAllSubtask();
        assertEquals(Collections.EMPTY_LIST, manager.getSubtasks());
    }

    @Test
    public void getListSubtaskIdEpic() {
        Epic epic = new Epic("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now());
        manager.addNewEpic(epic);

        Subtask subtask = new Subtask("name", "description", Status.NEW, Duration.ZERO, LocalDateTime.now(), epic.getId());
        manager.addNewSubtask(subtask);

        assertEquals(List.of(subtask), manager.getListSubtaskIdEpic(epic.getId()));
    }
}