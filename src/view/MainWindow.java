package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainWindow extends Pane {
    Thread update;

    public MainWindow(){

    }

    public void init(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("RootLayout.fxml"));
            Pane layout = loader.load();
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("MainWindow.fxml"));
            Pane table = loader.load();
            table.relocate(0,20);
            layout.getChildren().addAll(table);
            getChildren().addAll(layout);
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
             * TODO:Implement here
             * Step:
             * 1.get data from SystemController
             * 2.the data will be json formatted string,so parse it
             * 3.add the data to layout
             */
        }
    }


}
