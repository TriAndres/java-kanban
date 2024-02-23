package ru.practicum.manage;

import ru.practicum.exseption.ManagerSaveException;
import ru.practicum.model.Epic;
import ru.practicum.model.Task;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static ru.practicum.manage.TaskType.*;


public class InMemoryTaskManager implements TaskManager {
    protected static final Map<Integer, Task> taskMap = new HashMap<>();
    protected static final Map<Integer, Epic> epicMap = new HashMap<>();
    protected static final Map<Integer, Subtask> subtaskMap = new HashMap<>();

    private List<Task> fileList = new ArrayList<>();
    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        historyManager = Managers.getDefaultHistory();
    }

    public List<Task> getFileList() {
        return fileList;
    }

    public void setFileList(List<Task> fileList) {
        this.fileList = fileList;
    }


    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> list = new ArrayList<>();
        for (Integer key : taskMap.keySet()) {
            Task task = taskMap.get(key);
            list.add(task);
        }
        return list;
    }

    @Override
    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> list = new ArrayList<>();
        for (Integer key : epicMap.keySet()) {
            Epic epic = epicMap.get(key);
            list.add(epic);
        }
        return list;
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        ArrayList<Subtask> list = new ArrayList<>();
        for (Integer key : subtaskMap.keySet()) {
            Subtask subtask = subtaskMap.get(key);
            list.add(subtask);
        }
        return list;
    }

    @Override
    public void deleteTask() {
        taskMap.clear();
    }

    @Override
    public void deleteEpic() {
        epicMap.clear();
        subtaskMap.clear();

    }

    @Override
    public void deleteSubtask() {
        subtaskMap.clear();
        for (Epic value : epicMap.values()) {
            value.getSubtasks().clear();
            statusEpic(value);
        }
    }

    @Override
    public Task getTask(Integer taskId) {
        add(taskMap.get(taskId));
        return taskMap.get(taskId);
    }

    public Epic getEpic(Integer epicId) {
        add(epicMap.get(epicId));
        return epicMap.get(epicId);
    }

    @Override
    public Subtask getSubtask(Integer subtaskId) {
        add(subtaskMap.get(subtaskId));
        return subtaskMap.get(subtaskId);
    }

    @Override
    public void addNewTask(Task task) {
        Integer id = taskMap.size() + 1;
        taskMap.put(id, task);
        task.setId(id);
        fileList.add(task);
    }

    @Override
    public void addNewEpic(Epic epic) {
        Integer id = epicMap.size() + 1;
        epicMap.put(id, epic);
        epic.setId(id);
        fileList.add(epic);
    }

    @Override
    public void addNewSubtask(Subtask subtask) {
        Integer id = subtaskMap.size() + 1;
        subtaskMap.put(id, subtask);
        subtask.setId(id);
        Integer idEpic = subtask.getIdEpic();
        Epic epic = epicMap.get(idEpic);
        epic.add(subtask);
        statusEpic(epic);
        fileList.add(subtask);
    }

    @Override
    public void updateTask(Task task) {
        if (taskMap.containsKey(task.getId())) {
            taskMap.put(task.getId(), task);
        } else {
            System.err.println("Такого ключа нет");
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epicMap.containsKey(epic.getId())) {
            epicMap.put(epic.getId(), epic);
        } else {
            System.err.println("Такого ключа нет");
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtaskMap.containsKey(subtask.getId())) {
            subtaskMap.put(subtask.getId(), subtask);
            Integer idEpic = subtask.getIdEpic();
            Epic epic = epicMap.get(idEpic);
            ArrayList<Subtask> arrayList = epic.getSubtasks();
            arrayList.set(subtask.getId(), subtask);
            statusEpic(epic);
        } else {
            System.err.println("Такого ключа нет");
        }
    }

    @Override
    public void deleteIdTask(Integer id) {
        taskMap.remove(id);
        remove(id);
    }

    @Override
    public void deleteIdEpic(Integer id) {
        Epic epic = epicMap.get(id);
        for (Subtask subtask : epic.getSubtasks()) {
            subtaskMap.remove(subtask.getId());
        }
        epicMap.remove(id);
        remove(id);
    }

    @Override
    public void deleteIdSubtask(Integer id) {
        Subtask subtask = subtaskMap.get(id);
        Epic epic = epicMap.get(subtask.getIdEpic());
        epic.getSubtasks().remove(subtask);
        statusEpic(epic);
        subtaskMap.remove(id);
        remove(id);
    }

    ///////////////////////////////////////////////////////////////////////
    //методы из класса HistoryManager
    public void add(Task task) {
        historyManager.add(task);
    }

    public void remove(int id) {
        historyManager.remove(id);
    }

    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    ////////////////////////////////////////////////////////////////////////////
    //Дополнительные методы:
    public ArrayList<Subtask> getListSubtaskIdEpic(Integer id) {
        Epic epic = epicMap.get(id);
        return epic.getSubtasks();
    }

    public void statusEpic(Epic epic) {
        ArrayList<Subtask> list = epic.getSubtasks();
        int count1 = 0;
        int count2 = 0;
        for (Subtask subtask : list) {
            if (subtask.getStatus().equals(Status.NEW)) {
                count1++;
                if (count1 == list.size()) {
                    epic.setStatus(Status.NEW);
                }
            } else if (subtask.getStatus().equals(Status.DONE)) {
                count2++;
                if (count2 == list.size()) {
                    epic.setStatus(Status.DONE);
                }
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }
        Task task = taskMap.get(epic.getId());
        if (epic.getStatus().equals(Status.NEW)) {
            task.setStatus(Status.NEW);
        } else if (epic.getStatus().equals(Status.IN_PROGRESS)) {
            task.setStatus(Status.IN_PROGRESS);
        } else if (epic.getStatus().equals(Status.DONE)) {
            task.setStatus(Status.DONE);
        }
    }
}