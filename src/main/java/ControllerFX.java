
import Model.ADT.Dictionary.MyDictionary;
import Model.ProgramState;
import Model.Statement.IStatement;
import View.Command.Command;
import View.Command.RunExampleCommand;
import View.TextMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.text.Style;
import java.io.IOException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class ControllerFX {

    @FXML private CheckBox fashion;
    @FXML private ListView menuListView;
    @FXML private Button goButton;

    private ArrayList<Stage> stageList = new ArrayList<>();
    private Stage primaryStage;
    private TextMenu menu;

    private final ObservableList<RunExampleCommand> listOfCommands = FXCollections.observableArrayList();


    @FXML public void initialize() {
        menuListView.setItems(listOfCommands);
    }

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void setMenu(TextMenu menu) {
        this.menu = menu;
    }

    public void setListOfCommands() {
        HashMap<Integer, Command> commands =  menu.getCommands();
        ArrayList<RunExampleCommand> arrayList = new ArrayList(commands.values());
        listOfCommands.addAll(arrayList);
    }

    public void pressGoButton() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("run.fxml"));
            Parent root = loader.load();

            ControllerRunFX controllerRunFX = loader.getController();

            // -- controler
            var index = menuListView.getSelectionModel().getSelectedIndex() + 1;
            ((IStatement)menu.getCommands().get(index).getController().getRepository().getProgramStates().get(0).getExecutionStack().getStack().get(0)).typeCheck(new MyDictionary<>());

            controllerRunFX.setNoOfProgramStates();
            controllerRunFX.setController(menu.getCommands().get(index).getController());
            controllerRunFX.setCurrentProgramStateIndex(0);
            controllerRunFX.setProgramStatesId();
            controllerRunFX.setExecutionStack();
            controllerRunFX.setSymbolTable();
            controllerRunFX.setOutput();
            controllerRunFX.setFileTable();
            controllerRunFX.setHeap();
            controllerRunFX.setSemaphore();
            controllerRunFX.programStatesId_ListView.getSelectionModel().select(0);

            // -- stage
            Stage stage = new Stage();
            stageList.add(stage);

            // position
            stage.setX(primaryStage.getX() + primaryStage.getWidth());
            stage.setY(primaryStage.getY());

            // icon
            stage.getIcons().add(new Image(getClass().getResourceAsStream("Style/Icon.jpg")));
            stage.setTitle("Run");

            // when close
            stage.setOnHidden(e -> {controllerRunFX.shutdown(); removeStage(stage);});

            // run
            stage.setScene(new Scene(root));
            stage.show();

            setMode();

        }catch (Exception exception){
            if (menuListView.getSelectionModel().getSelectedIndex() == -1)
                showErrorMessage("No program state was selected.");
            else
                showErrorMessage("Statement " + Integer.toString(menuListView.getSelectionModel().getSelectedIndex() + 1) + " don't pass the type check");
        }
    }

    // design elements
    void removeStage(Stage stage){
        stageList.remove(stage);
    }

    void setFashion(){
        fashion.setSelected(true);
    }

    public void setMode(){
        if (fashion.isSelected())
            setDarkMode();
        else
            setLightMode();
    }

    public void setDarkMode() {
        // remove
        primaryStage.getScene().getStylesheets().remove("Style/main_window_light.css");
        for (var stage : stageList){
            stage.getScene().getStylesheets().remove("Style/run_window_light.css");
        }

        // add
        primaryStage.getScene().getStylesheets().setAll("Style/main_window_dark.css");
        for (var stage : stageList){
            stage.getScene().getStylesheets().setAll("Style/run_window_dark.css");
        }
    }

    public void setLightMode(){
        // remove
        primaryStage.getScene().getStylesheets().remove("Style/main_window_dark.css");
        for (var stage : stageList){
            stage.getScene().getStylesheets().remove("Style/run_window_dark.css");
        }

        // add
        primaryStage.getScene().getStylesheets().setAll("Style/main_window_light.css");
        for (var stage : stageList){
            stage.getScene().getStylesheets().setAll("Style/run_window_light.css");
        }
    }


    void showErrorMessage (String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);

        if (fashion.isSelected()) {
            alert.getDialogPane().getStylesheets().add(getClass().getResource("Style/dialog_pane_dark.css").toExternalForm());
            alert.getDialogPane().setGraphic(new ImageView("Style/Dark_Alert.png"));
        }
        else {
            alert.getDialogPane().getStylesheets().add(getClass().getResource("Style/dialog_pane_light.css").toExternalForm());
            alert.getDialogPane().setGraphic(new ImageView("Style/Light_Alert.png"));
        }

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("Style/Icon.jpg")));
        stage.setX(primaryStage.getX() + primaryStage.getWidth());
        stage.setY(primaryStage.getY());

        alert.setHeaderText("  Error \uD83D\uDE41 ");
        alert.setTitle("Error Message");
        alert.setContentText(text);
        alert.showAndWait();
    }
}

