package ru.practicum.manage;

import ru.practicum.model.Epic;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    ArrayList<Task> getTasks();
    ArrayList<Epic> getEpics();
    ArrayList<Subtask> getSubtasks();
    void deleteTask();
    void deleteEpic();
    void deleteSubtask();
    Task getTask(Integer tascId);
    Epic getEpic(Integer epicId);
    Subtask getSubtask(Integer subtaskId);
    Task addNewTask(Task task);
    Epic addNewEpic(Epic epic);
    Subtask addNewSubtask(Subtask subtask);
    void updateTask(Task task);
    void updateEpic(Epic epic);
    void updateSubtask(Subtask subtask);
    void deleteIdTask(Integer id);
    void deleteIdEpic(Integer id);
    void deleteIdSubtask(Integer id);
    List<Task> getHistory();

}