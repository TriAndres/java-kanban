package ru.practicum.manage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.model.Task;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.practicum.model.Status.NEW;

class InMemoryHistoryManagerTest {
    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

    @BeforeEach
    public void beforeEach() {
        inMemoryHistoryManager.add(new Task(1, "1", "Задача", NEW));
        inMemoryHistoryManager.add(new Task(2, "2", "Задача", NEW));
        inMemoryHistoryManager.add(new Task(3, "3", "Задача", NEW));
    }

    @Test
    public void add() {
        int sizeList = inMemoryHistoryManager.getHistory().size();

        assertTrue(sizeList > 0);

        inMemoryHistoryManager.add(new Task(4, "4", "Задача", NEW));

        assertTrue(sizeList < inMemoryHistoryManager.getHistory().size());

    }

    @Test
    public void remove() {
        int sizeList = inMemoryHistoryManager.getHistory().size();

        assertTrue(sizeList > 0);

        inMemoryHistoryManager.remove(sizeList);

        assertTrue(sizeList > inMemoryHistoryManager.getHistory().size());
    }

    @Test
    public void getHistory() {
        List<Task> listOne = inMemoryHistoryManager.getHistory();

        List<Task> listTwo = new ArrayList<>(List.of(
                new Task(1, "1", "Задача", NEW),
                new Task(2, "2", "Задача", NEW),
                new Task(3, "3", "Задача", NEW)
        ));

        assertIterableEquals(listOne, listTwo);

        inMemoryHistoryManager.add(new Task(3, "3", "Задача Задача", NEW));

        List<Task> listThree = inMemoryHistoryManager.getHistory();

        List<Task> listFour = new ArrayList<>(List.of(
                new Task(1, "1", "Задача", NEW),
                new Task(2, "2", "Задача", NEW),
                new Task(3, "3", "Задача Задача", NEW)
        ));

        assertIterableEquals(listThree, listFour);
    }
}