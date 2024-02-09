package ru.practicum;

import ru.practicum.manage.InMemoryTaskManager;
import ru.practicum.model.Epic;
import ru.practicum.model.Status;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager manage = new InMemoryTaskManager();

        //Создайте две задачи, а также эпик с двумя подзадачами и эпик с одной подзадачей.
        System.out.println("\nСоздаём две задачи");
        manage.addNewTask(new Task("*** 1 ЗАДАЧА ***"));
        Task task1 = manage.getTask(1);

        manage.addNewTask(new Task("*** 2 ЗАДАЧА ***"));
        Task task2 = manage.getTask(2);

        System.out.println("\nСоздайте эпик с двумя подзадачами");
        manage.addNewEpic(new Epic("*** 1 эпик ***"));
        Epic epic1 = manage.getEpic(1);

        manage.addNewSubtask(new Subtask("1 подзадача", 1));
        Subtask subtask1 = manage.getSubtask(1);
        subtask1.setStatus(Status.DONE);
        manage.statusEpic(epic1);////

        manage.addNewSubtask(new Subtask("2 подзадача", 1));
        Subtask subtask2 = manage.getSubtask(2);
        subtask2.setStatus(Status.IN_PROGRESS);
        manage.statusEpic(epic1);////

        System.out.println("\nСоздайте эпик с одной подзадачей");
        manage.addNewEpic(new Epic("*** 2 эпик ***"));
        Epic epic2 = manage.getEpic(2);

        manage.addNewSubtask(new Subtask("3 подзадача", 2));
        Subtask subtask3 = manage.getSubtask(3);
        subtask3.setStatus(Status.DONE);
        manage.statusEpic(epic2);////

        //////////////////////////////////////////////////////////////////////////////////////
        manage.addNewTask(new Task("*** 3 ЗАДАЧА ***"));
        Task task3 = manage.getTask(3);

        System.out.println("\nСоздайте эпик с одной подзадачей");
        manage.addNewEpic(new Epic("*** 3 эпик ***"));
        Epic epic3 = manage.getEpic(3);

        manage.addNewSubtask(new Subtask("1 подзадача", 3));
        Subtask subtask4 = manage.getSubtask(4);
        subtask4.setStatus(Status.DONE);
        manage.statusEpic(epic3);////


//////////////////////////////////////////////////////////////////////////////////////
        manage.addNewTask(new Task("*** 4 ЗАДАЧА ***"));
        Task task4 = manage.getTask(4);

        System.out.println("\nСоздайте эпик с одной подзадачей");
        manage.addNewEpic(new Epic("*** 4 эпик ***"));
        Epic epic4 = manage.getEpic(4);

//        ru.practicum.manage.addSubtask(new ru.practicum.model.Subtask("1 подзадача", 4));
//        ru.practicum.model.Subtask subtask5 = ru.practicum.manage.getIdSubtask(5);
//        subtask5.setStatus(ru.practicum.model.Status.DONE);
//        ru.practicum.manage.statusEpic(epic4);////


//////////////////////////////////////////////////////////////////////////////////////

        //Распечатайте списки эпиков, задач и подзадач через System.out.println(..).
        System.out.println("\nРаспечатайте списки эпиков, задач и подзадач");
        System.out.println(manage.getTasks());
        System.out.println(manage.getEpics());
        System.out.println(manage.getSubtasks());

        //Измените статусы созданных объектов, распечатайте их. Проверьте, что статус задачи
        // и подзадачи сохранился, а статус эпика рассчитался по статусам подзадач.
        System.out.println("\nГруппа задачи 1, вывод 1 раз:");
        System.out.println(task1);
        System.out.println(epic1);
        for (Subtask subtask : manage.getListSubtaskIdEpic(1)) {
            System.out.println(subtask);
        }

        System.out.println("\nГруппа задачи 1, меняем статус 1 раз");
        subtask1.setStatus(Status.IN_PROGRESS);
        subtask2.setStatus(Status.DONE);
        manage.statusEpic(epic1);////

        System.out.println("\nГруппа задачи 1, вывод 2 раз:");
        System.out.println(task1);
        System.out.println(epic1);
        for (Subtask subtask : manage.getListSubtaskIdEpic(1)) {
            System.out.println(subtask);
        }

        System.out.println("\nГруппа задачи 1, меняем статус 2 раз");
        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        manage.statusEpic(epic1);////

        System.out.println("\nГруппа задачи 1, вывод 3 раз:");
        System.out.println(task1);
        System.out.println(epic1);
        for (Subtask subtask : manage.getListSubtaskIdEpic(1)) {
            System.out.println(subtask);
        }

        //И, наконец, попробуйте удалить одну из задач и один из эпиков.
        System.out.println("\nГруппа задачи 2, вывод 1 раз:");
        System.out.println(task2);
        System.out.println(epic2);
        for (Subtask subtask : manage.getListSubtaskIdEpic(epic2.getId())) {
            System.out.println(subtask);
        }

        System.out.println("\nГруппа задачи 2, меняем статус 1 раз");
        subtask3.setStatus(Status.IN_PROGRESS);
        manage.statusEpic(epic2);////


        System.out.println("\nГруппа задачи 2, вывод 2 раз:");
        System.out.println(task2);
        System.out.println(epic2);
        for (Subtask subtask : manage.getListSubtaskIdEpic(epic2.getId())) {
            System.out.println(subtask);
        }


        System.out.println("\nГруппа задачи 2, удаление эпика,  вывод задачи 2 и вывод списков эпика и подзадач 1 раз:");
        System.out.println(task2);
        manage.deleteIdEpic(task2.getId());
        System.out.println(manage.getEpics());
        System.out.println(manage.getSubtasks());

        System.out.println("\nГруппа задачи 2, удаление задачи 2, вывод списков задач, эпиков и подзадач  1 раз:");
        manage.deleteIdTask(task2.getId());
        System.out.println(manage.getTasks());
        System.out.println(manage.getEpics());
        System.out.println(manage.getSubtasks());


        System.out.println("////");
        System.out.println("\nВывод истории 2 getHistory():");
        for (Task task : manage.getHistory()) {
            System.out.println(task);
        }
    }
}