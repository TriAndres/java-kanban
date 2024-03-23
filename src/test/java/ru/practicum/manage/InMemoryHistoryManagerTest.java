package ru.practicum.manage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.practicum.model.Status.NEW;

public class InMemoryHistoryManagerTest {

    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

    @Test
    public void add() {
        Task task1 = new Task(1, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now());
        Task task2 = new Task(2, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now());
        Task task3 = new Task(3, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now());
        inMemoryHistoryManager.add(task1);
        inMemoryHistoryManager.add(task2);
        inMemoryHistoryManager.add(task3);
        assertNotNull(inMemoryHistoryManager.getHistory());
        assertEquals(List.of(task1, task2, task3), inMemoryHistoryManager.getHistory());
        assertNotNull(inMemoryHistoryManager.getHistory());
    }

    @Test
    public void getHistoryWithRepeatedTasks() {
        Task task1 = new Task(1, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now());
        Task task2 = new Task(2, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now());
        Task task3 = new Task(3, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now());
        inMemoryHistoryManager.add(task1);
        inMemoryHistoryManager.add(task2);
        inMemoryHistoryManager.add(task3);
        inMemoryHistoryManager.add(task1);
        inMemoryHistoryManager.add(task2);
        inMemoryHistoryManager.add(task3);
        List<Task> list = List.of(task1, task2, task3);
        assertNotNull(inMemoryHistoryManager.getHistory());
        assertEquals(list, inMemoryHistoryManager.getHistory());
        assertEquals(3, inMemoryHistoryManager.getHistory().size());

    }

    @Test
    public void remove1() {
        Task task1 = new Task(1, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now());
        Task task2 = new Task(2, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now());
        Task task3 = new Task(3, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now());
        inMemoryHistoryManager.add(task1);
        inMemoryHistoryManager.add(task2);
        inMemoryHistoryManager.add(task3);

        inMemoryHistoryManager.remove(1);

        assertEquals(List.of(task2, task3), inMemoryHistoryManager.getHistory());
        assertNotNull(inMemoryHistoryManager.getHistory());
        assertEquals(2, inMemoryHistoryManager.getHistory().size());
    }

    @Test
    public void remove2() {
        Task task1 = new Task(1, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now());
        Task task2 = new Task(2, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now());
        Task task3 = new Task(3, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now());
        inMemoryHistoryManager.add(task1);
        inMemoryHistoryManager.add(task2);
        inMemoryHistoryManager.add(task3);

        inMemoryHistoryManager.remove(2);

        assertEquals(List.of(task1, task3), inMemoryHistoryManager.getHistory());
        assertNotNull(inMemoryHistoryManager.getHistory());
        assertEquals(2, inMemoryHistoryManager.getHistory().size());
    }

    @Test
    public void remove3() {
        Task task1 = new Task(1, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now());
        Task task2 = new Task(2, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now());
        Task task3 = new Task(3, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now());
        inMemoryHistoryManager.add(task1);
        inMemoryHistoryManager.add(task2);
        inMemoryHistoryManager.add(task3);

        inMemoryHistoryManager.remove(3);

        assertEquals(List.of(task1, task2), inMemoryHistoryManager.getHistory());
        assertNotNull(inMemoryHistoryManager.getHistory());
        assertEquals(2, inMemoryHistoryManager.getHistory().size());
    }
}