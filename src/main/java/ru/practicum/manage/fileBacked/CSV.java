package ru.practicum.manage.fileBacked;

import ru.practicum.manage.history.HistoryManager;
import ru.practicum.model.Epic;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.util.List;

import static ru.practicum.model.TaskType.*;

public class CSV {
    public static String toString(Task task) {
        String string;
        if (task instanceof Epic) {
            string = String.format("%s,%d,%s,%s,%s%n", EPIC, task.getId(), task.getTitle(), task.getDescription(), task.getStatus());
        } else if (task instanceof Subtask) {
            Integer epicId = ((Subtask) task).getIdEpic();
            string = String.format("%s,%d,%s,%s,%s,%d%n", SUBTASK, task.getId(), task.getTitle(), task.getDescription(), task.getStatus(), epicId);
        } else {
            string = String.format("%s,%d,%s,%s,%s%n", TASK, task.getId(), task.getTitle(), task.getDescription(), task.getStatus());
        }
        return string;
    }

    public Task fromString(String value) {
        return null;
    }

    public static String historyToString(HistoryManager manager) {
        String line = "";
        for (Task task : manager.getHistory()) {
            line += task.getId() + ",";
        }
        return String.join("", "History ", line);
    }

    static List<Integer> historyFromString(String value) {
        return null;
    }
}