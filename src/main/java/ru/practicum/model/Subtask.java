package ru.practicum.model;

public class Subtask extends Task {
    private Integer idEpic;

    public Subtask(String description, Integer idEpic) {
        super(description);
        this.idEpic = idEpic;
        setTitle("Подзадача");
    }

    public Subtask(Integer id, String description, Integer idEpic) {
        super(id, description);
        this.idEpic = idEpic;
        setTitle("Подзадача");
    }

    public Subtask(String title, String description, Status status) {
        super(title,description,status);
        this.id = getId();
    }

    public Integer getIdEpic() {
        return idEpic;
    }

    public void setIdEpic(Integer idEpic) {
        this.idEpic = idEpic;
    }
}