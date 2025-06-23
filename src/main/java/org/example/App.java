package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/pantalla.fxml"));
        Scene scene = new Scene(root, 400, 200);
        stage.setScene(scene);
        stage.setTitle("BMO-Calculator");

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
