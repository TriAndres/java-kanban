package ru.practicum;

import ru.practicum.manage.InMemoryTaskManager;
import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

public class App {
    public static void main(String[] args) {
        InMemoryTaskManager manage = new InMemoryTaskManager();

        System.out.println("///////////////////////////////////////////////");
        System.out.println("\nСоздаём 4 задачи");
        manage.addNewTask(new Task("*** 1 ЗАДАЧА ***"));
        Task task1 = manage.getTask(1);

        manage.addNewTask(new Task("*** 2 ЗАДАЧА ***"));
        Task task2 = manage.getTask(2);

        manage.addNewTask(new Task("*** 3 ЗАДАЧА ***"));
        Task task3 = manage.getTask(3);

        manage.addNewTask(new Task("*** 4 ЗАДАЧА ***"));
        Task task4 = manage.getTask(4);

        System.out.println("///////////////////////////////////////////////");
        System.out.println("\nВывод истории 2 getHistory():");
        for (Task task : manage.getHistory()) {
            System.out.println(task);
        }
        System.out.println("///////////////////////////////////////////////");
    }
}