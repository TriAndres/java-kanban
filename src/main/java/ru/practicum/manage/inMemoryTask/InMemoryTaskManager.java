package ru.practicum.manage.inMemoryTask;

import ru.practicum.manage.Managers;
import ru.practicum.manage.history.HistoryManager;
import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryTaskManager implements TaskManager {
    protected  static final Map<Integer, Task> taskMap = new HashMap<>();
    protected static final Map<Integer, Epic> epicMap = new HashMap<>();
    protected static final Map<Integer, Subtask> subtaskMap = new HashMap<>();
    protected static final HistoryManager historyManager = Managers.getDefaultHistory();

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
                epicMap.put(id, epic);
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
                    ArrayList<Subtask> subtasks = epic.getSubtasks();
                    subtasks.add(subtask);
                    statusEpic(epic);
                }
            }
        }
    }

    @Override
    public void deleteAllTask() {
        if (!taskMap.isEmpty()) {
            for (Integer key : taskMap.keySet()) {
                remove(key);
            }
            taskMap.clear();
        }
    }

    @Override
    public void deleteAllEpic() {
        if (!epicMap.isEmpty()) {
            if (!subtaskMap.isEmpty()) {
                subtaskMap.clear();
            }
            for (Integer key : epicMap.keySet()) {
                remove(key);
            }
            epicMap.clear();
        }
    }

    @Override
    public void deleteAllSubtask() {
        if (!epicMap.isEmpty()) {
            for (Epic value : epicMap.values()) {
                if (!value.getSubtasks().isEmpty()) {
                    value.getSubtasks().clear();
                    statusEpic(value);
                }
            }
            subtaskMap.clear();
        }
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
    public Task getTaskId(Integer taskId) {
        if (taskMap.containsKey(taskId)) {
            Task task = taskMap.get(taskId);
            add(taskMap.get(task.getId()));
            return task;
        }
        return null;
    }

    @Override
    public Epic getEpicId(Integer epicId) {
        if (epicMap.containsKey(epicId)) {
            Epic epic = epicMap.get(epicId);
            add(epicMap.get(epic.getId()));
            return epic;
        }
        return null;
    }

    @Override
    public Subtask getSubtaskId(Integer subtaskId) {
        if (subtaskMap.containsKey(subtaskId)) {
            Subtask subtask = subtaskMap.get(subtaskId);
            add(subtaskMap.get(subtask.getId()));
            return subtask;
        }
        return null;
    }

    @Override
    public void updateTask(Task task) {
        if (task != null) {
            if (task.getId() != null) {
                remove(task.getId());
                taskMap.put(task.getId(), task);
                add(task);
            }
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epic != null) {
            if (epic.getId() != null) {
                remove(epic.getId());
                epicMap.put(epic.getId(), epic);
                add(epic);
            }
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtask != null) {
            if (subtask.getId() != null) {
                remove(subtask.getId());
                subtaskMap.put(subtask.getId(), subtask);
                add(subtask);
                Integer idEpic = getSubtasks().get(subtask.getId()).getIdEpic();
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
        if (taskMap.containsKey(id)) {
            taskMap.remove(id);
            remove(id);
        }
    }

    @Override
    public void deleteIdEpic(Integer id) {
        if (epicMap.containsKey(id)) {
            Epic epic = epicMap.get(id);
            for (Subtask subtask : epic.getSubtasks()) {
                subtaskMap.remove(subtask.getId());
                remove(subtask.getId());
            }
            epicMap.remove(id);
            remove(id);
        }
    }

    @Override
    public void deleteIdSubtask(Integer id) {
        if (subtaskMap.containsKey(id)) {
            int epicId = subtaskMap.get(id).getIdEpic();
            Epic epic = epicMap.get(epicId);
            ArrayList<Subtask> subtaskId = epic.getSubtasks();
            subtaskId.remove(id);
            subtaskMap.remove(id);
            statusEpic(epic);
            remove(id);

        }
    }

    ///////////////////////////////////////////////////////////////////////
    //Дополнительные методы
    public List<Subtask> getListSubtaskIdEpic(Integer id) {
        if (epicMap.containsKey(id)) {
            Epic epic = epicMap.get(id);
            return new ArrayList<>(epic.getSubtasks());
        }
        return null;
    }

    private void statusEpic(Epic epic) {
        if (epic != null) {
            List<Subtask> subtaskList = epic.getSubtasks();
            int count1 = 0;
            int count2 = 0;
            for (Subtask subtask : subtaskList) {
                if (subtask.getStatus().equals(Status.NEW)) {
                    count1++;
                    if (count1 == subtaskList.size()) {
                        epic.setStatus(Status.NEW);
                    }
                } else if (subtask.getStatus().equals(Status.DONE)) {
                    count2++;
                    if (count2 == subtaskList.size()) {
                        epic.setStatus(Status.NEW);
                    }
                } else {
                    epic.setStatus(Status.IN_PROGRESS);
                }
            }
            if (taskMap.containsKey(epic.getId())) {
                Task task = taskMap.get(epic.getId());
                if (epic.getStatus().equals(Status.NEW)) {
                    task.setStatus(Status.NEW);
                } else if (epic.getStatus().equals(Status.DONE)) {
                    task.setStatus(Status.DONE);
                } else {
                    task.setStatus(Status.IN_PROGRESS);
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////
    //методы из класса HistoryManager
    public void add(Task task) {
        historyManager.add(task);
    }

    public void remove(Integer id) {
        historyManager.remove(id);
    }

    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

}