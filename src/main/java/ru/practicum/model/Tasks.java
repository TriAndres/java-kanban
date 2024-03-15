package ru.practicum.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface Tasks {
    //Task
    default Integer getId() {
        return null;
    }

    default void setId(Integer id) {
    }

    default String getName() {
        return null;
    }

    default void setName(String name) {
    }

    default String getDescription() {
        return null;
    }

    default void setDescription(String description) {
    }

    default Status getStatus() {
        return null;
    }

    default void setStatus(Status status) {
    }

    default LocalDateTime getStartTime() {
        return null;
    }

    default void setStartTime(LocalDateTime startTime) {
    }

    default void setDuration(Long duration) {
    }

    default LocalDateTime getEndTime() {
        return null;
    }

    default Long getDuration() {
        return null;
    }

    default TaskType getType() {
        return null;
    }

    //Epic
    default ArrayList<Integer> getSubtasksId() {
        return null;
    }

    default void addSubtask(int id) {
    }

    default void setEndTime(LocalDateTime endTime) {
    }

    //Subtask
    default Integer getEpicId() {
        return null;
    }

    default void setEpicId(Integer idEpic) {
    }
}