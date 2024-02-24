package ru.practicum.manage;

import ru.practicum.exseption.ManagerSaveException;
import ru.practicum.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static ru.practicum.model.TaskType.*;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public static void main(String[] args) {
        File file = new File("src\\main\\java\\ru\\practicum\\manage\\fileBacked\\test.csv");
        TaskManager manager = new FileBackedTaskManager(file);

        while (true) {
            System.out.println("""
                    Действие:
                    1 - добавить задачу
                    2 - добавить эпик
                    3 - добавить подзадачу
                    4 - вывод
                    5 - удаление
                    """);
            String line = new Scanner(System.in).nextLine();
            switch (line) {
                case "1":
                    System.out.println("Задача, описание");
                    Task task = new Task(new Scanner(System.in).nextLine());
                    manager.addNewTask(task);
                    System.out.println(task);
                    break;
                case "2":
                    System.out.println("Эпик,описание");
                    Epic epic = new Epic(new Scanner(System.in).nextLine());
                    manager.addNewEpic(epic);
                    System.out.println(epic);
                    break;
                case "3":
                    System.out.println("Подзадача, описание");
                    String name = new Scanner(System.in).nextLine();
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
                    manager.deleteSubtask();
            }
        }

    }

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("type,id,name,description,status,epic\n");
            for (Task value : taskMap.values()) {
                writer.write(String.format("%s,%d,%s,%s,%s%n", TaskType.TASK,value.getId(),value.getTitle(),value.getDescription(),value.getStatus()));
            }
            for (Epic value : epicMap.values()) {
                writer.write(String.format("%s,%d,%s,%s,%s%n", EPIC,value.getId(),value.getTitle(),value.getDescription(),value.getStatus()));
            }
            for (Subtask value : subtaskMap.values()) {
                writer.write(String.format("%s,%d,%s,%s,%s,%d%n", SUBTASK,value.getId(),value.getTitle(),value.getDescription(),value.getStatus(),value.getIdEpic()));
            }
        } catch (IOException e) {
            throw new ManagerSaveException();
        }
    }

    @Override
    public ArrayList<Task> getTasks() {
        save();
        return super.getTasks();
    }

    @Override
    public ArrayList<Epic> getEpics() {
        save();
        return super.getEpics();
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        save();
        return super.getSubtasks();
    }

    @Override
    public void deleteTask() {
        super.deleteTask();
        save();
    }

    @Override
    public void deleteEpic() {
        super.deleteEpic();
        save();
    }

    @Override
    public void deleteSubtask() {
        super.deleteSubtask();
        save();
    }

    @Override
    public Task getTask(Integer taskId) {
        save();
        return super.getTask(taskId);
    }

    @Override
    public Epic getEpic(Integer epicId) {
        save();
        return super.getEpic(epicId);
    }

    @Override
    public Subtask getSubtask(Integer subtaskId) {
        save();
        return super.getSubtask(subtaskId);
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
        ArrayList<String> arrayList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.ready()) {
                arrayList.add(reader.readLine());
            }
            for (String string : arrayList) {
                String[] line = string.split(",");
                if (!line[0].equals("type")) {
                    if (TaskType.valueOf(line[0]).equals(TASK)) {
                        taskMap.put(Integer.parseInt(line[1]), new Task(Integer.parseInt(line[1]), line[2], line[3], Status.valueOf(line[4])));
                    }
                    if (TaskType.valueOf(line[0]).equals(EPIC)) {
                        epicMap.put(Integer.parseInt(line[1]), new Epic(Integer.parseInt(line[1]), line[2], line[3], Status.valueOf(line[4])));
                    }
                    if (TaskType.valueOf(line[0]).equals(SUBTASK)) {
                        subtaskMap.put(Integer.parseInt(line[1]), new Subtask(Integer.parseInt(line[1]), line[2], line[3], Status.valueOf(line[4]), Integer.parseInt(line[5])));
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException();
        }
        return fileBackedTaskManager;
    }
}






















/*
public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.ready()) {

                String[] line = reader.readLine().split(",");
                if (!line[0].equals("type")) {

//                    if (TaskType.valueOf(line[0]).equals(TASK)) {
//
//                        Task task = new Task(Integer.parseInt(line[1]), line[2], line[3], Status.valueOf(line[4]));
//                        if (!taskMap.containsValue(task)) {
//                            System.out.println(task);
//                            taskMap.put(Integer.parseInt(line[1]), task);
//                        }
                    // } else
                    if (!TaskType.valueOf(line[0]).equals(EPIC)) {

                        Epic epic = new Epic(Integer.parseInt(line[1]), line[2], line[3], Status.valueOf(line[4]));
                        if (!epicMap.containsValue(epic)) {
                            System.out.println(epic);
                            epicMap.put(Integer.parseInt(line[1]), epic);
                        }

                    }
//                    else if (!TaskType.valueOf(line[0]).equals(SUBTASK)) {
//                        Subtask subtask = new Subtask(Integer.parseInt(line[1]), line[2], line[3], Status.valueOf(line[4]), Integer.parseInt(line[5]));
//                        if (subtaskMap.containsValue(subtask)) {
//                            subtaskMap.put(Integer.parseInt(line[1]), subtask);
//                        }
//                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException();
        }
        return fileBackedTaskManager;
    }
 */