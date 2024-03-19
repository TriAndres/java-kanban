package ru.practicum;

import ru.practicum.manage.FileBackedTaskManager;
import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Scanner;

import static ru.practicum.manage.FileBackedTaskManager.loadFromFile;

public class App {
    public static void main(String[] args) {
        File file = new File("src\\main\\java\\ru\\practicum\\manage\\test.csv");
        FileBackedTaskManager manager = loadFromFile(file);
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
                    manager.addNewTask(new Task(
                            "Task 1", "Задача 1",
                            Status.NEW,
                            LocalDateTime.now(),
                            1L
                    ));
                    Epic epic2 = new Epic(
                            "Epic 2", "Эпик 2",
                            Status.NEW,
                            LocalDateTime.now(),
                            2L
                            );
                    manager.addNewEpic(epic2);

                    manager.addNewSubtask(new Subtask(
                            "Subtask 3", "Субтаск 3",
                            Status.NEW,
                            LocalDateTime.now(),
                            3L,
                            epic2.getId()

                    ));
                    System.out.println(manager.getTaskId(1));
                    System.out.println(manager.getEpicId(2));
                    System.out.println(manager.getSubtaskId(3));
                    break;
                case "2":
                    System.out.println(manager.getTaskId(1));
                    System.out.println(manager.getEpicId(2));
                    System.out.println(manager.getSubtaskId(3));
                    break;
            }
        }
    }
}