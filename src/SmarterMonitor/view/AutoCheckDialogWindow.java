package SmarterMonitor.view;

import SmarterMonitor.Main;
import SmarterMonitor.controller.SystemController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AutoCheckDialogWindow {


    @FXML
    private javafx.scene.text.Text textAlret;
    @FXML
    private Button killButton;
    @FXML
    private Button keepButton;

    private Stage dialogStage;
    private String processName;
    private MainWindow mainWindow;
    private SystemController systemController;
    private Main main;
    private int pid;
    private Process process;

    public AutoCheckDialogWindow(){

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setProcessName (String processName){
        this.processName = processName;
        textAlret.setText("The CPU usage of " + processName + " is more than 150. Do you want to kill it?");
    }

    public void setMainWindow(MainWindow mainWindow){
        this.mainWindow = mainWindow;
    }

    public void setMain(Main main){
        this.main = main;
    }

    public void setPid(int pid){
        this.pid = pid;
    }

    public void setProcess(Process process){
        this.process = process;
    }


    @FXML
    private void initialize() {
    }

    @FXML
    private void keepProcess(){
        //dialogStage.setTitle("Test");
        if (process.getNeedKill() == 1){
            main.setNeedKill(pid);
        }
        mainWindow.setExistDialog(false);
        dialogStage.close();
    }

    @FXML
    private void killProcess(){
        //TODO kill the process
        //int pid;
        //pid = mainWindow.getSelectionPID();
        System.out.println(pid);
        systemController = new SystemController();
        //systemController.killProcess(pid);   //In Linux, this line shouldn't comment.  TODO
        //main.deleteProcessData(mainWindow.getSelectionPro());
        main.deleteProcessData(pid);
        mainWindow.setExistDialog(false);
        dialogStage.close();
    }
}
