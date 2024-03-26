package ru.practicum.manage;

import ru.practicum.manage.history.HistoryManager;
import ru.practicum.manage.history.InMemoryHistoryManager;
import ru.practicum.manage.tasks.InMemoryTaskManager;
import ru.practicum.manage.tasks.TaskManager;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}