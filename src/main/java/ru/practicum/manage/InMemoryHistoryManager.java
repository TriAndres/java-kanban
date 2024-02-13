package ru.practicum.manage;

import ru.practicum.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private static class Node<E> {
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
    private final Map<Integer, Node<Task>> taskMap = new HashMap<>();

    private void linkLast(Task task) {
        if (head == null) {
            head = new Node<>(null, task, null);
            tail = head;
        } else if (head.next == null) {
            tail = new Node<>(head, task, null);
            head.next = tail;
        }
    }

    @Override
    public void add(Task task) {
        if (taskMap.containsKey(task.getId())) {
            remove(task.getId());
        }
        if (task != null) {
            linkLast(task);
            taskMap.put(task.getId(), tail);
        }
    }

    @Override
    public void remove(int id) {
        if (taskMap.containsKey(id)) {
            removeNode(taskMap.get(id));
            taskMap.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> list = new ArrayList<>();
        Node<Task> currentNode = head;
        list.add(head.data);
        while (currentNode.next != null) {
            currentNode = currentNode.next;
            list.add(currentNode.data);
        }
        return list;
    }

    public void removeNode(Node<Task> node) {
        if (node.prev != null && node.next != null) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        } else if (node.prev != null) {
            tail = node.prev;
        } else if (node.next != null) {
            head = node.next;
        }
    }
}