package ru.practicum.manage;

import ru.practicum.model.Epic;
import ru.practicum.model.Task;
import ru.practicum.model.Subtask;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    // a. Получение списка всех задач.
    ArrayList<Task> getTasks();
    ArrayList<Epic> getEpics();
    ArrayList<Subtask> getSubtasks();

    // b. Удаление всех задач.
    void deleteTask();
    void deleteEpic();
    void deleteSubtask();

    // c. Получение по идентификатору.
    Task getTask(Integer tascId);
    Epic getEpic(Integer epicId);
    Subtask getSubtask(Integer subtaskId);

    // d. Создание. Сам объект должен передаваться в качестве параметра.
    Task addNewTask(Task task);
    Epic addNewEpic(Epic epic);
    Subtask addNewSubtask(Subtask subtask);

    // e. Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    void updateTask(Task task);
    void updateEpic(Epic epic);
    void updateSubtask(Subtask subtask);

    // f. Удаление по идентификатору.
    void deleteIdTask(Integer id);
    void deleteIdEpic(Integer id);
    void deleteIdSubtask(Integer id);

}