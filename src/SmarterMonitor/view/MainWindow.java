package SmarterMonitor.view;


import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import SmarterMonitor.Main;
import org.apache.commons.lang.ObjectUtils;


import java.io.IOException;


public class MainWindow extends Pane {
    private Main main;
//    Thread update;
//    class Process{
//        private final StringProperty pName;
//        private final IntegerProperty pID;
//        private final StringProperty ownerInfo;
//
//        private final StringProperty memory;
//        private final FloatProperty cpu;
//
//        public Process(String pName, int pID, String ownerInfo, String memory, float cpu){
//            this.pName = new SimpleStringProperty(pName);
//            this.pID = new SimpleIntegerProperty(pID);
//            this.ownerInfo = new SimpleStringProperty(ownerInfo);
//            this.memory = new SimpleStringProperty(memory);
//            this.cpu = new SimpleFloatProperty(cpu);
//        }
//
//        public void setpName(String Name){
//            pName.set(Name);
//        }
//        public String getpName(){
//            return pName.get();
//        }
//        public void setpID(int id){
//            pID.set(id);
//        }
//        public Integer getpID(){
//            return pID.get();
//        }
//        public void setOwnerInfo(String owner){
//            ownerInfo.set(owner);
//        }
//        public String getonwerInfo(){
//            return ownerInfo.get();
//        }
//        public void setMemory(String memo){
//            memory.set(memo);
//        }
//        public String getMemory(){
//            return memory.get();
//        }
//        public void setCpu(float Cpu){
//            cpu.set(Cpu);
//        }
//        public float getCpu(){
//            return cpu.get();
//        }
//
//    }

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

//    public void init(){
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("RootLayout.fxml"));
//            BorderPane layout = (BorderPane) loader.load();
//            loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("MainWindow.fxml"));
//            AnchorPane table = (AnchorPane) loader.load();
//            layout.setCenter(table);
//            //Update the data from system to List
//            update = new DoInBackgroud();
//            update.start();
//
//
//
//
//            getChildren().addAll(layout);
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//
//    }

    @FXML
    private void initialize(){
        pName.setCellValueFactory(new PropertyValueFactory<Process,String>("name"));
        pID.setCellValueFactory(new PropertyValueFactory<Process,String>("pid"));
        uGroup.setCellValueFactory(new PropertyValueFactory<Process,String >("owner"));
        memory.setCellValueFactory(new PropertyValueFactory<Process, String>("memory"));
        cpu.setCellValueFactory(new PropertyValueFactory<Process,Float>("cpu"));



    }



    public void setMain(SmarterMonitor.Main main){
        this.main = main;
        //System.out.println(main.getProcessData().get(0).getpName());
        processTable.setItems(main.getProcessData());
    }

    public void setFilter(SmarterMonitor.Main main){
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



    @FXML
    private void killProcess() {
        //processTable.getSelectionModel().getSelectedItem();
        if (processTable.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("DialogWindow.fxml"));
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
                dialogStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public int getSelectionPID(){
        return processTable.getSelectionModel().getSelectedItem().getpID();
    }

//    class DoInBackgroud extends Thread{
//        private float updatef = 1.0f;
//        ObservableList<Process> processData = FXCollections.observableArrayList();
//        public void setUpdatef(float f){
//            updatef = f;
//        }
//        @Override
//        public void run() {
//            /**
//             *
//             * JSON format example
//             * {
//             "result": [{
//             "cpu": 5.66,
//             "memory": "3.22GB",
//             "name": "chrome",
//             "pid": 1234,
//             "owner": "root",
//             "ownergrp": "root"
//             }, {
//             "cpu": 45.66,
//             "memory": "32.22GB",
//             "name": "chrome",
//             "pid": 12343,
//             "owner": "root",
//             "ownergrp": "root"
//             }]
//             }
//             *
//             *
//             * TODO:Implement here
//             * Step:
//             * 1.get data from SystemController
//             * 2.the data will be json formatted string,so parse it
//             * 3.add the data to layout
//             */
//            //SystemController controller = new SystemController();
//            //String JSONStr = controller.getallprocesses();
//            String JSONStr = "[{\n" +
//                    "             \"cpu\": 5.66,\n" +
//                    "             \"memory\": \"3.22GB\",\n" +
//                    "             \"name\": \"chrome\",\n" +
//                    "             \"pid\": 1234,\n" +
//                    "             \"owner\": \"root\",\n" +
//                    "             \"ownergrp\": \"root\"\n" +
//                    "             }, {\n" +
//                    "             \"cpu\": 45.66,\n" +
//                    "             \"memory\": \"32.22GB\",\n" +
//                    "             \"name\": \"chrome\",\n" +
//                    "             \"pid\": 12343,\n" +
//                    "             \"owner\": \"root\",\n" +
//                    "             \"ownergrp\": \"root\"\n" +
//                    "             }]";
//            JSONArray jsonArray = JSONArray.fromObject(JSONStr);
//
//            Object[] os = jsonArray.toArray();
//            for (int i = 0; i < os.length; i++) {
//                JSONObject jsonObject = JSONObject.fromObject(os[i]);
//                processData.add(new Process(jsonObject.get("name").toString(), Integer.parseInt(jsonObject.get("pid").toString()), jsonObject.get("owner").toString() + "/" + jsonObject.get("ownergrp").toString(), jsonObject.get("memory").toString(), Float.parseFloat(jsonObject.get("cpu").toString())));
//                System.out.println("Testing--------------------");
//                System.out.println("Name: " + jsonObject.get("name"));
//                System.out.println("pid: " + jsonObject.get("pid"));
//                System.out.println("ownerInfo: " + jsonObject.get("owner").toString() + "/" + jsonObject.get("ownergrp").toString());
//                System.out.println("Memory: "+jsonObject.get("memory"));
//                System.out.println("cpu: "+jsonObject.get("cpu"));
//                System.out.println("ObList: "+processData.get(i).getpName());
//            }
//
//            processTable.setItems(processData);
//        }
//    }





}
