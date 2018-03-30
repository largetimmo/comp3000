package SmarterMonitor.view;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javafx.stage.Modality;
import javafx.stage.Stage;
import SmarterMonitor.Main;



import java.io.IOException;


public class MainWindow extends Pane {
    private Main main;
    private boolean existDialog = false;

    @FXML
    private Button killButton;
    @FXML
    private TableView<Process>processTable;
    @FXML
    private TableColumn<Process, String> pName;
    @FXML
    private TableColumn<Process, String> pID;
    @FXML
    private TableColumn<Process, String> uGroup;
    @FXML
    private TableColumn<Process, String> memory;
    @FXML
    private TableColumn<Process, Float> cpu;
    @FXML
    private TextField searchField;

    public MainWindow(){

    }

    @FXML
    private void initialize(){
        pName.setCellValueFactory(new PropertyValueFactory<Process,String>("name"));
        pID.setCellValueFactory(new PropertyValueFactory<Process,String>("pid"));
        uGroup.setCellValueFactory(new PropertyValueFactory<Process,String >("owner"));
        memory.setCellValueFactory(new PropertyValueFactory<Process, String>("memory"));
        cpu.setCellValueFactory(new PropertyValueFactory<Process,Float>("cpu"));
    }

    public void setFilter(SmarterMonitor.Main main){
        this.main = main;
        FilteredList<Process> filteredData = new FilteredList<>(main.getProcessData(), p->true);

        searchField.textProperty().addListener((ovservable, oldValue, newValue) -> {
            filteredData.setPredicate(process -> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (process.getpName().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }
                return false;
            });
        });

        SortedList<Process> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(processTable.comparatorProperty());

        processTable.setItems(sortedData);
    }

    public void checkProcess(){
        if (existDialog == true){
            return;
        }
        else {
            int tableSize = processTable.getItems().size();
            for (int i = 0; i < processTable.getItems().size(); i++) {
                if (processTable.getItems().size() < tableSize) {
                    i--;
                    tableSize = processTable.getItems().size();
                }
                if (processTable.getItems().get(i).getNeedKill() == 1) {
                    killCheckedProcess(processTable.getItems().get(i));
                }
            }
        }
    }

    private void killCheckedProcess(Process process){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/SmarterMonitor/view/AutoCheckDialogWindow.fxml"));
            AnchorPane dialog = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Auto Check Process");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(dialog);
            dialogStage.setScene(scene);
            AutoCheckDialogWindow dialogWindow = loader.getController();
            dialogWindow.setDialogStage(dialogStage);
            dialogWindow.setProcessName(process.getpName());
            dialogWindow.setMainWindow(this);
            dialogWindow.setMain(main);
            dialogWindow.setPid(process.getpID());
            dialogWindow.setProcess(process);
            existDialog = true;
            dialogStage.showAndWait();
            //return dialogStage;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void killProcess() {
        //processTable.getSelectionModel().getSelectedItem();
        if (processTable.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/SmarterMonitor/view/DialogWindow.fxml"));
                AnchorPane dialog = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Kll Process");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                Scene scene = new Scene(dialog);
                dialogStage.setScene(scene);
                DialogWindow dialogWindow = loader.getController();
                dialogWindow.setDialogStage(dialogStage);
                dialogWindow.setProcessName(processTable.getSelectionModel().getSelectedItem().getpName());
                dialogWindow.setMainWindow(this);
                dialogWindow.setMain(main);
                dialogWindow.setPid(processTable.getSelectionModel().getSelectedItem().getpID());
                dialogWindow.setProcess(processTable.getSelectionModel().getSelectedItem());
                dialogStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getSelectionPID(){
        if (processTable.getSelectionModel().getSelectedItem() != null) {
            //Testing Code
            //System.out.println("This PID id" + processTable.getSelectionModel().getSelectedItem().getpID());
            return processTable.getSelectionModel().getSelectedItem().getpID();
        }
        else {
            return 0;
        }
    }


    @FXML
    public boolean setSelection(int selectPID){
        if (selectPID > 0){
            for (int i=0;i<processTable.getItems().size();i++) {
                if (processTable.getItems().get(i).getpID() == selectPID){
                    processTable.getSelectionModel().select(processTable.getItems().get(i));
                    return true;
                }
            }
        }
        return false;
    }

    public void setExistDialog(boolean existDialog){
        this.existDialog = existDialog;
    }
}
