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
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        while (true) {
            System.out.println("""
                    Действие:
                    1 - тест1
                    2 - теые2
                    """);
            String line = new Scanner(System.in).nextLine();
            switch (line) {
                case "1":
                    manager.addNewTask(new Task("*** 1 ЗАДАЧА ***"));                      //1
                    manager.addNewEpic(new Epic("*** 1 ЭПИК ***"));                        //1
                    manager.addNewTask(new Task("*** 2 ЗАДАЧА ***"));                      //2
                    manager.addNewEpic(new Epic("*** 2 ЭПИК ***"));                        //2
                    manager.addNewTask(new Task("*** 3 ЗАДАЧА ***"));                      //3
                    manager.addNewSubtask(new Subtask("*** 1 ПОДЗАДАЧА ***", 2)); //1
                    manager.addNewSubtask(new Subtask("*** 2 ПОДЗАДАЧА ***",2));  //2
                    manager.addNewTask(new Task("*** 4 ЗАДАЧА ***"));                      //4
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

//Так же надо проверить, что история корректно загрузилась, для этого добавьте метод getHistory() в интерфейс TaskManager

                    for (Task task : manager.getTasks()) {
                        System.out.println(task.toString());
                    }
//Проверка корректной загрузки подзадач для эпиков
                    for (Epic epic : manager.getEpics()) {
                        System.out.println(epic.toString());

                        System.out.println(epic.getId() + "@@@");

                        for (Subtask subtask : epic.getSubtasks()) {
                            System.out.println(subtask + "###");
                        }
                    }



                    for (Subtask subtask : manager.getSubtasks()) {
                        System.out.println(subtask.toString());
                    }
                    break;

            }
        }
    }

    public void save() {
        final String FIRST_LINE = "type,id,name,description,status,epicId\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
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
        Status status = null;
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.ready()) {
                String[] line = reader.readLine().split(",");
                if (line.length >= 4) {
                    status = switch (line[4]) {
                        case "NEW" -> Status.NEW;
                        case "IN_PROGRESS" -> Status.IN_PROGRESS;
                        case "DONE" -> Status.DONE;
                        default -> status;
                    };
                    if (line[0].equals("TASK")) {
                        fileBackedTaskManager.updateTask(new Task(Integer.parseInt(line[1]), line[2], line[3], status));
                    }
                    if (line[0].equals("EPIC")) {
                        fileBackedTaskManager.updateEpic(new Epic(Integer.parseInt(line[1]), line[2], line[3], status));
                    }
                    if (line[0].equals("SUBTASK")) {
                        fileBackedTaskManager.updateSubtask(new Subtask(Integer.parseInt(line[1]), line[2], line[3], status, Integer.parseInt(line[5])));
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при Чтении.");
        }
        return fileBackedTaskManager;
    }
}