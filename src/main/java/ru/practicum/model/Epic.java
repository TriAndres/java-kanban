package ru.practicum.model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks;

    public Epic(String description) {
        super(description);
        this.id = getId();
        subtasks = new ArrayList<>();
        setTitle("Эпик");
    }

    public Epic(Integer id, String title, String description, Status status) {
        super(id, title, description, status);
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
    public String toString() {
        return TaskType.EPIC + ", id=" + id + ", name=" + title + ", description=" + description + ", status=" + status + "\n";
    }
}