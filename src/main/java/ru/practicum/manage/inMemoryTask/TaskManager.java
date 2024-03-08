package ru.practicum.manage.inMemoryTask;

import ru.practicum.model.Epic;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.util.ArrayList;
import java.util.Map;

public interface TaskManager {

    // a. Получение списка всех задач.
    ArrayList<Task> getTasks();
    ArrayList<Epic> getEpics();
    ArrayList<Subtask> getSubtasks();

    // b. Удаление всех задач.
    void deleteAllTask();
    void deleteAllEpic();
    void deleteAllSubtask();

    // c. Получение по идентификатору.
    Task getTaskId(Integer taskId);
    Epic getEpicId(Integer epicId);
    Subtask getSubtaskId(Integer subtaskId);

    // d. Создание. Сам объект должен передаваться в качестве параметра.
    void addNewTask(Task task);
    void addNewEpic(Epic epic);
    void addNewSubtask(Subtask subtask);

    // e. Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    void updateTask(Task task);
    void updateEpic(Epic epic);
    void updateSubtask(Subtask subtask);

    // f. Удаление по идентификатору.
    void deleteIdTask(Integer id);
    void deleteIdEpic(Integer id);
    void deleteIdSubtask(Integer id);
}