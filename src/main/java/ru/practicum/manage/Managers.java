package ru.practicum.manage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.practicum.manage.history.HistoryManager;
import ru.practicum.manage.history.InMemoryHistoryManager;
import ru.practicum.manage.tasks.InMemoryTaskManager;
import ru.practicum.manage.tasks.TaskManager;
import ru.practicum.model.LocalDateAdapter;

import java.time.LocalDateTime;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter());
        return gsonBuilder.create();
    }
}