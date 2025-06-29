package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controladorpanelb {

    private Controlador controladorPadre;

    @FXML
    private Button vec1;
    @FXML
    private Button vec2;
    @FXML
    private Button vec3;
    @FXML
    private Button vec4;
    private TextField pantalla;

    public void setControladorPadre(Controlador controladorPadre) {
        this.controladorPadre = controladorPadre;
    }

    @FXML
    private void mmat1() {
        if (controladorPadre != null) {
            controladorPadre.mmat1();
        }
    }

    @FXML
    private void mmat2() {
        if (controladorPadre != null) {
            controladorPadre.mmat2();
        }
    }

    @FXML
    private void mmat3() {
        if (controladorPadre != null) {
            controladorPadre.mmat3();
        }
    }

    @FXML
    private void mmat4() {
        if (controladorPadre != null) {
            controladorPadre.mmat4();
        }
    }

    public void setPantalla(TextField pantalla) {
        this.pantalla = pantalla;
    }

    public void agregarPotencia(ActionEvent actionEvent) {
        if (controladorPadre != null) {
            controladorPadre.mpot();
        }
    }

    public void agregarRaiz(ActionEvent actionEvent) {
        if (controladorPadre != null) {
            controladorPadre.mraiz();
        }
    }

    public void agregarDet(ActionEvent actionEvent) {
        if (controladorPadre != null) {
            controladorPadre.mdet();
        }
    }

    public void agregarInv(ActionEvent actionEvent) {
        if (controladorPadre != null) {
            controladorPadre.minv();
        }

    }

    public void agregarParen(ActionEvent actionEvent) {
        if (controladorPadre != null) {
            controladorPadre.mparen();
        }
    }

    public void mvec1(ActionEvent actionEvent) {
        if (controladorPadre != null) {
            controladorPadre.mvec1();
        }
    }
    public void mvec2(ActionEvent actionEvent) {
        if (controladorPadre != null) {
            controladorPadre.mvec2();
        }
    }
    public void mvec3(ActionEvent actionEvent) {
        if (controladorPadre != null) {
            controladorPadre.mvec3();
        }
    }
}
