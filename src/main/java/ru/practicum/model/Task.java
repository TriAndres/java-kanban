package ru.practicum.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;


public class Task {
    protected Integer id;
    protected String name;
    protected String description;
    protected Status status;
    protected Duration duration;
    protected LocalDateTime startTime;
    protected TaskType taskType;

    protected final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public Task(String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
        setTaskType(TaskType.TASK);
    }

    public Task(Integer id, String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
        taskType = getTaskType();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    @Override
    public String toString() {
        return getTaskType() +
                ", id=" + id +
                ", name=" + name +
                ", description=" + description +
                ", status=" + status +
                ", duration=" + duration.toMinutes() +
                ", startTime=" + startTime.format(formatter) +
                ", endTime=" + getEndTime().format(formatter) + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status && Objects.equals(duration, task.duration) && Objects.equals(startTime, task.startTime) && Objects.equals(formatter, task.formatter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status, duration, startTime, formatter);
    }

    //Epic
    public ArrayList<Integer> getSubtasksId() {
        return null;
    }

    public void addSubtask(int id) {
    }

    public void setEndTime(LocalDateTime endTime) {
    }

    //Subtask
    public Integer getEpicId() {
        return null;
    }

    public void setEpicId(Integer idEpic) {
    }
}