package ru.practicum.manage.memory.task;

import ru.practicum.manage.Managers;
import ru.practicum.manage.memory.history.HistoryManager;
import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private static Integer generateId = 0;
    protected final Map<Integer, Task> taskMap = new HashMap<>();
    protected final Map<Integer, Epic> epicMap = new HashMap<>();
    protected final Map<Integer, Subtask> subtaskMap = new HashMap<>();
    protected final HistoryManager historyManager = Managers.getDefaultHistory();

    public static Integer generateId() {
        return ++generateId;
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> list = new ArrayList<>();
        if (!taskMap.isEmpty()) {
            for (Integer key : taskMap.keySet()) {
                Task task = taskMap.get(key);
                list.add(task);
                add(task);
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
                add(epic);
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
                add(subtask);
            }
        }
        return list;
    }

    @Override
    public void deleteAllTask() {
        taskMap.clear();
    }

    @Override
    public void deleteAllEpic() {
        subtaskMap.clear();
        epicMap.clear();
    }

    @Override
    public void deleteAllSubtask() {
        if (!epicMap.isEmpty()) {
            for (Epic value : epicMap.values()) {
                if (!value.getSubtasks().isEmpty()) {
                    value.getSubtasks().clear();
                }
                statusEpic(value);
            }
            subtaskMap.clear();
        }
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
    public void addNewTask(Task task) {
        if (task != null) {
            Integer id = generateId();
            if (!taskMap.containsKey(id)) {
                task.setId(id);
                taskMap.put(id, task);
            }
        }
    }

    @Override
    public void addNewEpic(Epic epic) {
        if (epic != null) {
            Integer id = generateId();
            if (!epicMap.containsKey(id)) {
                epic.setId(id);
                epicMap.put(id, epic);
            }
        }
    }

    @Override
    public void addNewSubtask(Subtask subtask) {
        if (subtask != null) {
            Integer id = generateId();
            subtask.setId(id);
            Integer idEpic = subtask.getIdEpic();
            Epic epic = epicMap.get(idEpic);
            if (epic != null) {
                subtaskMap.put(id, subtask);
                epic.addSubtask(subtask);
                statusEpic(epic);
            } else {
                System.out.println("Epic нет в списке");
            }
        }
    }

    @Override
    public void updateTask(Task task) {
        if (task != null) {
            taskMap.put(task.getId(), task);
            add(task);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epic != null) {
            epicMap.put(epic.getId(), epic);
            add(epic);
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtask != null) {
            subtaskMap.put(subtask.getId(), subtask);
            add(subtask);
            Integer idEpic = subtask.getIdEpic();
            Epic epic = epicMap.get(idEpic);
            epic.removeSubtask(subtask);
            epic.addSubtask(subtask);
            statusEpic(epic);
        }
    }

    @Override
    public void deleteIdTask(Integer id) {
        if (taskMap.containsKey(id)) {
            taskMap.remove(id);
            remove(id);
        } else {
            System.out.println("Task нет в списке");
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
        } else {
            System.out.println("Epic нет в списке");
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
            remove(id);
            statusEpic(epic);
        } else {
            System.out.println("Subtask нет в списке");
        }
    }

    //Дополнительные методы
    public List<Subtask> getListSubtaskIdEpic(Integer id) {
        if (epicMap.containsKey(id)) {
            Epic epic = epicMap.get(id);
            return new ArrayList<>(epic.getSubtasks());
        }
        return null;
    }

    public void statusEpic(Epic epic) {
        if (epicMap.containsKey(epic.getId())) {
            if (epic.getSubtasks().isEmpty()) {
                epic.setStatus(Status.NEW);
            } else {
                List<Subtask> subtasksNew = new ArrayList<>(epic.getSubtasks());
                int countDone = 0;
                int countNew = 0;
                for (Subtask subtask : subtasksNew) {
                    if (subtask.getStatus().equals(Status.DONE)) {
                        countDone++;
                    }
                    if (subtask.getStatus().equals(Status.NEW)) {
                        countNew++;
                    }
                    if (countDone == epic.getSubtasks().size()) {
                        epic.setStatus(Status.DONE);
                    } else if (countNew == epic.getSubtasks().size()) {
                        epic.setStatus(Status.NEW);
                    } else {
                        epic.setStatus(Status.IN_PROGRESS);
                    }
                }
            }
        } else {
            System.out.println("Epic нет в списке");
        }
    }

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