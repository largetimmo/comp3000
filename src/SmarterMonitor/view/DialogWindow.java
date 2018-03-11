package SmarterMonitor.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;

public class DialogWindow extends Pane{

    @FXML
    private javafx.scene.text.Text textAlret;
    @FXML
    private Button killButton;
    @FXML
    private Button keepButton;

    private Stage dialogStage;
    private String processName;
    private MainWindow mainWindow;

    public DialogWindow(){

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setProcessName (String processName){
        this.processName = processName;
        textAlret.setText(processName + " need be killed");
    }

    public void setMainWindow(MainWindow mainWindow){
        this.mainWindow = mainWindow;
    }



    @FXML
    private void initialize() {
    }

    @FXML
    private void keepProcess(){
        //dialogStage.setTitle("Test");
        dialogStage.close();
    }

    @FXML
    private void killProcess(){
        //TODO kill the process
        int pid;
        pid = mainWindow.getSelectionPID();
        //System.out.println(pid);
        dialogStage.close();
    }

}
