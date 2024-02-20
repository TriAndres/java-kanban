package ru.practicum.manage;

import ru.practicum.model.Task;

import java.util.Objects;

public class Node {
    protected Task task;
    protected Node nextTask;
    protected Node prevTask;

    public Node(Task task, Node prevTask, Node nextTask) {
        this.task = task;
        this.nextTask = nextTask;
        this.prevTask = prevTask;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Node node = (Node) object;
        return Objects.equals(task, node.task) && Objects.equals(nextTask, node.nextTask) && Objects.equals(prevTask, node.prevTask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(task, nextTask, prevTask);
    }
}
