package ru.practicum.model;

import java.time.LocalDateTime;

public class Subtask extends Task {
    private Integer idEpic;

    public Subtask(String name, String description, Status status) {
        super(name, description, status);
    }

    public Subtask(String name, String description, Status status, LocalDateTime startTime, Long duration, Integer idEpic) {
        super(name, description, status, startTime, duration);
        this.idEpic = idEpic;
    }

    public Subtask(Integer id, String name, String description, Status status, LocalDateTime startTime, Long duration, Integer idEpic) {
        super(id, name, description, status, startTime, duration);
        this.idEpic = idEpic;
    }

    @Override
    public Integer getEpicId() {
        return idEpic;
    }

    @Override
    public void setEpicId(Integer idEpic) {
        this.idEpic = idEpic;
    }

    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
    }

            @Override
    public String toString() {
        return getType() +
                ", id=" + id +
                ", name=" + name +
                ", description=" + description +
                ", status=" + status +
                ", duration=" + duration +
                ", startTime=" + startTime.format(formatter) +
                ", endTime=" + getEndTime().format(formatter) +
                ", idEpic = " + idEpic + "\n";

    }
}