package ru.practicum.model;

public class Subtask extends Task {
    private Integer idEpic;

    public Subtask(String description, Integer idEpic) {
        super(description);
        this.idEpic = idEpic;
        setTitle("Подзадача");
    }

    public Subtask(Integer id, String title, String description, Status status, Integer idEpic) {
        super(id, title, description, status);
        this.idEpic = idEpic;
    }

    public Integer getIdEpic() {
        return idEpic;
    }

    public void setIdEpic(Integer idEpic) {
        this.idEpic = idEpic;
    }

    @Override
    public String toString() {
        return TaskType.SUBTASK + ", id=" + id + ", name=" + title + ", description=" + description + ", status=" + status + ", epicId=" + idEpic + "\n";
    }
}