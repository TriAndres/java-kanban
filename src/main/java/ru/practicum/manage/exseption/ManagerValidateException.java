package ru.practicum.manage.exseption;

public class ManagerValidateException extends RuntimeException {
    public ManagerValidateException(String message) {
        super(message);
    }
}