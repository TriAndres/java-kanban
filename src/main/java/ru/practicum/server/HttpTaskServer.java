package ru.practicum.server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import ru.practicum.manage.Managers;
import ru.practicum.manage.tasks.TaskManager;
import ru.practicum.model.Epic;
import ru.practicum.model.Subtask;
import ru.practicum.model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.SQLOutput;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {
    public static final int PORT = 8080;
    private HttpServer httpServer;
    private TaskManager taskManager;
    private Gson gson;

    public HttpTaskServer() throws IOException {
        this(Managers.getDefault());
    }

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.taskManager = taskManager.getDefault();
        httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/task", new TaskHandler());
        gson = Managers.getGson();
    }

    public static void main(String[] args) throws IOException {
        HttpTaskServer userServer = new HttpTaskServer();
        userServer.start();
        userServer.stop();
    }

    public void start() {
        httpServer.start();
        System.out.println("Starter HttpTaskServer " + PORT);
    }

    public void stop() {
        httpServer.stop(0);
        System.out.println("Остановка сервера на порту " + PORT);
    }

    class TaskHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            System.out.println("Обработка /tasks запросов от клиента");

            String query = exchange.getRequestURI().getQuery();
            int id;

            Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod(), query);

            switch (endpoint) {
                case POST_TASK:
                    InputStream inputStreamTask = exchange.getRequestBody();
                    String bodyTask = new String(inputStreamTask.readAllBytes(), UTF_8);
                    if (bodyTask.isEmpty()) {
                        writeResponse(exchange, "Необходимо заполнить все поля задач", 400);
                        return;
                    }
                    try {
                        Task task = gson.fromJson(bodyTask, Task.class);
                        if (task.getId() == null) {
                            taskManager.addNewTask(task);
                            writeResponse(exchange,"Задача добавлена",201);
                        } else {
                            taskManager.updateTask(task);
                            writeResponse(exchange,"Задача обновлена",201);
                        }
                    } catch (JsonSyntaxException e) {
                        writeResponse(exchange,"Полцяен некорректно JSON",400);
                    }
                    break;
                case POST_EPIC:
                    InputStream inputStreamEpic = exchange.getRequestBody();
                    String bodyEpic = new String(inputStreamEpic.readAllBytes(), UTF_8);
                    if (bodyEpic.isEmpty()) {
                        writeResponse(exchange, "Необходимо заполнить все поля задач", 400);
                        return;
                    }
                    try {
                        Epic epic = gson.fromJson(bodyEpic, Epic.class);
                        if (epic.getId() == null) {
                            taskManager.addNewEpic(epic);
                            writeResponse(exchange,"Эпик добавлена",201);
                        } else {
                            taskManager.updateEpic(epic);
                            writeResponse(exchange,"Эпик обновлена",201);
                        }
                    } catch (JsonSyntaxException e) {
                        writeResponse(exchange,"Полцяен некорректно JSON",400);
                    }
                    break;
                case POST_SUBTASK:
                    InputStream inputStreamSubtask = exchange.getRequestBody();
                    String bodySubtask = new String(inputStreamSubtask.readAllBytes(), UTF_8);
                    if (bodySubtask.isEmpty()) {
                        writeResponse(exchange, "Необходимо заполнить все поля задач", 400);
                        return;
                    }
                    try {
                        Subtask subtask = gson.fromJson(bodySubtask, Subtask.class);
                        if (subtask.getId() == null) {
                            taskManager.addNewSubtask(subtask);
                            writeResponse(exchange,"Подзадача добавлена",201);
                        } else {
                            taskManager.updateSubtask(subtask);
                            writeResponse(exchange,"Подзадача обновлена",201);
                        }
                    } catch (JsonSyntaxException e) {
                        writeResponse(exchange,"Полцяен некорректно JSON",400);
                    }
                    break;
                case GET_TASK:
                    id = getId(query);
                    if (id == -1) {
                        writeResponse(exchange, "Некорректный id", 400);
                        return;
                    }
                    Task task = taskManager.getTaskId(id);
                    if (task != null) {
                        writeResponse(exchange, gson.toJson(task),200);
                    } else {
                        writeResponse(exchange, "Завача с id " + id + " не найдуна", 400);
                    }
                    break;
                case GET_EPIC:
                    id = getId(query);
                    if (id == -1) {
                        writeResponse(exchange, "Некорректный id", 400);
                        return;
                    }
                    Epic epic = taskManager.getEpicId(id);
                    if (epic != null) {
                        writeResponse(exchange, gson.toJson(epic),200);
                    } else {
                        writeResponse(exchange, "Эпик с id " + id + " не найдуна", 400);
                    }
                    break;
                case GET_SUBTASK:
                    id = getId(query);
                    if (id == -1) {
                        writeResponse(exchange, "Некорректный id", 400);
                        return;
                    }
                    Subtask subtask = taskManager.getSubtaskId(id);
                    if (subtask != null) {
                        writeResponse(exchange, gson.toJson(subtask),200);
                    } else {
                        writeResponse(exchange, "Подзадача с id " + id + " не найдуна", 400);
                    }
                    break;
                case GET_SUBTASK_EPIC:
                    id = getId(query);
                    if (id == -1) {
                        writeResponse(exchange, "Некорректный id", 400);
                        return;
                    }
                    if (taskManager.getEpicId(id) != null) {
                        writeResponse(exchange, gson.toJson(taskManager.getListSubtaskIdEpic(id)),200);
                    } else {
                        writeResponse(exchange, "Эпик с id " + id + " не найден", 404);
                    }
                    break;
                case DELETE_TASK:
                    id = getId(query);
                    if (id == -1) {
                        writeResponse(exchange, "Некоретный id", 400);
                        return;
                    }
                    if (taskManager.getTaskId(id) != null) {
                        taskManager.deleteIdTask(id);
                        writeResponse(exchange, "Задача удалена",200);
                    } else {
                        writeResponse(exchange,"Задача с id " + id + " не найдена", 400);
                    }
                    break;
                case DELETE_EPIC:
                    id = getId(query);
                    if (id == -1) {
                        writeResponse(exchange, "Некоретный id", 400);
                        return;
                    }
                    if (taskManager.getEpicId(id) != null) {
                        taskManager.deleteIdEpic(id);
                        writeResponse(exchange, "Эпик удалена",200);
                    } else {
                        writeResponse(exchange,"Эпик с id " + id + " не найдена", 400);
                    }
                    break;
                case DELETE_SUBTASK:
                    id = getId(query);
                    if (id == -1) {
                        writeResponse(exchange, "Некоретный id", 400);
                        return;
                    }
                    if (taskManager.getSubtaskId(id) != null) {
                        taskManager.deleteIdSubtask(id);
                        writeResponse(exchange, "Подзадача удалена",200);
                    } else {
                        writeResponse(exchange,"Подзадача с id " + id + " не найдена", 400);
                    }
                    break;
                case DELETE_TASKS:
                    taskManager.deleteAllTask();
                    if (taskManager.getTasks().isEmpty()) {
                        writeResponse(exchange, "Все задачи удалены", 200);
                    }
                    break;
                case DELETE_EPICS:
                    taskManager.deleteAllEpic();
                    if (taskManager.getEpics().isEmpty()) {
                        writeResponse(exchange, "Все эпики удалены", 200);
                    }
                    break;
                case DELETE_SUBTASKS:
                    taskManager.deleteAllSubtask();
                    if (taskManager.getSubtasks().isEmpty()) {
                        writeResponse(exchange, "Все подзадачи удалены", 200);
                    }
                    break;
                case GET_TASKS:
                    writeResponse(exchange, gson.toJson(taskManager.getTasks()),200);
                    break;
                case GET_EPICS:
                    writeResponse(exchange, gson.toJson(taskManager.getEpics()),200);
                    break;
                case GET_SUBTASKS:
                    writeResponse(exchange, gson.toJson(taskManager.getSubtasks()),200);
                    break;
                case GET_HISTORY:
                    writeResponse(exchange, gson.toJson(taskManager.getHistory()),200);
                    break;
                case GET_PRIORITY:
                    writeResponse(exchange, gson.toJson(taskManager.getPrioritizedTasks()),200);
                    break;
                default:
                    writeResponse(exchange, "Тауого эндпоинта не существует", 400);
                    break;
            }
        }

        private Endpoint getEndpoint(String path, String method, String query) {
            String[] pathParts = path.split("/");
            if (pathParts.length == 2) {
                return Endpoint.GET_PRIORITY;
            }
            String map = pathParts[2];
            if (query != null) {
                if (method.equals("GET")) {
                    if (map.equals("task"))  {
                        return Endpoint.GET_TASK;
                    }
                    if (map.equals("epic"))  {
                        return Endpoint.GET_EPIC;
                    }
                    if (map.equals("subtask"))  {
                        if (pathParts.length == 3) {
                            return Endpoint.GET_SUBTASKS;
                        } else {
                            return Endpoint.GET_SUBTASK_EPIC;
                        }
                    }
                }
                if (method.equals("DELETE")) {
                    if (map.equals("task")) {
                        return Endpoint.DELETE_TASK;
                    }
                    if (map.equals("epic")) {
                        return Endpoint.DELETE_EPIC;
                    }
                    if (map.equals("subtask")) {
                        return Endpoint.DELETE_SUBTASK;
                    }
                }
            } else  {
                if (method.equals("GET")) {
                    if (map.equals("task")) {
                        return Endpoint.GET_TASKS;
                    }
                    if (map.equals("epic")) {
                        return Endpoint.GET_EPICS;
                    }
                    if (map.equals("subtask")) {
                        return Endpoint.GET_SUBTASKS;
                    }
                    if (map.equals("history")) {
                        return Endpoint.GET_HISTORY;
                    }
                }
                if (method.equals("POST")) {
                    if (map.equals("task")) {
                        return Endpoint.POST_TASK;
                    }
                    if (map.equals("epic")) {
                        return Endpoint.POST_EPIC;
                    }
                    if (map.equals("subtask")) {
                        return Endpoint.POST_SUBTASK;
                    }
                }
                if (method.equals("DELETE")) {
                    if (map.equals("task")) {
                        return Endpoint.DELETE_TASKS;
                    }
                    if (map.equals("epic")) {
                        return Endpoint.DELETE_EPICS;
                    }
                    if (map.equals("subtask")) {
                        return Endpoint.DELETE_SUBTASKS;
                    }
                }
            }
            return Endpoint.UNKNOWN;
        }
    }

    private void writeResponse(HttpExchange exchange, String text, int code) throws IOException {
        byte[] bytes = text.getBytes(UTF_8);
        exchange.sendResponseHeaders(code, bytes.length);
        try (OutputStream os = exchange.getResponseBody()){
            os.write(bytes);
        }
        exchange.close();
    }

    private int getId(String query) {
        try {
            return Optional.of(Integer.parseInt(query.replaceFirst("id=",""))).get();
        } catch (NumberFormatException exception) {
            return -1;
        }
    }

    enum Endpoint {
        DELETE_TASKS, DELETE_SUBTASKS, DELETE_EPICS, GET_TASKS, GET_EPICS, GET_SUBTASKS, GET_TASK, GET_EPIC,
        GET_SUBTASK, GET_SUBTASK_EPIC, DELETE_TASK, DELETE_EPIC, DELETE_SUBTASK, POST_TASK, POST_SUBTASK,
        POST_EPIC, GET_HISTORY, GET_PRIORITY, UNKNOWN
    }
}
