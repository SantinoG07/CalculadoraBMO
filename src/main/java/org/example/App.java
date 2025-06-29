package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pantalla.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 400, 200);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/estilo.css")).toExternalForm());

        Controlador controlador = loader.getController();
        controlador.seleccionarmatriz(scene);

        stage.setScene(scene);
        stage.setTitle("BMO-Calculator");
        stage.setMaximized(true);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
