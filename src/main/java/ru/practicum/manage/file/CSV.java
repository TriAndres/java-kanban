package ru.practicum.manage.file;

import ru.practicum.manage.memory.history.HistoryManager;
import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.util.List;

public class CSV {
    public static String toString(Task task) {
        String string;
        if (task instanceof Epic) {
            string = String.format("%s,%d,%s,%s,%s%n", task.getType(), task.getId(), task.getName(), task.getDescription(), task.getStatus());
        } else if (task instanceof Subtask) {
            Integer epicId = ((Subtask) task).getEpicId();
            string = String.format("%s,%d,%s,%s,%s,%d%n", task.getType(), task.getId(), task.getName(), task.getDescription(), task.getStatus(), epicId);
        } else {
            string = String.format("%s,%d,%s,%s,%s%n", task.getType(), task.getId(), task.getName(), task.getDescription(), task.getStatus());
        }
        return string;
    }

    public Task fromString(String value) {
        String[] line = value.split(",");
        if (line[0].equals("type") || line[0].equals("History")) {
        }
        if (line[0].equals("TASK")) {
            return new Task(Integer.parseInt(line[1]), line[2], line[3], Status.valueOf(line[4].toUpperCase()));
        }
        if (line[0].equals("EPIC")) {
            return new Epic(Integer.parseInt(line[1]), line[2], line[3], Status.valueOf(line[4].toUpperCase()));
        }
        if (line[0].equals("SUBTASK")) {
            return new Subtask(Integer.parseInt(line[1]), line[2], line[3], Status.valueOf(line[4].toUpperCase()), Integer.parseInt(line[5]));
        }
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