package ru.practicum.exseption;

public class ManagerSaveException extends RuntimeException{
    public ManagerSaveException(final String message) {
        super(message);
    }

    public ManagerSaveException() {
        super();
    }
}
