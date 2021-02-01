package View.Command;

import Controller.Controller;

public class RunExampleCommand extends Command {
    private final Controller controller;

    public RunExampleCommand(Integer key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    @Override
    public void execute() {
        try{
            controller.allSteps();

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public String toString() {
        return getKey() + " _ int main() {\n" + getDescription() + "\n}";
    }
}
