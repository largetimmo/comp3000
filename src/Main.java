

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.MainWindow;

import java.io.IOException;

public class Main extends Application {

    private MainWindow mainWindow;
    @Override
    public void start(Stage primaryStage) throws Exception{
        mainWindow = new MainWindow();
        mainWindow.init();
        Scene scene = new Scene(mainWindow);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
