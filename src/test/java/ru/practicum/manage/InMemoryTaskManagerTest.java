package ru.practicum.manage;

import org.junit.jupiter.api.BeforeEach;
import ru.practicum.manage.tasks.InMemoryTaskManager;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    public void beforeEach() {
        manager = new InMemoryTaskManager();
    }
}
