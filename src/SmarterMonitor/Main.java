package SmarterMonitor;

import SmarterMonitor.controller.SystemController;
import SmarterMonitor.view.RootLayout;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import SmarterMonitor.view.MainWindow;
import SmarterMonitor.view.Process;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {

    private MainWindow mainWindow = new MainWindow();
    private int rate=5000;
    Timer mTimer = new Timer();
    TimerTask update;
    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<Process> processData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception{

        //mainWindow.init();
        this.primaryStage = primaryStage;

        this.primaryStage.setTitle("Smarter Monitor");
        this.primaryStage.show();
        initRootLayout();
        mainWindow = setMainWindow();


    }

    //Init the root layout
    private void initRootLayout(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            //Show the scene
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            RootLayout controller = loader.getController();
            controller.setMain(this);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //Set MainWindow
    private MainWindow setMainWindow(){
        try{
            //Load MainWindow
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/MainWindow.fxml"));
            AnchorPane mainWindow = (AnchorPane) loader.load();
            //Set into the center of rootLayout
            rootLayout.setCenter(mainWindow);
            //Data of table view
            getData();
            MainWindow controller = loader.getController();
            //controller.setMain(this);
            controller.setFilter(this);
            return controller;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    //Set the refresh rate
    public void setRate(int second,Timer mTimer){
        rate = second*1000;
        mTimer.cancel();
        mTimer.purge();
        update.cancel();
        mTimer = new Timer();
        update = new DoInBackgroud();
        mTimer.schedule(update,1,rate);
    }

    //Get Data from System every RATE second.
    private void getData(){
        update = new DoInBackgroud();
        mTimer.schedule(update, 1, rate);
    }


    public Timer getNewTimer(){
        return mTimer;
    }

    //Back Ground. A timerTask to get the process info from system
    class DoInBackgroud extends TimerTask {
        private float updatef = 1.0f;
        public void setUpdatef(float f) {
            updatef = f;
        }

        @Override
        public void run() {
                String JSONStr;
            int selectedPid=0;
            if (processData.size() != 0){
                selectedPid = mainWindow.getSelectionPID();
            }
                SystemController DATA = new SystemController();
                JSONStr = DATA.getallprocesses_test();  //In Linux, this function should be DATA.getallprocesses() TODO
                JSONStr = JSONStr.substring(10, JSONStr.length() - 1);
                //Testing Code
                //System.out.println("Test");
                //System.out.println(JSONStr);
                JSONArray jsonArray = JSONArray.fromObject(JSONStr);
                Object[] os = jsonArray.toArray();
                int num = -1;  //Check how many process in the Computer
                processData.removeAll(processData);
                for (int i = 0; i < os.length; i++) {
                    JSONObject jsonObject = JSONObject.fromObject(os[i]);
                    String checkMemory = jsonObject.get("memory").toString().substring(jsonObject.get("memory").toString().length()-3,jsonObject.get("memory").toString().length()-1);
                    if (checkMemory.equals("kB")) {
                        processData.add(new Process(jsonObject.get("name").toString(), Integer.parseInt(jsonObject.get("pid").toString()), jsonObject.get("owner").toString() + "/" + jsonObject.get("ownergrp").toString(), jsonObject.get("memory").toString(), Float.parseFloat(jsonObject.get("cpu").toString())));
                        num++;
                            //Testing Code
//                        System.out.println("Testing--------------------");
//                        System.out.println("Name: " + jsonObject.get("name"));
//                        System.out.println("pid: " + jsonObject.get("pid"));
//                        System.out.println("ownerInfo: " + jsonObject.get("owner").toString() + "/" + jsonObject.get("ownergrp").toString());
//                        System.out.println("Memory: " + jsonObject.get("memory"));
//                        System.out.println("cpu: " + jsonObject.get("cpu"));
//                        System.out.println("ObList: " + processData.get(num).getpName());
                    } else {
                        continue;
                    }
                }
                mainWindow.setSelection(selectedPid);
                //Testing Code
                //System.out.println(processData);
                //System.out.println("test");
            }
    }

    public ObservableList<Process> getProcessData(){
        return processData;
    }

    public void deleteProcessData(int pid){
        for (int i=0;i<processData.size();i++){
            if (processData.get(i).getpID() == pid){
                processData.remove(processData.get(i));
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
