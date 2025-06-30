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


            Scene scene = new Scene(root, 1400, 900);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/estilo.css")).toExternalForm());

            Controlador controlador = loader.getController();
            stage.setResizable(false);

            controlador.seleccionarmatriz(scene);

            stage.setScene(scene);

            stage.sizeToScene();


            stage.setTitle("BMO-Calculator");
            stage.show();
        }
    }