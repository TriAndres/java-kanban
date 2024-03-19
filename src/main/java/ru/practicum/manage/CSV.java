package ru.practicum.manage;

import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CSV {
    protected static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    public static String toString(Task task) {
        if (task instanceof Epic) {
            String[] toJoin = {String.valueOf(task.getType()), String.valueOf(task.getId()), task.getName(), task.getDescription(), String.valueOf(task.getStatus()),
                    String.valueOf(task.getStartTime().format(formatter)),String.valueOf(task.getDuration())};
            return String.join(",", toJoin) + "\n";
        } else if (task instanceof Subtask) {
            String[] toJoin = {String.valueOf(task.getType()), String.valueOf(task.getId()), task.getName(), task.getDescription(), String.valueOf(task.getStatus()),
                    String.valueOf(task.getStartTime().format(formatter)),String.valueOf(task.getDuration()), String.valueOf(task.getEpicId())};
            return String.join(",", toJoin) + "\n";
        } else {
            String[] toJoin = {String.valueOf(task.getType()), String.valueOf(task.getId()), task.getName(), task.getDescription(), String.valueOf(task.getStatus()),
                    String.valueOf(task.getStartTime().format(formatter)), String.valueOf(task.getDuration())};
            return String.join(",", toJoin) + "\n";
        }
    }

    public Task fromString(String value) {
        String[] line = value.split(",");

        if (line[0].equals("TASK")) {
            return new Task(Integer.parseInt(line[1]), line[2], line[3], Status.valueOf(line[4].toUpperCase()), LocalDateTime.parse(line[5], formatter), Long.parseLong(line[6]));
        }

        if (line[0].equals("EPIC")) {
            return new Epic(Integer.parseInt(line[1]), line[2], line[3], Status.valueOf(line[4].toUpperCase()), LocalDateTime.parse(line[5], formatter), Long.parseLong(line[6]));
        }

        if (line[0].equals("SUBTASK")) {
            return new Subtask(Integer.parseInt(line[1]), line[2], line[3], Status.valueOf(line[4].toUpperCase()), LocalDateTime.parse(line[5], formatter), Long.parseLong(line[6]), Integer.parseInt(line[7]));
        }
        if (line[0].equals("type") || line[0].equals("History")) {

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
        List<Integer> list = new ArrayList<>();
        if (value != null) {
            String[] id = value.split(",");
            for (String number : id) {
                list.add(Integer.parseInt(number));
            }
            return list;
        }
        return list;
    }
}