package SmarterMonitor;
import SmarterMonitor.controller.SystemController;
import SmarterMonitor.view.RootLayout;
import com.sun.istack.internal.localization.NullLocalizable;
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
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javafx.scene.control.TableView;
import org.apache.commons.lang.ObjectUtils;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {

    private MainWindow mainWindow = new MainWindow();
    private int rate=5000;
    Timer mTimer = new Timer();
    private Stage primaryStage;
    private BorderPane rootLayout;
    TimerTask update;
    private ObservableList<Process> processData = FXCollections.observableArrayList();
    //SystemController DATA = new SystemController();

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
    public void initRootLayout(){
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
    //Get process info example
//
//    class GetProc extends Thread{
//        int pid = -1;
//        public void setPid(int pid){
//            this.pid = pid;
//        }
//        @Override
//        public void run(){
//            String json = SystemController.getProcInfo(pid);
//            if (json.length()>10) {
//                JSONArray jsonArray = JSONArray.fromObject(json);
//                Object[] os = jsonArray.toArray();
//                JSONObject jsonObject = JSONObject.fromObject(os[0]);
//                processData.add(new Process(jsonObject.get("name").toString(), Integer.parseInt(jsonObject.get("pid").toString()), jsonObject.get("owner").toString() + "/" + jsonObject.get("ownergrp").toString(), jsonObject.get("memory").toString(), Float.parseFloat(jsonObject.get("cpu").toString())));
//                        System.out.println("Testing--------------------");
//                        System.out.println("Name: " + jsonObject.get("name"));
//                        System.out.println("pid: " + jsonObject.get("pid"));
//                        System.out.println("ownerInfo: " + jsonObject.get("owner").toString() + "/" + jsonObject.get("ownergrp").toString());
//                        System.out.println("Memory: " + jsonObject.get("memory"));
//                        System.out.println("cpu: " + jsonObject.get("cpu"));
//                        //System.out.println("ObList: " + processData.get(num).getpName());
//            }
//        }
//    }


    public void setRate(int second,Timer mTimer){
        rate = second*1000;
        mTimer.cancel();
        mTimer.purge();
        update.cancel();
        mTimer = new Timer();
        update = new DoInBackgroud();
        mTimer.schedule(update,1,rate);
    }

    private void getData(){
        update = new DoInBackgroud();
        mTimer.schedule(update, 1, rate);

    }

    public Timer getNewTimer(){
        return mTimer;
    }

    class DoInBackgroud extends TimerTask {
        private float updatef = 1.0f;

        public void setUpdatef(float f) {
            updatef = f;
        }

//        @Override
//        public void run(){
//            String JSONpids;
//            JSONpids = DATA.getallpids();
//            int[] JSONpid = getIntArray(JSONpids.substring(23, JSONpids.length()-2));
//            for (int i = 0; i< JSONpid.length; i++){
//                GetProc getProc = new GetProc();
//                getProc.setPid(JSONpid[i]);
//                processData.removeAll(processData);
//                getProc.start();
//
//            }
//        }
        @Override
        public void run() {
                String JSONStr;
            int selected=0;
            if (processData.size() != 0){
                selected = mainWindow.getSelection();
            }
                SystemController DATA = new SystemController();
                JSONStr = DATA.getallprocesses_test();
                JSONStr = JSONStr.substring(10, JSONStr.length() - 1);
                //System.out.println("Test");
                System.out.println(JSONStr);
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
                mainWindow.setSelection(selected);
                System.out.println(processData);
                System.out.println("test");
            }
//        private int[] getIntArray(String pids){
//            String[] pidArr = pids.split(",");
//            int[] pidint = new int[pidArr.length];
//            for (int i = 0; i < pidint.length; i++){
//                pidint[i] = Integer.parseInt(pidArr[i]);
//            }
//            return pidint;
//        }
    }

    public ObservableList<Process> getProcessData(){
        return processData;
    }

    public void deleteProcessData(Process data){
        processData.remove(data);
    }



    public static void main(String[] args) {
        launch(args);
    }
}
