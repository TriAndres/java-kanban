package ru.practicum.manage;

import ru.practicum.exseption.ManagerSaveException;
import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.io.*;
import java.util.ArrayList;

import static ru.practicum.manage.TaskType.*;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public static void main(String[] args) {
        File file = new File("src\\main\\java\\ru\\practicum\\manage\\fileBacked\\test.csv");
        FileBackedTaskManager manager = loadFromFile(file);


        manager.addNewTask(new Task("*** 1 ЗАДАЧА ***"));
        manager.addNewEpic(new Epic("*** 1 Эпик ***"));
        manager.addNewSubtask(new Subtask("*** 1 Подзадача ***", 1));


        for (Task task : manager.getTasks()) {
            System.out.println(task);
        }

        for (Epic epic : manager.getEpics()) {
            System.out.println(epic);
        }

        for (Subtask subtask : manager.getSubtasks()) {
            System.out.println(subtask);
        }
        System.out.println("////////////////////////////////////////////////////////////////////////////////////////////////");

        manager.addNewTask(new Task("*** 2 ЗАДАЧА ***"));
        manager.addNewEpic(new Epic("*** 2 Эпик ***"));
        manager.addNewSubtask(new Subtask("*** 2.1 Подзадача ***", 2));
        manager.addNewSubtask(new Subtask("*** 2.2 Подзадача ***", 2));


        for (Task task : manager.getTasks()) {
            System.out.println(task);
        }

        for (Epic epic : manager.getEpics()) {
            System.out.println(epic);
        }

        for (Subtask subtask : manager.getSubtasks()) {
            System.out.println(subtask);
        }


    }

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            writer.write("type,id,name,description,status,epic\n");
            for (Task task : getFileList()) {
                writer.write(Csv.toString(task));
            }
        } catch (IOException e) {
            throw new ManagerSaveException();
        }
    }

    @Override
    public ArrayList<Task> getTasks() {
        return super.getTasks();
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return super.getEpics();
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        return super.getSubtasks();
    }

    @Override
    public void deleteTask() {
        super.deleteTask();
    }

    @Override
    public void deleteEpic() {
        super.deleteEpic();
    }

    @Override
    public void deleteSubtask() {
        super.deleteSubtask();
    }

    @Override
    public Task getTask(Integer taskId) {
        return super.getTask(taskId);
    }

    @Override
    public Epic getEpic(Integer epicId) {
        return super.getEpic(epicId);
    }

    @Override
    public Subtask getSubtask(Integer subtaskId) {
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
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
    }

    @Override
    public void deleteIdTask(Integer id) {
        super.deleteIdTask(id);
    }

    @Override
    public void deleteIdEpic(Integer id) {
        super.deleteIdEpic(id);
    }

    @Override
    public void deleteIdSubtask(Integer id) {
        super.deleteIdSubtask(id);
    }


    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.ready()) {
                String[] line = reader.readLine().split(",");
                if (!line[0].equals("type")) {
                    if (TaskType.valueOf(line[0]).equals(TASK)) {
                        Task task = new Task(Integer.parseInt(line[1]), line[2], line[3], Status.valueOf(line[4]));
                        if (taskMap.containsValue(task)) {
                            taskMap.put(Integer.parseInt(line[1]), task);
                        }
                    } else if (TaskType.valueOf(line[0]).equals(EPIC)) {
                        Epic epic = new Epic(Integer.parseInt(line[1]), line[2], line[3], Status.valueOf(line[4]));
                        if (epicMap.containsValue(epic)) {
                            epicMap.put(Integer.parseInt(line[1]), epic);
                        }

                    } else if (TaskType.valueOf(line[0]).equals(SUBTASK)) {
                        Subtask subtask = new Subtask(Integer.parseInt(line[1]), line[2], line[3], Status.valueOf(line[4]), Integer.parseInt(line[5]));
                        if (subtaskMap.containsValue(subtask)) {
                            subtaskMap.put(Integer.parseInt(line[1]), subtask);
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
