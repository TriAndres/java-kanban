package ru.practicum.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    private Integer idEpic;

    public Subtask(String name, String description, Status status, Duration duration, LocalDateTime startTime, Integer idEpic) {
        super(name, description, status,  duration, startTime);
        this.idEpic = idEpic;
        setTaskType(TaskType.SUBTASK);
    }

    public Subtask(Integer id, String name, String description, Status status, Duration duration, LocalDateTime startTime, Integer idEpic) {
        super(id, name, description, status,  duration, startTime);
        this.idEpic = idEpic;
    }

    public Integer getEpicId() {
        return idEpic;
    }

    public void setEpicId(Integer idEpic) {
        this.idEpic = idEpic;
    }

    public TaskType getTaskType() {
        return TaskType.SUBTASK;
    }
}