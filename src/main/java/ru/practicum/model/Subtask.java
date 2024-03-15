package ru.practicum.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    private Integer idEpic;

    public Subtask(String description, Integer idEpic) {
        super(description);
        this.idEpic = idEpic;
        setName("Подзадача");
    }

    public Subtask(String name, String description, Status status, Integer idEpic) {
        super(name, description, status);
        this.idEpic = idEpic;
    }

    public Subtask(Integer id, String name, String description, Status status, Integer idEpic) {
        super(id, name, description, status);
        this.idEpic = idEpic;
    }

    public Subtask(Integer id, String name, String description, Status status, Integer idEpic, LocalDateTime startTime, Long duration) {
        super(id, name, description, status, startTime, duration);
        this.idEpic = idEpic;
    }

    public Subtask(Subtask subtask) {
        super(subtask);
        this.idEpic = subtask.idEpic;
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

    //        @Override
//    public String toString() {
//        return getType() +
//                "id=" + id +
//                ", name=" + name +
//                ", description=" + description +
//                ", status=" + status +
//                ", idEpic = " + idEpic +
//                ", duration=" + duration +
//                ", startTime=" + startTime.format(formatter) +
//                ", endTime=" + getEndTime().format(formatter) + "\n";
//
//    }
    @Override
    public String toString() {
        return getType() + ", id=" + id + ", name=" + name + ", description=" + description + ", status=" + status + ", epicId=" + idEpic + "\n";
    }
}