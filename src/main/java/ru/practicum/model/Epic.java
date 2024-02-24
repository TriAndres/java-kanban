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

    public Epic(Integer id, String description) {
        super(id, description);
        this.id = getId();
        subtasks = new ArrayList<>();
        setTitle("Эпик");
    }

    public Epic(String title, String description, Status status) {
        super(title,description,status);
        this.id = getId();
    }

    public Epic(Integer id, String title, String description, Status status) {
        super(id, title, description, status);
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

}