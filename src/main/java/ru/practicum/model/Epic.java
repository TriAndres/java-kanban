package ru.practicum.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private  ArrayList<Integer> subtasksId = new ArrayList<>();
    private transient LocalDateTime endTime;

    public Epic(String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        super(name, description, status, duration, startTime);
        this.endTime = super.getEndTime();
    }

    public Epic(Integer id, String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        super(id, name, description, status, duration, startTime);
        this.endTime = super.getEndTime();
    }

    @Override
    public ArrayList<Integer> getSubtasksId() {
        return subtasksId;
    }

    public void setSubtasksId(ArrayList<Integer> subtasksId) {
        this.subtasksId = subtasksId;
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
}