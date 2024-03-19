package ru.practicum.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subtasksId = new ArrayList<>();
    private transient LocalDateTime endTime;

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);

    }

    public Epic(String name, String description, Status status, LocalDateTime startTime, Long duration) {
        super(name, description, status, startTime, duration);
        this.endTime = super.getEndTime();
    }

    public Epic(Integer id, String name, String description, Status status, LocalDateTime startTime, Long duration) {
        super(id, name, description, status, startTime, duration);
        this.endTime = super.getEndTime();
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
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }

    @Override
    public String toString() {
        return getType() +
                ", id=" + getId() +
                ", name=" + getName() +
                ", description=" + getDescription() +
                ", status=" + getStatus() +
                ", duration=" + getDuration() +
                ", startTime=" + getStartTime().format(formatter) +
                ", endTime=" + getEndTime().format(formatter) + "\n";
    }
}