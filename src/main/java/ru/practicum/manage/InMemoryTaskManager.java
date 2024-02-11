package ru.practicum.manage;

import ru.practicum.model.Epic;
import ru.practicum.model.Task;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *Для каждой подзадачи известно, в рамках какого эпика она выполняется.
 *Каждый эпик знает, какие подзадачи в него входят.
 *Завершение всех подзадач эпика считается завершением эпика.
 */
public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> taskMap = new HashMap<>();
    private final Map<Integer, Epic> epicMap = new HashMap<>();
    private final Map<Integer, Subtask> subtaskMap = new HashMap<>();
    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        historyManager = Managers.getDefaultHistory();
    }

    // a. Получение списка всех задач.
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

    // b. Удаление всех задач.
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

    // c. Получение по идентификатору.
    @Override
    public Task getTask(Integer taskId) {
        add(taskMap.get(taskId));
   /*
       add(taskMap.get(taskId));

Если хотите, можно один раз вызвать получение переменной taskMap.get(taskId)
и записать её в локальную переменную, а затем передать эту локальную
переменную в метод add и вернуть из метода через return. Это позволит
использовать выражение taskMap.get(taskId) один раз

Как это сделать? Переменную в классе App() ?
    */
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

    // d. Создание. Сам объект должен передаваться в качестве параметра.
    @Override
    public Task addNewTask(Task task) {
        Integer id = taskMap.size() + 1;
        taskMap.put(id, task);
        task.setId(id);
        return task;
    }

    @Override
    public Epic addNewEpic(Epic epic) {
        Integer id = epicMap.size() + 1;
        epicMap.put(id, epic);
        epic.setId(id);
        return epic;
    }

    @Override
    public Subtask addNewSubtask(Subtask subtask) {
        Integer id = subtaskMap.size() + 1;
        subtaskMap.put(id, subtask);
        subtask.setId(id);
        Integer idEpic = subtask.getIdEpic();
        Epic epic = epicMap.get(idEpic);
        epic.add(subtask);
        statusEpic(epic);
        return subtask;
    }

    // e. Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
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

    // f. Удаление по идентификатору.
    @Override
    public void deleteIdTask(Integer id) {
        taskMap.remove(id);
    }

    @Override
    public void deleteIdEpic(Integer id) {
        Epic epic = epicMap.get(id);
        for (Subtask subtask : epic.getSubtasks()) {
            subtaskMap.remove(subtask.getId());
        }
        epicMap.remove(id);
    }

    @Override
    public void deleteIdSubtask(Integer id) {
        Subtask subtask = subtaskMap.get(id);
        Epic epic = epicMap.get(subtask.getIdEpic());
        epic.getSubtasks().remove(subtask);
        statusEpic(epic);
        subtaskMap.remove(id);
    }

    public void add(Task task) {
        historyManager.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    //Дополнительные методы:
    //a. Получение списка всех подзадач определённого эпика.
    public ArrayList<Subtask> getListSubtaskIdEpic(Integer id) {
        Epic epic = epicMap.get(id);
        return epic.getSubtasks();
    }

    //Управление статусами осуществляется по следующему правилу:
    // a. Менеджер сам не выбирает статус для задачи. Информация
    // о нём приходит менеджеру вместе с информацией о самой задаче.
    // По этим данным в одних случаях он будет сохранять статус, в
    // других будет рассчитывать.
    // b. Для эпиков:
    //*если у эпика нет подзадач или все они имеют статус NEW, то статус должен быть NEW.
    //*если все подзадачи имеют статус DONE, то и эпик считается завершённым — со статусом DONE.
    //*во всех остальных случаях статус должен быть IN_PROGRESS.

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