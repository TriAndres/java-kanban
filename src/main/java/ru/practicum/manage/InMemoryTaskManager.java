package ru.practicum.manage;

import ru.practicum.exseption.ManagerValidateException;
import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static Integer generateId = 0;
    protected final Map<Integer, Task> taskMap = new HashMap<>();
    protected final Map<Integer, Epic> epicMap = new HashMap<>();
    protected final Map<Integer, Subtask> subtaskMap = new HashMap<>();
    protected final HistoryManager historyManager = Managers.getDefaultHistory();
    private final Comparator<Task> comparator = Comparator.comparing(Task::getStartTime);
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
                prioritized.remove(subtask);
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
            taskMap.put(id, task);
            addPrioritizedTasks(task);
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
            Epic epic = epicMap.get(subtask.getEpicId());
            addPrioritizedTasks(subtask);
            subtaskMap.put(id, subtask);
            epic.addSubtask(id);
            statusEpic(epic);
            updateTimeEpic(epic);
        }
    }

    @Override
    public void updateTask(Task task) {
        if (!taskMap.containsKey(task.getId()) && task.getId() != null ) {
            taskMap.put(task.getId(), task);
            Task oldTask = taskMap.get(task.getId());
            remove(oldTask.getId());
            add(task.getId());
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (!epicMap.containsKey(epic.getId()) && epic.getId() != null) {
            epicMap.put(epic.getId(), epic);
            Epic oldEpic = epicMap.get(epic.getId());
            remove(oldEpic.getId());
            add(epic.getId());
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (!subtaskMap.containsKey(subtask.getId()) && subtask.getId() != null) {
            subtaskMap.put(subtask.getId(), subtask);
            Subtask oldSubtask = subtaskMap.get(subtask.getId());
            subtask.setEpicId(oldSubtask.getEpicId());
            remove(oldSubtask.getId());
            add(subtask.getId());
            if (oldSubtask.getEpicId() != 0) {
                Epic epic = epicMap.get(subtask.getEpicId());
                statusEpic(epic);
                updateTimeEpic(epic);
            }
        }
    }

    @Override
    public void deleteIdTask(Integer id) {
        if (taskMap.containsKey(id)) {
            prioritized.remove(taskMap.get(id));
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
            prioritized.remove(subtaskMap.get(id));
            Subtask subtask = subtaskMap.get(id);
            Epic epic = epicMap.get(subtask.getEpicId());
            epic.getSubtasksId().remove(id);
            subtaskMap.remove(id);
            remove(id);
            statusEpic(epic);
            updateTimeEpic(epic);
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
        prioritizedAdd(task);
        validateTaskPriority();
    }

    public void prioritizedAdd(Task task) {
        if (task != null) {
            prioritized.add(task);
        }
    }

    public void updateTimeEpic(Epic epic) {
        List<Subtask> subtasks = getListSubtaskIdEpic(epic.getId());
        LocalDateTime startTime = subtasks.getFirst().getStartTime();
        LocalDateTime endTime = subtasks.getFirst().getEndTime();

        for (Subtask subtask : subtasks) {
            if (subtask.getStartTime().isBefore(startTime)) {
                startTime = subtask.getStartTime();
            }
            if (subtask.getEndTime().isAfter(endTime)) {
                endTime = subtask.getEndTime();
            }
        }

        epic.setStartTime(startTime);
        epic.setEndTime(endTime);
        Long duration = (long) (endTime.getSecond() - startTime.getSecond());
        epic.setDuration(duration);
    }

    public boolean checkTime(Task task) {
        List<Task> tasks = List.copyOf(getPrioritizedTasks());
        int sizeTimeNull = 0;
        if (!tasks.isEmpty()) {
            for (Task taskSave : tasks) {
                if (taskSave.getStartTime() != null && taskSave.getEndTime() != null) {
                    if (task.getStartTime().isBefore(taskSave.getStartTime())
                            && task.getEndTime().isBefore(taskSave.getStartTime())) {
                        return true;
                    } else if (task.getStartTime().isAfter(taskSave.getEndTime())
                            && task.getEndTime().isAfter(taskSave.getEndTime())) {
                        return true;
                    }
                } else {
                    sizeTimeNull++;
                }

            }
            return sizeTimeNull == tasks.size();
        } else {
            return true;
        }
    }

    private void validateTaskPriority() {
        List<Task> tasks = getPrioritizedTasks();

        for (int i = 1; i < tasks.size(); i++) {
            Task task = tasks.get(i);

            boolean taskHasIntersections = checkTime(task);

            if (taskHasIntersections) {
                throw new ManagerValidateException(
                        "Задачи #" + task.getId() + " и #" + tasks.get(i - 1) + "пересекаются");
            }
        }
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

    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}