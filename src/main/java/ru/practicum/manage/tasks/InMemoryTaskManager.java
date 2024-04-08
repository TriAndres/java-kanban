package ru.practicum.manage.tasks;

import ru.practicum.manage.exseptionsMy.ManagerValidateException;
import ru.practicum.manage.Managers;
import ru.practicum.manage.history.HistoryManager;
import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    public static Integer generateId = 0;
    protected final Map<Integer, Task> taskMap = new HashMap<>();
    protected final Map<Integer, Epic> epicMap = new HashMap<>();
    protected final Map<Integer, Subtask> subtaskMap = new HashMap<>();
    protected final HistoryManager historyManager = Managers.getDefaultHistory();

    protected final Set<Task> prioritized = new TreeSet<>(Comparator.comparing(Task::getStartTime));

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
                add(task.getId());
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
                add(epic.getId());
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
                add(subtask.getId());
            }
        }
        return list;
    }


    @Override
    public void deleteAllTask() {
        taskMap.clear();
        prioritized.clear();
    }

    @Override
    public void deleteAllEpic() {
        subtaskMap.clear();
        epicMap.clear();
    }

    @Override
    public void deleteAllSubtask() {
        for (Epic epic : epicMap.values()) {
            for (Integer key : epic.getSubtasksId()) {
                Subtask subtask = subtaskMap.get(key);
                prioritizedRemove(subtask);
                subtaskMap.remove(key);
                historyManager.remove(key);
            }
            epic.getSubtasksId().clear();
        }
    }

    @Override
    public Task getTaskId(Integer id) {
        if (taskMap.containsKey(id)) {
            Task task = taskMap.get(id);
            add(taskMap.get(task.getId()).getId());
            return task;
        }
        return null;
    }

    @Override
    public Epic getEpicId(Integer id) {
        if (epicMap.containsKey(id)) {
            Epic epic = epicMap.get(id);
            add(epicMap.get(epic.getId()).getId());
            return epic;
        }
        return null;
    }

    @Override
    public Subtask getSubtaskId(Integer id) {
        if (subtaskMap.containsKey(id)) {
            Subtask subtask = subtaskMap.get(id);
            add(subtaskMap.get(subtask.getId()).getId());
            return subtask;
        }
        return null;
    }

    @Override
    public void addNewTask(Task task) {
        Integer id = generateId();
        if (!taskMap.containsKey(id)) {
            task.setId(id);
            validate(task);
            taskMap.put(id, task);
            prioritizedAdd(task);
        }
    }

    @Override
    public void addNewEpic(Epic epic) {
        Integer id = generateId();
        if (!epicMap.containsKey(id)) {
            epic.setId(id);
            epicMap.put(id, epic);
        }

    }

    @Override
    public void addNewSubtask(Subtask subtask) {
        Integer id = generateId();
        if (!subtaskMap.containsKey(id)) {
            subtask.setId(id);
            validate(subtask);
            Epic epic = epicMap.get(subtask.getEpicId());
            subtaskMap.put(id, subtask);
            prioritizedAdd(subtask);
            epic.addSubtask(id);
            statusEpic(epic);
        }
    }

    @Override
    public void updateTask(Task task) {
        if (task == null) return;
        if (!taskMap.containsKey(task.getId())) {
            Task oldTask = taskMap.get(task.getId());
            taskMap.put(task.getId(), task);
            if (oldTask == null) return;
            remove(oldTask.getId());
            add(task.getId());
            prioritizedRemove(oldTask);
            prioritizedAdd(task);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epic == null) return;
        if (!epicMap.containsKey(epic.getId())) {
            epicMap.put(epic.getId(), epic);
            Epic oldEpic = epicMap.get(epic.getId());
            remove(oldEpic.getId());
            add(epic.getId());
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtask == null) return;
        if (!subtaskMap.containsKey(subtask.getId())) {
            subtaskMap.put(subtask.getId(), subtask);
            Subtask oldSubtask = subtaskMap.get(subtask.getId());
            if (oldSubtask == null) return;
            subtask.setEpicId(oldSubtask.getEpicId());
            remove(oldSubtask.getId());
            add(subtask.getId());
            if (oldSubtask.getEpicId() != 0) {
                Epic epic = epicMap.get(subtask.getEpicId());
                statusEpic(epic);
            }
            prioritizedRemove(oldSubtask);
            prioritizedAdd(subtask);
        }
    }

    @Override
    public void deleteIdTask(Integer id) {
        if (taskMap.containsKey(id)) {
            prioritizedRemove(taskMap.get(id));
            taskMap.remove(id);
            remove(id);
        }
    }

    @Override
    public void deleteIdEpic(Integer id) {
        if (epicMap.containsKey(id)) {
            Epic epic = epicMap.get(id);
            for (Integer subtaskId : epic.getSubtasksId()) {
                remove(subtaskId);
            }
            epicMap.remove(id);
            remove(id);
        }
    }

    @Override
    public void deleteIdSubtask(Integer id) {
        if (subtaskMap.containsKey(id)) {
            Subtask subtask = subtaskMap.get(id);
            Epic epic = epicMap.get(subtask.getEpicId());
            epic.getSubtasksId().remove(id);
            statusEpic(epic);
            remove(id);
            prioritizedRemove(subtaskMap.get(id));
            subtaskMap.remove(id);
        }
    }

    //Дополнительные методы
    @Override
    public List<Subtask> getListSubtaskIdEpic(Integer id) {
        if (epicMap.containsKey(id)) {
            List<Subtask> subtasks = new ArrayList<>();
            Epic epic = epicMap.get(id);
            for (Integer key : epic.getSubtasksId()) {
                subtasks.add(subtaskMap.get(key));
            }
            return subtasks;
        }
        return null;
    }

    public void statusEpic(Epic epic) {
        if (epicMap.containsKey(epic.getId())) {
            setEpicDuration(epic);
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


    public void setEpicDuration(Epic epic) {
        ArrayList<Integer> subtasks = epic.getSubtasksId();
        Duration epicDuration = Duration.ZERO;
        LocalDateTime startTime = LocalDateTime.MAX;
        LocalDateTime endTime = LocalDateTime.MIN;
        if (!subtasks.isEmpty()) {
            for (Integer taskId : subtasks) {
                Subtask subtask = subtaskMap.get(taskId);
                if (subtask.getStartTime().isBefore(startTime)) {
                    startTime = subtask.getStartTime();
                }
                if (subtask.getEndTime().isAfter(endTime)) {
                    endTime = subtask.getEndTime();
                }
            }
            epicDuration = Duration.between(startTime, endTime);
            epic.setStartTime(startTime);
            epic.setEndTime(endTime);
        } else {
            epic.setStartTime(LocalDateTime.MAX.minusSeconds(59).minusNanos(999999999));
            epic.setEndTime(LocalDateTime.MAX.minusSeconds(59).minusNanos(999999999));
        }
        epic.setDuration(epicDuration);

    }


    public void validate(Task task) {
        List<Task> tasks = new ArrayList<>(getPrioritizedTasks());
        if (!tasks.isEmpty()) {
            for (Task listTask : tasks) {
                if (task.getStartTime().isBefore(listTask.getStartTime())
                        && task.getEndTime().isBefore(listTask.getEndTime())
                        || task.getStartTime().isAfter(listTask.getEndTime())
                        && task.getEndTime().isAfter(listTask.getEndTime())) {
                } else {
                    throw new ManagerValidateException("Ошибка валидации");
                }
            }
        }
    }

    public TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    //методы из класса HistoryManager
    public void add(int id) {
        if (epicMap.containsKey(id)) {
            historyManager.add(epicMap.get(id));
        } else if (subtaskMap.containsKey(id)) {
            historyManager.add(subtaskMap.get(id));
        } else if (taskMap.containsKey(id)) {
            historyManager.add(taskMap.get(id));
        }
    }

    public void remove(Integer id) {
        if (id != null) {
            historyManager.remove(id);
        }
    }

    public ArrayList<Task> getHistory() {
        return new ArrayList<>(historyManager.getHistory());
    }

    //методы из prioritized
    public void prioritizedRemove(Task task) {
        prioritized.removeIf(oldTask -> Objects.equals(oldTask.getId(), task.getId()));
    }

    public void prioritizedAdd(Task task) {
        prioritized.add(task);
    }

    public List<Task> getPrioritizedTasks() {
        return List.copyOf(prioritized);
    }

    public void prioritizedClear() {
        prioritized.clear();
    }
}