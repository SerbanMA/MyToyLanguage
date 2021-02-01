package View;
import View.Command.Command;

import java.util.HashMap;
import java.util.Scanner;

public class TextMenu {
    private HashMap<Integer, Command> commands;

    public TextMenu() {
        this.commands = new HashMap<>();
    }

    private void printMenu(){
        for (Command command : commands.values()){
            String line = String.format("%s:\n %s", command.getKey(), command.getDescription());
            System.out.println(line);
        }
    }

    public HashMap<Integer, Command> getCommands() { return commands;}


    public void addCommand(Command command){
        commands.put(command.getKey(), command);
    }

    public void show(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            printMenu();

            System.out.println("Input the option:");
            int key = scanner.nextInt();
            Command command = commands.get(key);
            if (command == null)
                System.out.println("Invalid Option");
            else
                command.execute();
        }
    }
}
