package ru.practicum.manage.fileBacked;

import ru.practicum.manage.history.HistoryManager;
import ru.practicum.model.*;


import java.util.List;

import static ru.practicum.model.TaskType.*;

public class Csv {

    public static String toString(Task task) {
        Integer epicId = 0;
        TaskType taskType = TASK;
        if (task instanceof Epic) {
            taskType = EPIC;
        } else if (task instanceof Subtask) {
            epicId = ((Subtask) task).getIdEpic();
            taskType = SUBTASK;
        }
        return String.format("%s,%d,%s,%s,%s,%d%n", taskType,task.getId(),task.getTitle(),task.getDescription(),task.getStatus(),epicId);
    }

    public Task fromString(String value) {
        return null;
    }

    static String historyToString(HistoryManager manager) {
        return null;
    }

    static List<Integer> historyFromString(String value) {
        return null;
    }
}