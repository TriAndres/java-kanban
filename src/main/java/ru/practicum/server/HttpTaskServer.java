package ru.practicum.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import ru.practicum.manage.Managers;
import ru.practicum.manage.tasks.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

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

    class TaskHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

        }
    }

    public static void main(String[] args) throws IOException {
        HttpTaskServer userServer = new HttpTaskServer();
        userServer.start();
        userServer.stop();
    }

    private int parsePathId(String path) {
        try {
            return Integer.parseInt(path);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }

    public void start() {
        System.out.println("Starter HttpTaskServer" + PORT);
        System.out.println("http://localhost:" + PORT + "/task");
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(0);
        System.out.println("Остановка сервера на порту " + PORT);
    }

    private String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }

    private void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getRequestHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    enum Endpoint {

    }
}
