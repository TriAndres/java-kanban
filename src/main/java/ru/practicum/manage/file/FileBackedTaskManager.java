package ru.practicum.manage.file;

import ru.practicum.exseption.ManagerSaveException;
import ru.practicum.manage.memory.task.InMemoryTaskManager;
import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;
    
    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public static void main(String[] args) {
        File file = new File("src\\main\\java\\ru\\practicum\\manage\\file\\test.csv");
        FileBackedTaskManager manager = loadFromFile(file);
        while (true) {

            System.out.println("""
                    Действие:
                    1 - тест1
                    2 - тест2
                    3 - тест3
                    4 - тест4
                    """);
            String line = new Scanner(System.in).nextLine();
            switch (line) {
                case "1":
                    manager.addNewTask(new Task("*** 1 ЗАДАЧА ***"));
                    manager.addNewEpic(new Epic("*** 1 ЭПИК ***"));
                    manager.addNewTask(new Task("*** 2 ЗАДАЧА ***"));
                    manager.addNewEpic(new Epic("*** 2 ЭПИК ***"));
                    manager.addNewTask(new Task("*** 3 ЗАДАЧА ***"));
                    manager.addNewSubtask(new Subtask("*** 1 ПОДЗАДАЧА ***", 2));
                    manager.addNewSubtask(new Subtask("*** 2 ПОДЗАДАЧА ***", 2));
                    manager.addNewTask(new Task("*** 4 ЗАДАЧА ***"));
                    manager.addNewTask(new Task("*** 5 ЗАДАЧА ***"));
                    manager.addNewEpic(new Epic("*** 3 ЭПИК ***"));

                    System.out.println(manager.getTaskId(3));
                    System.out.println(manager.getSubtaskId(1));
                    System.out.println(manager.getSubtaskId(2));
                    System.out.println(manager.getEpicId(2));
                    System.out.println(manager.getTaskId(4));
                    System.out.println(manager.getTaskId(5));

                    for (Task task : manager.getTasks()) {
                        System.out.println(task.toString());
                    }

                    for (Epic epic : manager.getEpics()) {
                        System.out.println(epic.toString());
                    }

                    for (Subtask subtask : manager.getSubtasks()) {
                        System.out.println(subtask.toString());
                    }
                    break;
                case "2":
                    System.out.println(manager.getSubtasks());
                    System.out.println(manager.getTasks());
                    System.out.println(manager.getEpics());

                    for (Task task : manager.getTasks()) {
                        System.out.println(task.toString());
                    }

                    for (Epic epic : manager.getEpics()) {
                        System.out.println(epic.toString());

                        System.out.println(epic.getId());

                        for (Integer subtaskId : epic.getSubtasksId()) {
                            System.out.print(subtaskId + " ");
                        }
                    }

                    for (Subtask subtask : manager.getSubtasks()) {
                        System.out.println(subtask.toString());
                    }
                    break;
                case "3":

                    System.out.println(manager.getSubtasks());
                    System.out.println(manager.getTasks());
                    System.out.println(manager.getEpics());

                    for (Epic epic : manager.getEpics()) {
                        System.out.print(epic.getId() + " ");
                        System.out.print(epic.getSubtasksId() + " ");
                    }
                    break;
                case "4":

                    System.out.println(manager.getSubtasks());
                    System.out.println(manager.getTasks());
                    System.out.println(manager.getEpics());
                    break;
            }
        }
    }

    public void save() {
        final String FIRST_LINE = "type,id,name,description,status,epicId\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,  StandardCharsets.UTF_8))) {
            writer.write(FIRST_LINE);
            for (Task task : taskMap.values()) {
                if (task != null) {
                    writer.write(CSV.toString(task));
                }
            }
            for (Epic epic : epicMap.values()) {
                if (epic != null) {
                    writer.write(CSV.toString(epic));
                }
            }
            for (Subtask subtask : subtaskMap.values()) {
                if (subtask != null) {
                    writer.write(CSV.toString(subtask));
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
                } if (task instanceof Subtask subtask) {
                    fileBackedTaskManager.updateSubtask(subtask);
                } else {
                    fileBackedTaskManager.updateTask(task);
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при Чтении.");
        }
        return fileBackedTaskManager;
    }
}