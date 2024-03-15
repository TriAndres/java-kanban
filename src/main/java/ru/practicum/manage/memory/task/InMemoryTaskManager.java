package ru.practicum.manage.memory.task;

import ru.practicum.exseption.ManagerValidateException;
import ru.practicum.manage.Managers;
import ru.practicum.manage.memory.history.HistoryManager;
import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private static Integer generateId = 0;
    protected final Map<Integer, Task> taskMap = new HashMap<>();
    protected final Map<Integer, Epic> epicMap = new HashMap<>();
    protected final Map<Integer, Subtask> subtaskMap = new HashMap<>();
    protected final HistoryManager historyManager = Managers.getDefaultHistory();

    //private Comparator<Task> comparator = Comparator.comparing(Task::getStartTime);
    private Comparator<Task> comparator = Comparator.comparing(Task::getId);
    protected final Set<Task> prioritized = new TreeSet<>(comparator);

    public List<Task> getPrioritizedTasks() {
        return List.copyOf(prioritized);
    }

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
        if (!taskMap.isEmpty()) {
            for (Integer id : taskMap.keySet()) {
                remove(id);
                prioritized.remove(taskMap.get(id));
            }
            taskMap.clear();
        }
    }

    @Override
    public void deleteAllEpic() {
        if (!epicMap.isEmpty()) {
            for (Integer id : epicMap.keySet()) {
                remove(id);
                prioritized.remove(epicMap.get(id));
            }
            epicMap.clear();
        }
        if (!subtaskMap.isEmpty()) {
            for (Integer id : subtaskMap.keySet()) {
                remove(id);
                prioritized.remove(subtaskMap.get(id));
            }
            subtaskMap.clear();
        }
    }

    @Override
    public void deleteAllSubtask() {
        if (!subtaskMap.isEmpty()) {
            for (Integer id : subtaskMap.keySet()) {
                remove(id);
                prioritized.remove(subtaskMap.get(id));
            }
        }
        if (!epicMap.isEmpty()) {
            for (Epic epic : epicMap.values()) {
                epic.getSubtasksId().clear();
                statusEpic(epic);
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
                addPrioritizedTasks(task);
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
                addPrioritizedTasks(epic);
            }
        }
    }

    @Override
    public void addNewSubtask(Subtask subtask) {
        if (subtask != null) {
            Integer id = generateId();
            subtask.setId(id);
            Epic epic = epicMap.get(subtask.getEpicId());
            if (epic != null) {
                subtaskMap.put(id, subtask);
                epic.addSubtask(id);
                addPrioritizedTasks(subtask);
                statusEpic(epic);
            }
        }
    }

    @Override
    public void updateTask(Task task) {
        if (task != null) {
            taskMap.put(task.getId(), task);
            Task oldTask = taskMap.get(task.getId());
            prioritized.remove(oldTask);
            remove(task.getId());
            prioritized.add(task);
            add(task);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epic != null) {
            epicMap.put(epic.getId(), epic);
            Epic oldEpic = epicMap.get(epic.getId());
            prioritized.remove(oldEpic);
            remove(epic.getId());
            prioritized.add(epic);
            add(epic);
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtask != null) {
            subtaskMap.put(subtask.getId(), subtask);
            Subtask oldSubtask = subtaskMap.get(subtask.getId());
            subtask.setEpicId(oldSubtask.getEpicId());
            prioritized.remove(oldSubtask);
            remove(oldSubtask.getId());
            prioritized.add(subtask);
            add(subtask);
            if (oldSubtask.getEpicId() != 0) {
                Epic epic = epicMap.get(subtask.getEpicId());
                statusEpic(epic);
            }
        }
    }

    @Override
    public void deleteIdTask(Integer id) {
        if (taskMap.containsKey(id)) {
            taskMap.remove(id);
            remove(id);
            prioritized.remove(taskMap.get(id));
        }
    }

    @Override
    public void deleteIdEpic(Integer id) {
        if (epicMap.containsKey(id)) {
            Epic epic = epicMap.get(id);
            for (Integer subtaskId : epic.getSubtasksId()) {
                remove(subtaskId);
                prioritized.remove(subtaskMap.remove(subtaskId));
            }
            epicMap.remove(id);
            remove(id);
            prioritized.remove(epicMap.remove(id));
        }
    }

    @Override
    public void deleteIdSubtask(Integer id) {
        if (subtaskMap.containsKey(id)) {
            Subtask subtask = subtaskMap.get(id);
            Epic epic = epicMap.get(subtask.getEpicId());
            if (subtask.getEpicId() != 0) {
                epic.getSubtasksId().remove(id);
            }
            subtaskMap.remove(id);
            remove(id);
            prioritized.remove(subtaskMap.get(id));
            statusEpic(epic);
        }
    }

    //Дополнительные методы
    public List<Integer> getListSubtaskIdEpic(Integer id) {
        if (epicMap.containsKey(id)) {
            Epic epic = epicMap.get(id);
            return new ArrayList<>(epic.getSubtasksId());
        }
        return null;
    }

    public void statusEpic(Epic epic) {
        if (epicMap.containsKey(epic.getId())) {
            if (epic.getSubtasksId().isEmpty()) {
                epic.setStatus(Status.NEW);
            } else {
                List<Integer> subtasksNew = new ArrayList<>(epic.getSubtasksId());
                int countDone = 0;
                int countNew = 0;
                for (Integer subtaskId : subtasksNew) {
                    if (subtaskMap.get(subtaskId).getStatus().equals(Status.NEW)) {
                        countDone++;
                    }
                    if (subtaskMap.get(subtaskId).getStatus().equals(Status.NEW)) {
                        countNew++;
                    }
                    if (countDone == epic.getSubtasksId().size()) {
                        epic.setStatus(Status.DONE);
                    } else if (countNew == epic.getSubtasksId().size()) {
                        epic.setStatus(Status.NEW);
                    } else {
                        epic.setStatus(Status.IN_PROGRESS);
                    }
                }
            }
        }
    }

    public void addPrioritizedTasks(Task task) {
        prioritized.add(task);
        //validate();
    }


    public boolean checkTime(Task task1) {
        List<Task> taskList = List.copyOf(getPrioritizedTasks());
        int countTimeNull = 0;
        if (!taskList.isEmpty()) {
            for (Task task2 : taskList) {
                if (task2.getStartTime() != null && task2.getEndTime() != null) {
                    if (task1.getStartTime().isBefore(task2.getStartTime())
                            && task1.getEndTime().isBefore(task2.getEndTime())) {
                        return true;
                    } else if (task1.getStartTime().isAfter(task2.getStartTime())
                            && task1.getEndTime().isAfter(task2.getEndTime())) {
                        return true;
                    }
                } else {
                    countTimeNull++;
                }
            }
            return countTimeNull == taskList.size();
        } else {
            return true;
        }

    }

    public void validate() {
        List<Task> taskList = List.copyOf(getPrioritizedTasks());
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            boolean checkTime = checkTime(task);
            if (checkTime) {
                throw new ManagerValidateException(
                        "Задачи " + task.getId() + " и " + taskList.get(i - 1) + " пересекаются");
            }
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