package ru.practicum.model;

import java.util.Objects;

public class Task {
    protected Integer id;
    protected String title;
    protected String description;
    protected Status status;

    public Task(String description) {
        this.description = description;
        this.id = getId();
        this.status = Status.NEW;
        setTitle("Задача");
    }

    public Task(Integer id, String description) {
        this.id = id;
        this.description = description;
        this.status = Status.NEW;
        setTitle("Задача");
    }

    public Task(String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.id = getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Task task = (Task) object;
        return Objects.equals(id, task.id) && Objects.equals(title, task.title) && Objects.equals(description, task.description) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status);
    }

    @Override
    public String toString() {
        return title + ":" + description + ", status: " + status;
    }
}