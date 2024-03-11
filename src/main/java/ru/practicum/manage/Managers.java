package ru.practicum.manage;

import ru.practicum.manage.memory.history.HistoryManager;
import ru.practicum.manage.memory.history.InMemoryHistoryManager;
import ru.practicum.manage.memory.task.InMemoryTaskManager;
import ru.practicum.manage.memory.task.TaskManager;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}