package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Controlador {

    public TextField pantalla;
    @FXML
    private TextField display;

    @FXML
    public void mbtn1() {
        pantalla.setText(pantalla.getText()+1);
    }

    public void mvectores() {
        pantalla.setText("Modo vectores");
    }

    public void mmatrices() {
        pantalla.setText("Modo matrices");
    }

    public void mecuaciones() {
        pantalla.setText("Modo ecuaciones");
    }

    public void mcalculos() {
        pantalla.setText("Modo calculos");
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
}
