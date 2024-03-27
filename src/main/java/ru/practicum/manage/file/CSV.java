package ru.practicum.manage.file;

import ru.practicum.manage.history.HistoryManager;
import ru.practicum.model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CSV {
    protected static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static String toString(Task task) {
        if (task instanceof Epic) {
            String[] toJoin = {String.valueOf(task.getType()), String.valueOf(task.getId()), task.getName(), task.getDescription(), String.valueOf(task.getStatus()),
                    String.valueOf(task.getDuration()), String.valueOf(task.getStartTime().format(formatter))};
            return String.join(",", toJoin) + "\n";
        } else if (task instanceof Subtask) {
            String[] toJoin = {String.valueOf(task.getType()), String.valueOf(task.getId()), task.getName(), task.getDescription(), String.valueOf(task.getStatus()),
                    String.valueOf(task.getDuration()), String.valueOf(task.getStartTime().format(formatter)), String.valueOf(task.getEpicId())};
            return String.join(",", toJoin) + "\n";
        } else if (task instanceof Task) {
            String[] toJoin = {String.valueOf(task.getType()), String.valueOf(task.getId()), task.getName(), task.getDescription(), String.valueOf(task.getStatus()),
                    String.valueOf(task.getDuration()), String.valueOf(task.getStartTime().format(formatter))};
            return String.join(",", toJoin) + "\n";
        }
        return null;
    }


    public Task fromString(String value) {
        String[] line = value.split(",");
        if (line[0].equals("type")) {
            return null;
        }
        if (line[0].equals("TASK")) {
            return new Task(Integer.parseInt(line[1]), line[2], line[3], Status.valueOf(line[4].toUpperCase()),
                    Duration.parse(line[5]), LocalDateTime.parse(line[6], formatter));
        }

        if (line[0].equals("EPIC")) {
            return new Epic(Integer.parseInt(line[1]), line[2], line[3], Status.valueOf(line[4].toUpperCase()),
                    Duration.parse(line[5]), LocalDateTime.parse(line[6], formatter));
        }

        if (line[0].equals("SUBTASK")) {
            return new Subtask(Integer.parseInt(line[1]), line[2], line[3], Status.valueOf(line[4].toUpperCase()),
                    Duration.parse(line[5]), LocalDateTime.parse(line[6], formatter), Integer.parseInt(line[7]));
        }
        if (line[0].equals("History")) {
            return null;
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
