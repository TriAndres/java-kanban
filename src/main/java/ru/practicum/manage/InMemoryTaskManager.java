package ru.practicum.manage;

import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryTaskManager implements TaskManager {
    protected static final Map<Integer, Task> taskMap = new HashMap<>();
    protected static final Map<Integer, Epic> epicMap = new HashMap<>();
    protected static final Map<Integer, Subtask> subtaskMap = new HashMap<>();
    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        historyManager = Managers.getDefaultHistory();
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> list = new ArrayList<>();
        if (!taskMap.isEmpty()) {
            for (Integer key : taskMap.keySet()) {
                Task task = taskMap.get(key);
                list.add(task);
            }
        }
        return list;
    }

    @Override
    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> list = new ArrayList<>();
        if (!epicMap.isEmpty()) {
            for (Integer key : epicMap.keySet()) {
                Epic epic = epicMap.get(key);
                list.add(epic);
            }
        }
        return list;
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        ArrayList<Subtask> list = new ArrayList<>();
        if (!subtaskMap.isEmpty()) {
            for (Integer key : subtaskMap.keySet()) {
                Subtask subtask = subtaskMap.get(key);
                list.add(subtask);
            }
        }
        return list;
    }

    @Override
    public void deleteTask() {
        if (!taskMap.isEmpty()) {
            taskMap.clear();
        }
    }

    @Override
    public void deleteEpic() {
        if (!epicMap.isEmpty()) {
            epicMap.clear();
            if (!subtaskMap.isEmpty()) {
                subtaskMap.clear();
            }
        }
    }

    @Override
    public void deleteSubtask() {
        if (!subtaskMap.isEmpty()) {
            subtaskMap.clear();
        }
        if (!epicMap.isEmpty()) {
            for (Epic value : epicMap.values()) {
                if (!value.getSubtasks().isEmpty()) {
                    value.getSubtasks().clear();
                    statusEpic(value);
                }
            }
        }
    }

    @Override
    public Task getTask(Integer taskId) {
        Task task = null;
        if (taskMap.containsKey(taskId)) {
            task = taskMap.get(taskId);
            add(taskMap.get(task.getId()));
        }
        return task;
    }

    @Override
    public Epic getEpic(Integer epicId) {
        Epic epic = null;
        if (epicMap.containsKey(epicId)) {
            epic = epicMap.get(epicId);
            add(epicMap.get(epic.getId()));
        }
        return epic;
    }

    @Override
    public Subtask getSubtask(Integer subtaskId) {
        Subtask subtask = null;
        if (subtaskMap.containsKey(subtaskId)) {
            subtask = subtaskMap.get(subtaskId);
            add(subtaskMap.get(subtask.getId()));
        }
        return subtask;
    }

    @Override
    public void addNewTask(Task task) {
        if (task != null) {
            Integer id = taskMap.size() + 1;
            if (!taskMap.containsKey(id)) {
                taskMap.put(id, task);
                task.setId(id);
            }
        }
    }

    @Override
    public void addNewEpic(Epic epic) {
        if (epic != null) {
            Integer id = epicMap.size() + 1;
            if (!epicMap.containsKey(id)) {
                epicMap.put(id,epic);
                epic.setId(id);
            }
        }
    }

    @Override
    public void addNewSubtask(Subtask subtask) {
        if (subtask != null) {
            Integer id = subtaskMap.size() + 1;
            if (!subtaskMap.containsKey(id)) {
                subtaskMap.put(id, subtask);
                subtask.setId(id);
                Integer idEpic = subtask.getIdEpic();
                if (epicMap.containsKey(idEpic)) {
                    Epic epic = epicMap.get(idEpic);
                    epic.addSubtask(subtask);
                    statusEpic(epic);
                }
            }
        }
    }

    @Override
    public void updateTask(Task task) {
        if (task != null) {
            if (task.getId() != null) {
                taskMap.put(task.getId(), task);
            }
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
        if (subtask != null) {
            if (subtask.getId() != null) {
                subtaskMap.put(subtask.getId(), subtask);
                Integer idEpic = subtask.getIdEpic();
                if (epicMap.containsKey(idEpic)) {
                    Epic epic = epicMap.get(idEpic);
                    List<Subtask> subtaskList = epic.getSubtasks();
                    if (subtaskList.contains(subtask)) {
                        subtaskList.set(subtask.getId(), subtask);
                        statusEpic(epic);
                    }
                }
            }
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