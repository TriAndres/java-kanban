package ru.practicum.model;

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

    default TaskType getType() {
        return null;
    }

    ;

    //Epic
    default ArrayList<Subtask> getSubtasks() {
        return null;
    }

    default void setSubtasks(ArrayList<Subtask> subtasks) {
    }

    default void addSubtask(Subtask subtask) {
    }

    default void removeSubtask(Subtask subtask) {
    }

    default void clearSubtask() {
    }

    //Subtask
    default Integer getIdEpic() {
        return null;
    }

    default void setIdEpic(Integer idEpic) {
    }
}