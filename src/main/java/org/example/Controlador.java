package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class Controlador {

    public TextField pantalla;
    @FXML
    private TextField display;
    @FXML
    private Label modoleyenda;
    @FXML
    private Label salidadefinirmatrices;
    @FXML
    private TextArea subMenu;
    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btn4;


    @FXML
    public void mbtn1() {
        pantalla.setText(pantalla.getText()+1);
    }

    public void mvectores() {
        modoleyenda.setText("Modo vectores");
    }

    public void mmatrices() {
        modoleyenda.setText("Modo matrices");
        subMenu.setText(
                "Definir Matriz\n"+
                        "1: MatrizA\n"+
                        "2: MatrizB\n"+
                        "3: MatrizC\n"+
                        "4: MatrizD\n"
        );
        subMenu.setVisible(true);
        subMenu.setManaged(true);
    }
    void seleccionarmatriz(Scene scene){ //Usamos el Scene porque vendria a ser una grabacion de la escena o pantalla que ve el usuario, se usa para esperar que este presione una tecla o haga algo, asi lo almacenamos/usamos
        scene.setOnKeyPressed(keyEvent -> { //Dentro de la escena, pedimos la tecla o accion hecha, kayevent para indicar que al presionar se ejecuta lo siguiente
                switch (keyEvent.getCode()){ //Getcode pide el ascii de la tecla presionada, keyevent es el hecho de presionar la tecla
                    case DIGIT1 -> definirMatriz("A");
                    case DIGIT2 -> definirMatriz("B");
                    case DIGIT3 -> definirMatriz("C");
                    case DIGIT4 -> definirMatriz("D");
                }
                }

        );
        btn1.setOnAction(e -> definirMatriz("A"));
        btn2.setOnAction(e -> definirMatriz("B"));
        btn3.setOnAction(e -> definirMatriz("C"));
        btn4.setOnAction(e -> definirMatriz("D"));
    }

    private void definirMatriz(String matrizid){
        salidadefinirmatrices.setText("Definir Matriz " + matrizid + "\nIngrese la cantidad de filas:");
    }

    public void mecuaciones() {
        modoleyenda.setText("Modo ecuaciones");
    }

    public void mcalculos() {
        modoleyenda.setText("Modo calculos");
    }

    public void mbtn4() {
        pantalla.setText(pantalla.getText()+4);
    }

    public void mbtn2() {
        pantalla.setText(pantalla.getText()+2);
    }

    public void mbtn5() {
        pantalla.setText(pantalla.getText()+5);
    }

    public void mbtn7() {
        pantalla.setText(pantalla.getText()+7);
    }

    public void mbtn0() {
        pantalla.setText(pantalla.getText()+0);
    }

    public void mbtn8() {
        pantalla.setText(pantalla.getText()+8);
    }

    public void mbtncoma() {
        pantalla.setText(pantalla.getText()+",");
    }

    public void mbtn3() {
        pantalla.setText(pantalla.getText()+3);
    }

    public void mbtn6() {
        pantalla.setText(pantalla.getText()+6);
    }

    public void mbtndel() {
        String texto=pantalla.getText();
        if(!texto.isEmpty()){
            pantalla.setText(texto.substring(0,texto.length()-1));
        }
    }

    public void mbtnmult() {
        pantalla.setText(pantalla.getText()+"*");
    }

    public void mbtn9() {
        pantalla.setText(pantalla.getText()+9);
    }

    public void mbtnresultado() {
        pantalla.setText(pantalla.getText()+1);
    }

    public void mbtnsuma() {
        pantalla.setText(pantalla.getText()+"+");
    }

    public void mbtnac() {
        pantalla.clear();
    }

    public void mbtndiv() {
        pantalla.setText(pantalla.getText()+"/");
    }

    public void mbtnmenos() {
        pantalla.setText(pantalla.getText()+"-");
    }

    public void mbtnon() {
    }

    @FXML
    public void initialize() {
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

                    };
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
}
