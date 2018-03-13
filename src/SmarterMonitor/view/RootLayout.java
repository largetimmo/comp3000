package SmarterMonitor.view;

import SmarterMonitor.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class RootLayout {
    MainWindow mainWindow;
    Main main;

    public void setMain(Main main){
        this.main =main;
    }

//    @FXML
//    private void setSetting(){
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("SetDialog.fxml"));
//            AnchorPane dialog = (AnchorPane) loader.load();
//            Stage setStage = new Stage();
//            setStage.setTitle("Set Refresh Rate");
//            setStage.initModality(Modality.WINDOW_MODAL);
//            Scene scene = new Scene(dialog);
//            setStage.setScene(scene);
//            SetDialog controller = loader.getController();
//            controller.setMain(main);
//            controller.setDialogStage(setStage);
//            setStage.showAndWait();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
