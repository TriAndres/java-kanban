package ru.practicum.manage.file;

import ru.practicum.exseption.ManagerSaveException;
import ru.practicum.manage.tasks.InMemoryTaskManager;
import ru.practicum.manage.tasks.TaskManager;
import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public static void main(String[] args) {
        File file = new File("src\\main\\java\\ru\\practicum\\manage\\file\\test.csv");
        TaskManager manager1 = loadFromFile(file);
        while (true) {

            System.out.println("""
                    Действие:
                    1 - тест1
                    2 - тест2
                    """);
            String line = new Scanner(System.in).nextLine();
            switch (line) {
                case "1":
                    // Спринт 8
                    manager1.addNewTask(new Task(
                            "Task 1", "Задача 1",
                            Status.NEW,
                            Duration.ofMinutes(1L),
                            LocalDateTime.now()

                    ));
                    Epic epic2 = new Epic(
                            "Epic 2", "Эпик 2",
                            Status.NEW,
                            Duration.ofMinutes(2L),
                            LocalDateTime.now()

                    );
                    manager1.addNewEpic(epic2);

                    manager1.addNewSubtask(new Subtask(
                            "Subtask 3", "Субтаск 3",
                            Status.NEW,
                            Duration.ofMinutes(3L),
                            LocalDateTime.now(),

                            epic2.getId()

                    ));
                    System.out.println(manager1.getTasks());
                    System.out.println(manager1.getEpics());
                    System.out.println(manager1.getSubtasks());

                    break;
                case "2":
                    System.out.println(manager1.getTasks());
                    System.out.println(manager1.getEpics());
                    System.out.println(manager1.getSubtasks());

                    break;
            }
        }
    }

    public void save() {
        final String FIRST_LINE = "type,id,name,description,status,startTime,duration,epicId\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            writer.write(FIRST_LINE);
            for (Task task : taskMap.values()) {
                if (task != null) {
                    writer.write(Objects.requireNonNull(CSV.toString(task)));
                }
            }
            for (Epic epic : epicMap.values()) {
                if (epic != null) {
                    writer.write(Objects.requireNonNull(CSV.toString(epic)));
                }
            }
            for (Subtask subtask : subtaskMap.values()) {
                if (subtask != null) {
                    writer.write(Objects.requireNonNull(CSV.toString(subtask)));
                }
            }
            writer.write(CSV.historyToString(historyManager));
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при записи.");
        }
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> list = super.getTasks();
        save();
        return list;
    }

    @Override
    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> list = super.getEpics();
        save();
        return list;
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        ArrayList<Subtask> list = super.getSubtasks();
        save();
        return list;
    }

    @Override
    public void deleteAllTask() {
        super.deleteAllTask();
        save();
    }

    @Override
    public void deleteAllEpic() {
        super.deleteAllEpic();
        save();
    }

    @Override
    public void deleteAllSubtask() {
        super.deleteAllSubtask();
        save();
    }

    @Override
    public Task getTaskId(Integer taskId) {
        Task task = super.getTaskId(taskId);
        save();
        return task;
    }

    @Override
    public Epic getEpicId(Integer epicId) {
        Epic epic = super.getEpicId(epicId);
        save();
        return epic;
    }

    @Override
    public Subtask getSubtaskId(Integer subtaskId) {
        Subtask subtask = super.getSubtaskId(subtaskId);
        save();
        return subtask;
    }

    @Override
    public void addNewTask(Task task) {
        super.addNewTask(task);
        save();
    }

    @Override
    public void addNewEpic(Epic epic) {
        super.addNewEpic(epic);
        save();
    }

    @Override
    public void addNewSubtask(Subtask subtask) {
        super.addNewSubtask(subtask);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void deleteIdTask(Integer id) {
        super.deleteIdTask(id);
        save();
    }

    @Override
    public void deleteIdEpic(Integer id) {
        super.deleteIdEpic(id);
        save();
    }

    @Override
    public void deleteIdSubtask(Integer id) {
        super.deleteIdSubtask(id);
        save();
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        CSV csv = new CSV();
        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            while (reader.ready()) {
                String line = reader.readLine();
                Task task = csv.fromString(line);
                if (task instanceof Epic epic) {
                    fileBackedTaskManager.updateEpic(epic);
                }
                if (task instanceof Subtask subtask) {
                    fileBackedTaskManager.updateSubtask(subtask);
                }
                if (task instanceof Task task1) {
                    fileBackedTaskManager.updateTask(task1);
                }
            }
            String lineWithHistory = reader.readLine();
            for (int id : CSV.historyFromString(lineWithHistory)) {
                fileBackedTaskManager.add(id);
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при Чтении.");
        }
        return fileBackedTaskManager;
    }

    private static void lineWithHistory(int id) {
    }
}