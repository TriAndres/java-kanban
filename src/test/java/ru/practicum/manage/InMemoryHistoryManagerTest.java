package ru.practicum.manage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.practicum.model.Status.NEW;

class InMemoryHistoryManagerTest {

    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

    @BeforeEach
    public void beforeEach() {
        inMemoryHistoryManager.add(new Task(1, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now()));
        inMemoryHistoryManager.add(new Task(2, "2", "Задача", NEW, Duration.ZERO, LocalDateTime.now()));
        inMemoryHistoryManager.add(new Task(3, "3", "Задача", NEW, Duration.ZERO, LocalDateTime.now()));
    }

    @Test
    public void add() {
        assertNotNull(inMemoryHistoryManager.getHistory());

        int sizeList = inMemoryHistoryManager.getHistory().size();

        assertTrue(sizeList > 0);

        inMemoryHistoryManager.add(new Task(4, "4", "Задача", NEW, Duration.ZERO, LocalDateTime.now()));

        assertTrue(sizeList < inMemoryHistoryManager.getHistory().size());

    }

    @Test
    public void remove() {
        assertNotNull(inMemoryHistoryManager.getHistory());

        int sizeList = inMemoryHistoryManager.getHistory().size();

        assertTrue(sizeList > 0);

        inMemoryHistoryManager.remove(sizeList);

        assertTrue(sizeList > inMemoryHistoryManager.getHistory().size());
    }

    @Test
    public void getHistory() {
        Task task1 = new Task(1, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now());
        Task task2 = new Task(2, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now());
        Task task3 = new Task(3, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now());
        inMemoryHistoryManager.add(task1);
        inMemoryHistoryManager.add(task2);
        inMemoryHistoryManager.add(task3);
        List<Task> listOne = inMemoryHistoryManager.getHistory();

        List<Task> listTwo = new ArrayList<>(List.of(
                task1,
                task2,
                task3
        ));
        assertNotNull(listOne);
        assertNotNull(listTwo);
        assertIterableEquals(listOne, listTwo);
    }

    @Test
    public void removeHistory() {
        Task task1 = new Task(1, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now());
        Task task2 = new Task(2, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now());
        Task task3 = new Task(3, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now());
        inMemoryHistoryManager.add(task1);
        inMemoryHistoryManager.add(task2);
        inMemoryHistoryManager.add(task3);
        List<Task> listOne = inMemoryHistoryManager.getHistory();

        assertNotNull(listOne);
        inMemoryHistoryManager.remove(task1.getId());
        inMemoryHistoryManager.remove(task2.getId());
        inMemoryHistoryManager.remove(task3.getId());
        assertIterableEquals(Collections.EMPTY_LIST, inMemoryHistoryManager.getHistory());
    }

}