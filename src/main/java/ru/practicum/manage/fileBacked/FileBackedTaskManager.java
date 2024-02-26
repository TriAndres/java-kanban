package ru.practicum.manage.fileBacked;

import ru.practicum.exseption.ManagerSaveException;
import ru.practicum.manage.inMemoryTask.InMemoryTaskManager;
import ru.practicum.manage.inMemoryTask.TaskManager;
import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public static void main(String[] args) {
        File file = new File("src\\main\\java\\ru\\practicum\\manage\\file\\test.csv");
        TaskManager manager = new FileBackedTaskManager(file);

        while (true) {
            System.out.println("""
                    Действие:
                    1 - добавить задачу
                    2 - добавить эпик
                    3 - добавить подзадачу
                    4 - вывод
                    5 - удаление task
                    6 - удаление epic
                    7 - удаление subtask
                    """);
            String line = new Scanner(System.in).nextLine();
            switch (line) {
                case "1":
                    System.out.println("Задача,введите description");
                    Task task = new Task(new Scanner(System.in).nextLine());
                    manager.addNewTask(task);
                    System.out.println(task);
                    break;
                case "2":
                    System.out.println("Эпик, введите description");
                    Epic epic = new Epic(new Scanner(System.in).nextLine());
                    manager.addNewEpic(epic);
                    System.out.println(epic);
                    break;
                case "3":
                    System.out.println("Подзадача,введите description");
                    String name = new Scanner(System.in).nextLine();
                    System.out.println("Введите epicId");
                    Integer id = new Scanner(System.in).nextInt();
                    Subtask subtask = new Subtask(name, id);
                    manager.addNewSubtask(subtask);
                    System.out.println(subtask);
                    break;
                case "4":
                    System.out.println("вывод");
                    for (Task task1 : loadFromFile(file).getTasks()) {
                        System.out.println(task1.toString());
                    }

                    for (Epic epic1 : loadFromFile(file).getEpics()) {
                        System.out.println(epic1.toString());
                    }

                    for (Subtask subtask1 : loadFromFile(file).getSubtasks()) {
                        System.out.println(subtask1.toString());
                    }
                    break;
                case "5":
                    manager.deleteAllTask();
                    break;
                case "6":
                    manager.deleteAllEpic();
                    break;
                case "7":
                    manager.deleteAllSubtask(); //ошибка, удаляет всё
                    break;
            }
        }
    }

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write("type,id,name,description,status,epicId\n");
            for (Task task : taskMap.values()) {
                if (task != null) {
                    writer.write(Csv.toString(task));
                }
            }
            for (Epic epic : epicMap.values()) {
                if (epic != null) {
                    writer.write(Csv.toString(epic));
                }
            }
            for (Subtask subtask : subtaskMap.values()) {
                if (subtask != null) {
                    writer.write(Csv.toString(subtask));
                }
            }
            writer.newLine();
            writer.write("type,id,name,description,status,epicId\n");
            for (Task task : historyManager.getHistory()) {
                writer.write(Csv.historyToString(task));
            }
        } catch (IOException e) {
            throw new ManagerSaveException();
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
        Status status = null;
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.ready()) {
                String[] line = reader.readLine().split(",");
                status = switch (line[4]) {
                    case "NEW" -> Status.NEW;
                    case "IN_PROGRESS" -> Status.IN_PROGRESS;
                    case "DONE" -> Status.DONE;
                    default -> status;
                };
                if (!line[0].equals("type")) {

                    if (line[0].equals("TASK")) {
                        taskMap.put(Integer.parseInt(line[1]), new Task(Integer.parseInt(line[1]), line[2], line[3], status));
                    }
                    if (line[0].equals("EPIC")) {
                        epicMap.put(Integer.parseInt(line[1]), new Epic(Integer.parseInt(line[1]), line[2], line[3], status));
                    }
                    if (line[0].equals("SUBTASK")) {
                        subtaskMap.put(Integer.parseInt(line[1]), new Subtask(Integer.parseInt(line[1]), line[2], line[3], status, Integer.parseInt(line[5])));
                    }
                    if (line[0].equals("History")) {
                        if (taskMap.containsKey(Integer.parseInt(line[1]))) {
                            historyManager.add(taskMap.get(Integer.parseInt(line[1])));
                        } else if (epicMap.containsKey(Integer.parseInt(line[1]))) {
                            historyManager.add(epicMap.get(Integer.parseInt(line[1])));
                        } else if (subtaskMap.containsKey(Integer.parseInt(line[1]))) {
                            subtaskMap.containsKey(Integer.parseInt(line[1]));
                        }

                    }
                }
            }

        } catch (IOException e) {
            throw new ManagerSaveException();
        }
        return fileBackedTaskManager;
    }
}
