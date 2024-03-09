package ru.practicum.model;

import java.util.ArrayList;

public interface Tasks {
    //Task
    default Integer getId() {
        return null;
    }

    default void setId(Integer id) {
    }

    default String getTitle() {
        return null;
    }

    default void setTitle(String title) {
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

    //Epic
    default ArrayList<Subtask> getSubtasks() {
        return null;
    }

    default void addSubtask(Subtask subtask) {
    }

    default void removeSubtask(Subtask subtask) {
    }

    //Subtask
    default Integer getIdEpic() {
        return null;
    }

    default void setIdEpic(Integer idEpic) {
    }
}