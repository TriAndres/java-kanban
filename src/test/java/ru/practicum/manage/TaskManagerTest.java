package ru.practicum.manage;

import ru.practicum.manage.tasks.TaskManager;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T manager;
}