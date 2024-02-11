package ru.practicum.manage;

import ru.practicum.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    class Node<E> {
        public E data;
        public Node<E> next;
        public Node<E> prev;

        public Node(Node<E> prev, E data, Node<E> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node<Task> head;
    private Node<Task> tail;
    private int size = 0;
    private final Map<Integer, Task> taskMap;

    public InMemoryHistoryManager() {
        taskMap = new HashMap<>();
    }

    private void addFirst(Task task) {
        final Node<Task> oldHead = head;
        final Node<Task> newNode = new Node<>(null, task, oldHead);
        head = newNode;
        if (oldHead == null)
            head = newNode;
        else
            oldHead.prev = newNode;
        size++;
    }

    @Override
    public void add(Task task) {
        addFirst(task);
        taskMap.put(size, head.data);
        if (taskMap.size() > 10) {
            remove(taskMap.size() - 10);
        }
    }

    @Override
    public void remove(int id) {
        taskMap.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(taskMap.values());
    }
}