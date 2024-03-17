package ru.practicum.manage;

import ru.practicum.manage.memory.HistoryManager;
import ru.practicum.manage.memory.InMemoryHistoryManager;
import ru.practicum.manage.memory.InMemoryTaskManager;
import ru.practicum.manage.memory.TaskManager;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}