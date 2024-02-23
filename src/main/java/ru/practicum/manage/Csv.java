package ru.practicum.manage;

import ru.practicum.model.Epic;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.util.List;

public class Csv {

    public static String toString(Task task) {
        TaskType taskType;
        String epicId = "";
        StringBuilder sb = new StringBuilder();
        if (task instanceof Epic){
            taskType = TaskType.EPIC;
            for (Subtask subtask : ((Epic) task).getSubtasks()) {
                sb.append(subtask.getIdEpic()).append("/");
            }
            epicId = sb.toString();
        } else if (task instanceof Subtask) {
            taskType = TaskType.SUBTASK;
            epicId += ((Subtask) task).getIdEpic();

        } else {
            taskType = TaskType.TASK;
        }
        return String.format("%s,%d,%s,%s,%s,%s%n", taskType,task.getId(),task.getTitle(),task.getDescription(),task.getStatus(),epicId);
    }

    Task fromString(String value) {
        return null;
    }

    static String historyToString(HistoryManager manager) {
        return null;
    }

    static List<Integer> historyFromString(String value) {
        return null;
    }
}
