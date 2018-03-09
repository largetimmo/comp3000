package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainWindow extends Pane {

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

}
