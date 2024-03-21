package manage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.manage.InMemoryHistoryManager;
import ru.practicum.model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.practicum.model.Status.NEW;

class InMemoryHistoryManagerTest {
    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

    @BeforeEach
    public void beforeEach() {
        inMemoryHistoryManager.add(new Task(1, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now()));
        inMemoryHistoryManager.add(new Task(2, "2", "Задача", NEW, Duration.ZERO, LocalDateTime.now()));
        inMemoryHistoryManager.add(new Task(3, "3", "Задача", NEW, Duration.ZERO, LocalDateTime.now()));
    }

    @Test
    public void add() {
        int sizeList = inMemoryHistoryManager.getHistory().size();

        assertTrue(sizeList > 0);

        inMemoryHistoryManager.add(new Task(4, "4", "Задача", NEW, Duration.ZERO, LocalDateTime.now()));

        assertTrue(sizeList < inMemoryHistoryManager.getHistory().size());

    }

    @Test
    public void remove() {
        int sizeList = inMemoryHistoryManager.getHistory().size();

        assertTrue(sizeList > 0);

        inMemoryHistoryManager.remove(sizeList);

        assertTrue(sizeList > inMemoryHistoryManager.getHistory().size());
    }

    @Test
    public void getHistory() {
//        List<Task> listOne = inMemoryHistoryManager.getHistory();
//
//        List<Task> listTwo = new ArrayList<>(List.of(
//                new Task(1, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now()),
//                new Task(2, "2", "Задача", NEW, Duration.ZERO, LocalDateTime.now()),
//                new Task(3, "3", "Задача", NEW, Duration.ZERO, LocalDateTime.now())
//        ));
//
//        assertIterableEquals(listOne, listTwo);
//
//        inMemoryHistoryManager.add(new Task(3, "3", "Задача Задача", NEW, Duration.ZERO, LocalDateTime.now()));
//
//        List<Task> listThree = inMemoryHistoryManager.getHistory();
//
//        List<Task> listFour = new ArrayList<>(List.of(
//                new Task(1, "1", "Задача", NEW, Duration.ZERO, LocalDateTime.now()),
//                new Task(2, "2", "Задача", NEW, Duration.ZERO, LocalDateTime.now()),
//                new Task(3, "3", "Задача Задача", NEW, Duration.ZERO, LocalDateTime.now())
//        ));
//
//        assertIterableEquals(listThree, listFour);
    }
}