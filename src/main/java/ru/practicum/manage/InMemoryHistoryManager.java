package ru.practicum.manage;

import ru.practicum.model.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private final ArrayList<Task> taskArrayList;

    public InMemoryHistoryManager() {
        taskArrayList = new ArrayList<>();
    }

    @Override
    public void add(Task task) {
        if (task != null) {
            taskArrayList.add(task);
            if (taskArrayList.size() > 10) {
                taskArrayList.remove(0);
            }
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return taskArrayList;
    }
}
