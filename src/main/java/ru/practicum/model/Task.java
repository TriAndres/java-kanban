package ru.practicum.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


public class Task implements Tasks {
    protected Integer id;
    protected String name;
    protected String description;
    protected Status status;
    protected LocalDateTime startTime;
    protected Long duration;

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public Task(String description) {
        this.description = description;
        this.id = getId();
        this.status = Status.NEW;
        setName("Задача");
    }

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(Integer id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(Integer id, String name, String description, Status status, LocalDateTime startTime, Long duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(Task task) {
        this.id = task.id;
        this.name = task.name;
        this.description = task.description;
        this.status = task.status;
        this.startTime = task.startTime;
        this.duration = task.duration;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }
    @Override
    public LocalDateTime getStartTime() {
        return startTime;
    }
    @Override
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    @Override
    public void setDuration(Long duration) {
        this.duration = duration;
    }
    @Override
    public LocalDateTime getEndTime() {
        long MINUTE = 60L;
        return startTime.plusSeconds(duration * MINUTE);
    }
    @Override
    public Long getDuration() {
        return duration;
    }

    @Override
    public TaskType getType() {
        return TaskType.TASK;
    }

    //@Override
//    public String toString() {
//        return getType() +
//                "id=" + id +
//                ", name=" + name +
//                ", description=" + description +
//                ", status=" + status +
//                ", duration=" + duration +
//                ", startTime=" + startTime.format(formatter) +
//                ", endTime=" + getEndTime().format(formatter) + "\n";
//    }

    @Override
    public String toString() {
        return getType() + ", id=" + id + ", name=" + name + ", description=" + description + ", status=" + status + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status && Objects.equals(startTime, task.startTime) && Objects.equals(duration, task.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status, startTime, duration);
    }
}