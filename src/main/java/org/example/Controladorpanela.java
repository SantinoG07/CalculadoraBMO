package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controladorpanela {

    @FXML
    private TextField pantalla; // Ahora viene desde el controlador padre


    @FXML private Button btn1;
    @FXML private Button btn2;
    @FXML private Button btn3;
    @FXML private Button btn4;
    @FXML private Button btn5;
    @FXML private Button btn6;
    @FXML private Button btn7;
    @FXML private Button btn8;
    @FXML private Button btn9;
    @FXML private Button btn0;
    @FXML private Button btnsuma;
    @FXML private Button btnmenos;
    @FXML private Button btnmult;
    @FXML private Button btndiv;
    @FXML private Button btncoma;
    @FXML private Button btnresultado;
    @FXML private Button btnac;
    @FXML private Button btndel;
    @FXML private Button btnans;

    public void setPantalla(TextField pantalla) {
        System.out.println("Pantalla recibida en panel_a: " + pantalla);
        this.pantalla = pantalla;

        pantalla.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    switch (event.getCode()) {
                        case DIGIT9, NUMPAD9 -> mbtn9();
                        case DIGIT8, NUMPAD8 -> mbtn8();
                        case DIGIT7, NUMPAD7 -> mbtn7();
                        case DIGIT6, NUMPAD6 -> mbtn6();
                        case DIGIT5, NUMPAD5 -> mbtn5();
                        case DIGIT4, NUMPAD4 -> mbtn4();
                        case DIGIT3, NUMPAD3 -> mbtn3();
                        case DIGIT2, NUMPAD2 -> mbtn2();
                        case DIGIT1, NUMPAD1 -> mbtn1();
                        case ENTER -> mbtnresultado();
                        case ESCAPE -> mbtnac();
                        case DELETE -> mbtndel();
                    }

                    String texto = event.getText();
                    switch (texto) {
                        case "+" -> mbtnsuma();
                        case "-" -> mbtnmenos();
                        case "*" -> mbtnmult();
                        case "/" -> mbtndiv();
                        case "=" -> mbtnresultado();
                        case "," -> mbtncoma();
                    }
                });
            }
        });
    }

    private void agregarTexto(String texto) {
        if (pantalla != null) {
            pantalla.setText(pantalla.getText() + texto);
        }
    }

    @FXML private void mbtn1() { agregarTexto("1"); }
    @FXML private void mbtn2() { agregarTexto("2"); }
    @FXML private void mbtn3() { agregarTexto("3"); }
    @FXML private void mbtn4() { agregarTexto("4"); }
    @FXML private void mbtn5() { agregarTexto("5"); }
    @FXML private void mbtn6() { agregarTexto("6"); }
    @FXML private void mbtn7() { agregarTexto("7"); }
    @FXML private void mbtn8() { agregarTexto("8"); }
    @FXML private void mbtn9() { agregarTexto("9"); }
    @FXML private void mbtn0() { agregarTexto("0"); }
    @FXML private void mbtncoma() { agregarTexto(","); }
    @FXML private void mbtnsuma() { agregarTexto("+"); }
    @FXML private void mbtnmenos() { agregarTexto("-"); }
    @FXML private void mbtnmult() { agregarTexto("*"); }
    @FXML private void mbtndiv() { agregarTexto("/"); }

    @FXML private void mbtndel() {
        if (pantalla != null && !pantalla.getText().isEmpty()) {
            pantalla.setText(pantalla.getText().substring(0, pantalla.getText().length() - 1));
        }
    }

    @FXML private void mbtnac() {
        if (pantalla != null) pantalla.clear();
    }
    private Controlador controladorPadre;

    public void setControladorPadre(Controlador controlador) {
        this.controladorPadre = controlador;
    }
    @FXML
    private void mbtnresultado() {
        if (controladorPadre != null) {
            controladorPadre.mbtnresultado();
        }
    }


    @FXML private void btnans() {
        // lógica futura
    }
}
