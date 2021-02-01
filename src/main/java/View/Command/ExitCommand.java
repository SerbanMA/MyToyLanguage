package View.Command;

import Controller.Controller;

public class ExitCommand extends Command{

    public ExitCommand(Integer key, String description){super(key, description);}

    public Controller getController() {
        return null;
    }

    @Override
    public void execute() {
        System.exit(0);
    }

    @Override
    public String toString() {
        return "exit";
    }
}
