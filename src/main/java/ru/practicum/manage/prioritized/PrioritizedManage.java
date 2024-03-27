package ru.practicum.manage.prioritized;

import ru.practicum.model.Task;

import java.util.List;

public interface PrioritizedManage {
    void prioritizedAdd(Task task);

    void prioritizedRemove(int id);

    List<Task> getPrioritizedList();
}
