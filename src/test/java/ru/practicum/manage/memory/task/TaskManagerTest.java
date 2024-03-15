package ru.practicum.manage.memory.task;

import org.junit.jupiter.api.Test;

public abstract class TaskManagerTest<T extends TaskManager>{
    protected T manager;
    @Test
    public abstract void addNewTask();
}