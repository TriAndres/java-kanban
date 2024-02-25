package ru.practicum.manage.history;

import ru.practicum.model.Task;

public class Node {
    protected Task task;
    protected Node nextTask;
    protected Node prevTask;

    public Node(Task task, Node prevTask, Node nextTask) {
        this.task = task;
        this.nextTask = nextTask;
        this.prevTask = prevTask;
    }
}