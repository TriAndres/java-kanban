package ru.practicum.model;

public class Subtask extends Task {
    private Integer idEpic;

    public Subtask(String description, Integer idEpic) {
        super(description);
        this.idEpic = idEpic;
        setName("Подзадача");
    }

    public Subtask(String name, String description, Status status, Integer idEpic) {
        super(name, description, status);
        this.idEpic = idEpic;
    }

    public Subtask(Integer id, String name, String description, Status status, Integer idEpic) {
        super(id, name, description, status);
        this.idEpic = idEpic;
    }

    public Subtask(Subtask subtask) {
        super(subtask);
        this.idEpic = subtask.idEpic;
    }

    @Override
    public Integer getIdEpic() {
        return idEpic;
    }

    @Override
    public void setIdEpic(Integer idEpic) {
        this.idEpic = idEpic;
    }

    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
    }

    @Override
    public String toString() {
        return getType() + ", id=" + id + ", name=" + name + ", description=" + description + ", status=" + status + ", epicId=" + idEpic + "\n";
    }
}