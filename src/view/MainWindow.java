package view;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.xml.soap.Text;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow extends Pane {

    @FXML
    private Button killButton;

    public MainWindow(){

    }

    public void init(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("RootLayout.fxml"));
            BorderPane layout = (BorderPane) loader.load();
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("MainWindow.fxml"));
            AnchorPane table = (AnchorPane) loader.load();
            layout.setCenter(table);
            getChildren().addAll(layout);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }



    @FXML
    private void killProcess(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("DialogWindow.fxml"));
            AnchorPane dialog = (AnchorPane)loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Kll Process");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(dialog);
            dialogStage.setScene(scene);
            DialogWindow dialogWindow = loader.getController();
            dialogWindow.setDialogStage(dialogStage);
            dialogWindow.setProcessName("Process Name");
            dialogStage.showAndWait();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    class DoInBackgroud extends Thread{
        private float updatef = 1.0f;
        public void setUpdatef(float f){
            updatef = f;
        }
        @Override
        public void run(){
            /**
             *
             * JSON format example
             * {
             "result": [{
             "cpu": 5.66,
             "memory": "3.22GB",
             "name": "chrome",
             "pid": 1234,
             "owner": "root",
             "ownergrp": "root"
             }, {
             "cpu": 45.66,
             "memory": "32.22GB",
             "name": "chrome",
             "pid": 12343,
             "owner": "root",
             "ownergrp": "root"
             }]
             }
             *
             *
             * TODO:Implement here
             * Step:
             * 1.get data from SystemController
             * 2.the data will be json formatted string,so parse it
             * 3.add the data to layout
             */
            controller = new SystemController();
            String JSONStr = controller.getallprocesses();
            JSONObject jsonObject = JSONObject.fromObject(JSONStr);



        }
    }




}
