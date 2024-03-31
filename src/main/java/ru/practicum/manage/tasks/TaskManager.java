package ru.practicum.manage.tasks;

import ru.practicum.model.Epic;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.util.ArrayList;
import java.util.List;

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

    //Дополнительно
    List<Subtask> getListSubtaskIdEpic(Integer id);

    default TaskManager getDefault() {
        return null;
    }

    default void statusEpic(Epic epic) {

    }

    default void setEpicDuration(Epic epic) {
    }

    default void validate(Task task) {
    }

    //методы из класса HistoryManager
    default void add(int id) {
    }

    default void remove(Integer id) {

    }

    default ArrayList<Task> getHistory() {
        return new ArrayList<>();
    }

    //методы из класса prioritized

    default List<Task> getPrioritizedTasks() {
        return null;
    }

    default void prioritizedAdd(Task task) {

    }

    default void prioritizedRemove(Task task) {

    }

    default void prioritizedClear() {

    }
}