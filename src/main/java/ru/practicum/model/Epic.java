package ru.practicum.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks = new ArrayList<>();

    public Epic(String description) {
        super(description);
        this.id = getId();
        setName("Эпик");
    }

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    public Epic(Integer id, String name, String description, Status status) {
        super(id, name, description, status);
    }

    public Epic(String name, String description, Status status, long duration, LocalDateTime startTime) {
        super(name, description, status, duration, startTime);
    }

    public Epic(Integer id, String name, String description, Status status, long duration, LocalDateTime startTime) {
        super(id, name, description, status, duration, startTime);
    }

    public Epic(Epic epic) {
        super(epic);
        this.subtasks = epic.subtasks;
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    @Override
    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    @Override
    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    @Override
    public void clearSubtask() {
        subtasks.clear();
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }

    @Override
    public String toString() {
        return getType() + ", id=" + id + ", name=" + name + ", description=" + description + ", status=" + status + "\n";
    }
}