package View.Command;

import Controller.Controller;

public abstract class Command {
    private Integer key;
    private String description;

    protected Command(Integer key, String description) {
        this.key = key;
        this.description = description;
    }

    public Integer getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }

    public abstract Controller getController();

    public abstract void execute();


}
