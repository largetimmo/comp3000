package SmarterMonitor;
import SmarterMonitor.controller.SystemController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import SmarterMonitor.view.MainWindow;
import SmarterMonitor.view.Process;

import java.io.IOException;

public class Main extends Application {

    private MainWindow mainWindow;

    private Stage primaryStage;
    private BorderPane rootLayout;
    Thread update;
    private ObservableList<Process> processData = FXCollections.observableArrayList();
    @Override
    public void start(Stage primaryStage) throws Exception{
        mainWindow = new MainWindow();
        //mainWindow.init();
        this.primaryStage = primaryStage;

        this.primaryStage.setTitle("Smarter Monitor");
        this.primaryStage.show();
        initRootLayout();
        setMainWindow();

    }

    //Init the root layout
    public void initRootLayout(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            //Show the scene
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //Set MainWindow
    public void setMainWindow(){
        try{
            //Load MainWindow
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/MainWindow.fxml"));
            AnchorPane mainWindow = (AnchorPane) loader.load();
            //Set into the center of rootLayout
            rootLayout.setCenter(mainWindow);
            update = new DoInBackgroud();
            update.start();

            MainWindow controller = loader.getController();
            controller.setMain(this);
            controller.setFilter(this);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    //Get process info example
    /*
    class GetProc extends Thread{
        int pid = -1;
        public void setPid(int pid){
            this.pid = pid;
        }
        @Override
        public void run(){
            String json = SystemController.getProcInfo(pid);
        }
    }
    */

    class DoInBackgroud extends Thread {
        private float updatef = 1.0f;

        public void setUpdatef(float f) {
            updatef = f;
        }

        @Override
        public void run() {
            String JSONStr;
            SystemController DATA = new SystemController();
            JSONStr = DATA.getallprocesses_test();
            JSONStr = JSONStr.substring(10,JSONStr.length()-1);
            JSONStr = JSONStr.replaceAll("\u0001\u0006","Null");
            //System.out.println("Test");
            //System.out.println(JSONStr);
            JSONArray jsonArray = JSONArray.fromObject(JSONStr);
            Object[] os = jsonArray.toArray();
            int num=-1;  //Check how many process in the Computer
            for (int i = 0; i < os.length; i++) {
                JSONObject jsonObject = JSONObject.fromObject(os[i]);
                String checkMemory = jsonObject.get("memory").toString();
                if (!checkMemory.equals(" Null ")){
                    processData.add(new Process(jsonObject.get("name").toString(), Integer.parseInt(jsonObject.get("pid").toString()), jsonObject.get("owner").toString() + "/" + jsonObject.get("ownergrp").toString(), jsonObject.get("memory").toString(), Float.parseFloat(jsonObject.get("cpu").toString())));
                    num++;
                    System.out.println("Testing--------------------");
                    System.out.println("Name: " + jsonObject.get("name"));
                    System.out.println("pid: " + jsonObject.get("pid"));
                    System.out.println("ownerInfo: " + jsonObject.get("owner").toString() + "/" + jsonObject.get("ownergrp").toString());
                    System.out.println("Memory: " + jsonObject.get("memory"));
                    System.out.println("cpu: " + jsonObject.get("cpu"));
                    System.out.println("ObList: " + processData.get(num).getpName());
                }
                else {
                    continue;
                }

            }
        }
    }

    public ObservableList<Process> getProcessData(){
        return processData;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
