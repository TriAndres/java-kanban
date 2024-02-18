package ru.practicum.manage;

import org.junit.jupiter.api.Test;
import ru.practicum.model.Task;

import java.util.ArrayList;
import java.util.List;

import static ru.practicum.model.Status.NEW;

public class InMemoryHistoryManagerTest {
    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
    List<Task> list = new ArrayList<>(List.of(
            new Task(1,"1", "Задача", NEW),
            new Task(2,"2", "Задача", NEW),
            new Task(3,"3", "Задача", NEW)
    ));

    @Test
    public void add() {

    }

    @Test
    public void getHistory() {
    }
}
