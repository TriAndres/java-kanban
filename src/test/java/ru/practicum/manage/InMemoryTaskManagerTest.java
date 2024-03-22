package ru.practicum.manage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @BeforeEach
    public void beforeEach() {
        manager = new InMemoryTaskManager();
    }

    @Test
    public void addNewTask() {
        super.addNewTask();
    }

    @Test
    public void addNewEpic() {
        super.addNewEpic();
    }

    @Test
    public void addNewSubtask() {
        super.addNewSubtask();
    }

    @Test
    public void deleteIdTask() {
        super.deleteIdTask();

    }

    @Test
    public void deleteIdEpic() {
        super.deleteIdEpic();
    }

    @Test
    public void deleteIdSubtask() {
        super.deleteIdSubtask();
    }

    @Test
    public void updateTask() {
        super.updateTask();
    }

    @Test
    public void updateEpic() {
        super.updateEpic();
    }

    @Test
    public void updateSubtask() {
        super.updateSubtask();
    }

    @Test
    void deleteAllTask() {
        super.deleteAllTask();
    }

    @Test
    void deleteAllEpic() {
        super.deleteAllEpic();
    }

    @Test
    void deleteAllSubtask() {
        super.deleteAllSubtask();
    }

    @Test
    public void getListSubtaskIdEpic() {
        super.getListSubtaskIdEpic();
    }
}