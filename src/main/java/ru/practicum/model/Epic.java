package ru.practicum.model;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subtasksId = new ArrayList<>();

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
    }


    @Override
    public ArrayList<Integer> getSubtasksId() {
        return subtasksId;
    }

    @Override
    public void addSubtask(int id) {
        subtasksId.add(id);
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