package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.*;

public class Controlador {
    private String ultimoResultado = "";
    public String getUltimoResultado() {
        return ultimoResultado;
    }

    public Button vectores;
    // Modo ecuaciones
    boolean sis2x2 = true;

    @FXML
    public TextField pantalla;
    @FXML
    private Label modoleyenda;
    @FXML
    private Label salidadefinirmatrices;
    @FXML
    private TextArea subMenu;



    private enum Modo {
        vectores, matrices, ecuaciones, calculos
    }
    private Modo modoActual = Modo.calculos; // Por defecto



    /*#### Sistema de MATRICES ####*/
    /*Variables para matrices*/
    private String matrizselec;
    private int fmatriz;
    private int cmatriz;
    @FXML
    private Map<String, int[][]> matrices = new HashMap<>(); //Lo usamos para crear mas de 1 matriz y poder seleccionarlas por el id
    private int[][] matriz;
    private boolean esperandoFilas = true;
    private int filaA = 0;
    private int columnaA = 0;

    public void mmatrices() { //SUBMENU
        salidadefinirmatrices.setText("");
        modoActual = Modo.matrices;
        modoleyenda.setText("Modo matrices");
        salidadefinirmatrices.setText(
                "Definir Matriz\n" +
                        "1: MatrizA\n" +
                        "2: MatrizB\n" +
                        "3: MatrizC\n" +
                        "4: MatrizD\n"
        );
        seleccionarmatriz(pantalla.getScene());
    }

    void seleccionarmatriz(Scene scene) { //Usamos el Scene porque vendria a ser una grabacion de la escena o pantalla que ve el usuario, se usa para esperar que este presione una tecla o haga algo, asi lo almacenamos/usamos
        scene.setOnKeyPressed(keyEvent -> { //Dentro de la escena, pedimos la tecla o accion hecha, kayevent para indicar que al presionar se ejecuta lo siguiente

                    switch (keyEvent.getCode()) { //Getcode pide el ascii de la tecla presionada, keyevent es el hecho de presionar la tecla
                        case DIGIT1 -> definirMatriz("A");
                        case DIGIT2 -> definirMatriz("B");
                        case DIGIT3 -> definirMatriz("C");
                        case DIGIT4 -> definirMatriz("D");
                    }
                }

        );
    }

    private void definirMatriz(String matrizid) { //DEFINIR DIMENSIONES
        matrizselec = matrizid;
        esperandoFilas = true;

        salidadefinirmatrices.setText("Definir Matriz " + matrizid + "\nIngrese la cantidad de filas:");
        pantalla.setOnAction(e -> {
            try {
                int valor = Integer.parseInt(pantalla.getText());
                pantalla.clear();
                if (esperandoFilas) {
                    fmatriz = valor;
                    esperandoFilas = false;
                    salidadefinirmatrices.setText("Definir Matriz " + matrizid + "\nIngrese la cantidad de columnas:");
                } else {
                    cmatriz = valor;
                    pantalla.setOnAction(null);
                    crearmatriz(matrizid, fmatriz, cmatriz);
                }
            } catch (NumberFormatException ex) {
                salidadefinirmatrices.setText("Error");
                pantalla.clear();
            }
        });
    }

    private void crearmatriz(String matrizid, int filas, int columnas) { //DEFINIR VALORES
        matriz = new int[filas][columnas];
        filaA = 0;
        columnaA = 0;
        pantalla.setOnAction(e -> {
            try {
                int valor = Integer.parseInt(pantalla.getText());
                pantalla.clear();

                matriz[filaA][columnaA] = valor;
                columnaA++;
                if (columnaA == columnas) {
                    columnaA = 0;
                    filaA++;
                }
                if (filaA == filas) {
                    matrices.put(matrizid, matriz);
                    pantalla.setOnAction(null);
                    mostrarMatriz(matriz);
                }
            } catch (NumberFormatException ex) {
                salidadefinirmatrices.setText("Error");
                pantalla.clear();
            }
        });


    }

    private void mostrarMatriz(int[][] m) { //FUNCION DE PRUEBA
        StringBuilder sb = new StringBuilder("Matriz ingresada:\n");
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                sb.append(m[i][j]).append("\t");
            }
            sb.append("\n");
        }
        salidadefinirmatrices.setText(sb.toString());
    }

    public void mmat1() {
        if (matrices.containsKey("A")) {
            pantalla.setText(pantalla.getText() + "MatrizA");
        }
    }

    public void mmat2() {
        if (matrices.containsKey("B")) {
            pantalla.setText(pantalla.getText() + "MatrizB");
        }

    }

    public void mmat3() {
        if (matrices.containsKey("C")) {
            pantalla.setText(pantalla.getText() + "MatrizC");
        }
    }

    public void mmat4() {
        if (matrices.containsKey("D")) {
            pantalla.setText(pantalla.getText() + "MatrizD");
        }
    }

    public void mpot() {
        pantalla.setText(pantalla.getText() + "^");
    }

    public void mraiz() {
        pantalla.setText(pantalla.getText() + "√");
    }
    public void mdet() {
        pantalla.setText(pantalla.getText() + "Det(");
    }
    public void minv() {
        pantalla.setText(pantalla.getText() + "Inv(");
    }
    public void mparen() {
        pantalla.setText(pantalla.getText() + ")");
    }


    private void procesarmatS() {
        String expresion = pantalla.getText().replace(" ", ""); // eliminar espacios
        String[] mat = expresion.split("\\+"); // dividir por el símbolo +

        List<int[][]> matricesasumar = new ArrayList<>();

        for (String matri : mat) {
            String id = matri.replace("Matriz", ""); // sacar el "Matriz"
            if (!matrices.containsKey(id)) {
                salidadefinirmatrices.setText("No existe la matriz " + id);
                return;
            }
            matricesasumar.add(matrices.get(id));
        }

        // Verificamos dimensiones iguales
        int filas = matricesasumar.get(0).length;
        int columnas = matricesasumar.get(0)[0].length;

        for (int[][] m : matricesasumar) {
            if (m.length != filas || m[0].length != columnas) {
                salidadefinirmatrices.setText("Las matrices deben tener las mismas dimensiones.");
                return;
            }
        }

        // Hacemos la suma
        int[][] resultado = new int[filas][columnas];
        for (int[][] m : matricesasumar) {
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    resultado[i][j] += m[i][j];
                }
            }
        }

        mostrarMatriz(resultado); // Mostramos el resultado
    }


    private void procesarmatR() {
        String expresion  = pantalla.getText();
        String[] mat= expresion.split("\\-"); //Convierte la expresion en una array con las matrices ingresadas
        List<int[][]> matricesrestar = new ArrayList<>();
        for(String matri:mat){
            String id = matri.replace("Matriz", ""); //Elimina matriz de la expresion, dejando solamente los id
            matricesrestar.add(matrices.get(id));
        }
        int filas = matricesrestar.get(0).length;
        int columnas = matricesrestar.get(0)[0].length;
        for (int[][] m : matricesrestar) {
            if (m.length != filas || m[0].length != columnas) {
                salidadefinirmatrices.setText("Las matrices deben tener las mismas dimensiones.");
                return;
            }
        }
        int [][] resultado = new int[filas][columnas];
        int [][] primera = matricesrestar.get(0);
        for(int i=0; i<filas; i++){
            for(int j=0; j<columnas; j++){
                resultado[i][j]=primera[i][j];
            }
        }
        for (int k = 1; k < matricesrestar.size(); k++) {
            int [][] m= matricesrestar.get(k);
            for (int i = 0; i <filas; i++) {
                for (int j = 0; j <columnas; j++) {
                    resultado[i][j]-=m[i][j];
                }
            }
            
        }
        mostrarMatriz(resultado);

    }

    private void procesarmatD() {
        String expresion = pantalla.getText();
        String[] mat = expresion.split("/");

        if (mat.length != 2) {
            salidadefinirmatrices.setText("Formato incorrecto. Use MatrizA/MatrizB");
            return;
        }

        String id1 = mat[0].trim().replace("Matriz", "");
        String id2 = mat[1].trim().replace("Matriz", "");

        if (!matrices.containsKey(id1) || !matrices.containsKey(id2)) {
            salidadefinirmatrices.setText("Una de las matrices no existe.");
            return;
        }

        int[][] m1 = matrices.get(id1);
        int[][] m2 = matrices.get(id2);

        int filas1 = m1.length;
        int columnas1 = m1[0].length;
        int filas2 = m2.length;
        int columnas2 = m2[0].length;

        if (filas2 != columnas2) {
            salidadefinirmatrices.setText("La segunda matriz debe ser cuadrada para calcular la inversa.");
            return;
        }

        int det = determinante(m2, filas2);
        if (det == 0) {
            salidadefinirmatrices.setText("La segunda matriz no tiene inversa.");
            return;
        }

        int[][] adj = adjunta(m2, filas2);
        float[][] inversa = new float[filas2][columnas2];

        for (int i = 0; i < filas2; i++) {
            for (int j = 0; j < columnas2; j++) {
                inversa[i][j] = (float) adj[i][j] / det;
            }
        }

        if (columnas1 != filas2) {
            salidadefinirmatrices.setText("Columnas de la primera matriz deben coincidir con filas de la inversa.");
            return;
        }

        float[][] resultado = new float[filas1][columnas2];

        for (int i = 0; i < filas1; i++) {
            for (int j = 0; j < columnas2; j++) {
                for (int k = 0; k < columnas1; k++) {
                    resultado[i][j] += m1[i][k] * inversa[k][j];
                }
            }
        }

        mostrarMatrizFloat(resultado);
    }

    private void procesarmatM() {
        String expresion = pantalla.getText().replaceAll(" ", "");
        String[] mat = expresion.split("\\*");

        if (mat.length != 2) {
            salidadefinirmatrices.setText("Formato incorrecto. Use MatrizA*MatrizB o MatrizA*escalar");
            return;
        }

        String id1 = mat[0].trim().replace("Matriz", "");
        String segundo = mat[1].trim();

        if (!matrices.containsKey(id1)) {
            salidadefinirmatrices.setText("La primera matriz no existe.");
            return;
        }

        int[][] m1 = matrices.get(id1);
        try {
            float escalar = Float.parseFloat(segundo);

            int filas = m1.length;
            int columnas = m1[0].length;
            float[][] resultado = new float[filas][columnas];

            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    resultado[i][j] = m1[i][j] * escalar;
                }
            }
            mostrarMatrizFloat(resultado);
            return;

        } catch (NumberFormatException e) {
        }

        String id2 = segundo.replace("Matriz", "");
        if (!matrices.containsKey(id2)) {
            salidadefinirmatrices.setText("La segunda matriz no existe.");
            return;
        }

        int[][] m2 = matrices.get(id2);

        int filas1 = m1.length;
        int columnas1 = m1[0].length;
        int filas2 = m2.length;
        int columnas2 = m2[0].length;

        if (columnas1 != filas2) {
            salidadefinirmatrices.setText("Columnas de la primera matriz deben ser iguales a filas de la segunda.");
            return;
        }

        int[][] resultado = new int[filas1][columnas2];

        for (int i = 0; i < filas1; i++) {
            for (int j = 0; j < columnas2; j++) {
                for (int k = 0; k < columnas1; k++) {
                    resultado[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }

        mostrarMatriz(resultado);
    }

    private void mostrarMatrizFloat(float[][] m) {
        StringBuilder sb = new StringBuilder("Matriz inversa:\n");
        for (float[] fila : m) {
            for (float val : fila) {
                sb.append(String.format("%.2f", val)).append("\t");
            }
            sb.append("\n");
        }
        salidadefinirmatrices.setText(sb.toString());
    }
    private void procesarmatInv() {
        String expresion = pantalla.getText(); // Esperás algo como Inv(MatrizA)
        String id = expresion.replace("Inv(", "").replace(")", "").replace("Matriz", "").trim();

        if (!matrices.containsKey(id)) {
            salidadefinirmatrices.setText("La matriz no existe.");
            return;
        }

        int[][] A = matrices.get(id);
        int n = A.length;
        if (A[0].length != n) {
            salidadefinirmatrices.setText("La matriz no es cuadrada.");
            return;
        }

        int det = determinante(A, n);
        if (det == 0) {
            salidadefinirmatrices.setText("La matriz no tiene inversa.");
            return;
        }

        int[][] adj = adjunta(A, n);
        float[][] inversa = new float[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inversa[i][j] = (float) adj[i][j] / det;
            }
        }
        mostrarMatrizFloat(inversa);
    }

    private int[][] adjunta(int[][] A, int n) {
        int[][] adj = new int[n][n];
        if (n == 1) {
            adj[0][0] = 1;
            return adj;
        }

        int signo = 1;
        int[][] temp = new int[n-1][n-1];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                getCofactor(A, temp, i, j, n);
                signo = ((i + j) % 2 == 0) ? 1 : -1;
                adj[j][i] = signo * determinante(temp, n - 1); // Traspuesta
            }
        }
        return adj;

    }

    private int determinante(int[][] matriz, int n) {
        if (n == 1) return matriz[0][0];

        int det = 0;
        int[][] temp = new int[n-1][n-1];
        int signo = 1;

        for (int f = 0; f < n; f++) {
            getCofactor(matriz, temp, 0, f, n);
            det += signo * matriz[0][f] * determinante(temp, n - 1);
            signo = -signo;
        }

        return det;
    }
    private void getCofactor(int[][] mat, int[][] temp, int p, int q, int n) {
        int row = 0, col = 0;

        for (int i = 0; i < n; i++) {
            if (i == p) continue;

            col = 0;
            for (int j = 0; j < n; j++) {
                if (j == q) continue;

                temp[row][col] = mat[i][j];
                col++;
            }
            row++;
        }
    }

    /*#### FIN ####*/


    /*#### Sistema de ECUACIONES lineales ####*/
    @FXML private Button btnSistema2x2;
    @FXML private Button btnSistema3x3;

    @FXML private TextField coefA1;
    @FXML private TextField coefB1;
    @FXML private TextField coefC1;
    @FXML private TextField coefD1;
    @FXML private TextField coefA2;
    @FXML private TextField coefB2;
    @FXML private TextField coefC2;
    @FXML private TextField coefD2;
    @FXML private TextField coefA3;
    @FXML private TextField coefB3;
    @FXML private TextField coefC3;
    @FXML private TextField coefD3;

    @FXML private Label l1;
    @FXML private Label l2;
    @FXML private Label l3;
    @FXML private Label l4;
    @FXML private Label l5;
    @FXML private Label l6;
    @FXML private Label l7;
    @FXML private Label l8;
    @FXML private Label l9;
    @FXML private Label l10;
    @FXML private Label l11;
    @FXML private Label l12;

    @FXML
    public void mecuaciones() {
        salidadefinirmatrices.setText("");
        modoActual = Modo.ecuaciones;
        modoleyenda.setText("Modo ecuaciones");

        ocultarSubmenus();

        btnSistema2x2.setVisible(true); btnSistema2x2.setManaged(true);
        btnSistema3x3.setVisible(true); btnSistema3x3.setManaged(true);
    }


    @FXML
    public void mostrarCampos2x2() {
        ocultarCampos3x3();
        sis2x2 = true;

        l1.setVisible(true);
        l2.setVisible(true);
        l3.setVisible(true);
        l4.setVisible(true);
        l5.setVisible(true);
        l6.setVisible(true);

        coefA1.setVisible(true); coefA1.setManaged(true);
        coefB1.setVisible(true); coefB1.setManaged(true);
        coefC1.setVisible(true); coefC1.setManaged(true);
        coefA2.setVisible(true); coefA2.setManaged(true);
        coefB2.setVisible(true); coefB2.setManaged(true);
        coefC2.setVisible(true); coefC2.setManaged(true);
    }

    public void mostrarCampos3x3() {
        ocultarCampos2x2();
        sis2x2 = false;

        l1.setVisible(true);
        l2.setVisible(true);
        l3.setVisible(true);
        l4.setVisible(true);
        l5.setVisible(true);
        l6.setVisible(true);
        l7.setVisible(true);
        l8.setVisible(true);
        l9.setVisible(true);
        l10.setVisible(true);
        l11.setVisible(true);
        l12.setVisible(true);

        coefA1.setVisible(true); coefA1.setManaged(true);
        coefB1.setVisible(true); coefB1.setManaged(true);
        coefC1.setVisible(true); coefC1.setManaged(true);
        coefD1.setVisible(true); coefD1.setManaged(true);
        coefA2.setVisible(true); coefA2.setManaged(true);
        coefB2.setVisible(true); coefB2.setManaged(true);
        coefC2.setVisible(true); coefC2.setManaged(true);
        coefD2.setVisible(true); coefD2.setManaged(true);
        coefA3.setVisible(true); coefA3.setManaged(true);
        coefB3.setVisible(true); coefB3.setManaged(true);
        coefC3.setVisible(true); coefC3.setManaged(true);
        coefD3.setVisible(true); coefD3.setManaged(true);

    }

    public void ocultarCampos2x2() {
        l1.setVisible(false);
        l2.setVisible(false);
        l3.setVisible(false);
        l4.setVisible(false);
        l5.setVisible(false);
        l6.setVisible(false);
        coefA1.setVisible(false); coefA1.setManaged(false);
        coefB1.setVisible(false); coefB1.setManaged(false);
        coefC1.setVisible(false); coefC1.setManaged(false);
        coefA2.setVisible(false); coefA2.setManaged(false);
        coefB2.setVisible(false); coefB2.setManaged(false);
        coefC2.setVisible(false); coefC2.setManaged(false);
    }

    public void ocultarCampos3x3() {
        l1.setVisible(false);
        l2.setVisible(false);
        l3.setVisible(false);
        l4.setVisible(false);
        l5.setVisible(false);
        l6.setVisible(false);
        l7.setVisible(false);
        l8.setVisible(false);
        l9.setVisible(false);
        l10.setVisible(false);
        l11.setVisible(false);
        l12.setVisible(false);


        coefA1.setVisible(false); coefA1.setManaged(false);
        coefB1.setVisible(false); coefB1.setManaged(false);
        coefC1.setVisible(false); coefC1.setManaged(false);
        coefD1.setVisible(false); coefD1.setManaged(false);
        coefA2.setVisible(false); coefA2.setManaged(false);
        coefB2.setVisible(false); coefB2.setManaged(false);
        coefC2.setVisible(false); coefC2.setManaged(false);
        coefD2.setVisible(false); coefD2.setManaged(false);
        coefA3.setVisible(false); coefA3.setManaged(false);
        coefB3.setVisible(false); coefB3.setManaged(false);
        coefC3.setVisible(false); coefC3.setManaged(false);
        coefD3.setVisible(false); coefD3.setManaged(false);
    }

    @FXML
    public void resolverSistema2x2() {
        try {
            float a1 = Float.parseFloat(coefA1.getText());
            float b1 = Float.parseFloat(coefB1.getText());
            float c1 = Float.parseFloat(coefC1.getText());
            float a2 = Float.parseFloat(coefA2.getText());
            float b2 = Float.parseFloat(coefB2.getText());
            float c2 = Float.parseFloat(coefC2.getText());

            float D = a1 * b2 - b1 * a2;
            float Dx = c1 * b2 - b1 * c2;
            float Dy = a1 * c2 - c1 * a2;

            String resultado_;
            if (D == 0 && Dx == 0 && Dy == 0) {
                resultado_ = "El sistema tiene infinitas soluciones.";
            } else if (D == 0) {
                resultado_ = "El sistema no tiene solución.";
            } else {
                float x = Dx / D;
                float y = Dy / D;
                resultado_ = "Solución:\nX = " + x + "\nY = " + y;
            }

            pantalla.setText(resultado_);
        } catch (NumberFormatException e) {
            pantalla.setText("Error: Ingresá solo números válidos.");
        }
    }

    @FXML
    public void resolverSistema3x3() {
        try {
            float a1 = Float.parseFloat(coefA1.getText());
            float b1 = Float.parseFloat(coefB1.getText());
            float c1 = Float.parseFloat(coefC1.getText());
            float d1 = Float.parseFloat(coefD1.getText());

            float a2 = Float.parseFloat(coefA2.getText());
            float b2 = Float.parseFloat(coefB2.getText());
            float c2 = Float.parseFloat(coefC2.getText());
            float d2 = Float.parseFloat(coefD2.getText());

            float a3 = Float.parseFloat(coefA3.getText());
            float b3 = Float.parseFloat(coefB3.getText());
            float c3 = Float.parseFloat(coefC3.getText());
            float d3 = Float.parseFloat(coefD3.getText());

            float D =  (b2 * c3 - b3 * c2) * a1
                    - (b1 * c3 - b3 * c1) * a2
                    + (b1 * c2 - b2 * c1) * a3;

            float Dx = (b2 * c3 - b3 * c2) * d1
                    - (b1 * c3 - b3 * d1) * a2
                    + (b1 * d3 - b3 * d2) * a3;

            float Dy = (d2 * c3 - d3 * c2) * a1
                    - (d1 * c3 - d3 * c1) * a2
                    + (d1 * c2 - d2 * c1) * a3;

            float Dz = (b2 * d3 - b3 * d2) * a1
                    - (b1 * d3 - b3 * d1) * a2
                    + (b1 * d2 - b2 * d1) * a3;


            String resultado_;
            if (D == 0 && Dx == 0 && Dy == 0 && Dz == 0) {
                resultado_ = "El sistema tiene infinitas soluciones.";
            } else if (D == 0) {
                resultado_ = "El sistema no tiene solución.";
            } else {
                float x = Dx / D;
                float y = Dy / D;
                float z = Dz / D;

                resultado_ = "Solución:\nX = " + x + "\nY = " + y + "\nZ = " + z;
            }

            pantalla.setText(resultado_);
        } catch (NumberFormatException e) {
            pantalla.setText("Error: Ingresá solo números válidos.");
        }
    }
    /*#### FIN ####*/



    /*#### Sistema de calculos ####*/
    private void procesarcalS() {
        try {
            String[] numeros = pantalla.getText().replace(",", ".").split("\\+");
            double resultado = 0;
            for (String num : numeros) {
                if (!num.isEmpty()) {
                    resultado += Double.parseDouble(num);
                }
            }
            pantalla.setText(String.valueOf(resultado));
            ultimoResultado = String.valueOf(resultado);
        } catch (NumberFormatException e) {
            pantalla.setText("Error");
        }
    }
    private void procesarcalR() {
        try {
            String[] numeros = pantalla.getText().replace(",", ".").split("\\-");
            if (numeros.length == 0) {
                pantalla.setText("Error");
                return;
            }

            double resultado = Double.parseDouble(numeros[0]);
            for (int i = 1; i < numeros.length; i++) {
                if (!numeros[i].isEmpty()) {
                    resultado -= Double.parseDouble(numeros[i]);
                }
            }
            pantalla.setText(String.valueOf(resultado));
            ultimoResultado = String.valueOf(resultado);

        } catch (NumberFormatException e) {
            pantalla.setText("Error");
        }
    }
    private void procesarcalM() {
        try {
            String[] numeros = pantalla.getText().replace(",", ".").split("\\*");
            double resultado = 1;
            for (String num : numeros) {
                if (!num.isEmpty()) {
                    resultado *= Double.parseDouble(num);
                }
            }
            pantalla.setText(String.valueOf(resultado));
            ultimoResultado = String.valueOf(resultado);

        } catch (NumberFormatException e) {
            pantalla.setText("Error");
        }
    }
    private void procesarcalD() {
        try {
            String[] numeros = pantalla.getText().replace(",", ".").split("/");
            if (numeros.length == 0) {
                pantalla.setText("Error");
                return;
            }

            double resultado = Double.parseDouble(numeros[0]);
            for (int i = 1; i < numeros.length; i++) {
                double divisor = Double.parseDouble(numeros[i]);
                if (divisor == 0) {
                    pantalla.setText("No se puede dividir por cero");
                    return;
                }
                resultado /= divisor;
            }
            pantalla.setText(String.valueOf(resultado));
            ultimoResultado = String.valueOf(resultado);

        } catch (NumberFormatException e) {
            pantalla.setText("Error");
        }
    }
    private void procesarcalP() {
        try {
            String[] numeros = pantalla.getText().replace(",", ".").split("\\^");
            if (numeros.length != 2) {
                pantalla.setText("Error");
                return;
            }
            double base = Double.parseDouble(numeros[0]);
            double exponente = Double.parseDouble(numeros[1]);
            double resultado = Math.pow(base, exponente);
            pantalla.setText(String.valueOf(resultado));
            ultimoResultado = String.valueOf(resultado);

        } catch (NumberFormatException e) {
            pantalla.setText("Error");
        }
    }
    private void procesarcalRaiz() {
        try {
            String texto = pantalla.getText().replace(",", ".");

            int indiceRaiz = texto.indexOf("√");

            if (indiceRaiz == -1 || indiceRaiz == texto.length() - 1) {
                pantalla.setText("Error");
                return;
            }

            int indice;
            double radicando;

            // asume raiz 2 si no se especifica
            if (indiceRaiz == 0) {
                indice = 2;
            } else {
                indice = Integer.parseInt(texto.substring(0, indiceRaiz));
            }

            radicando = Double.parseDouble(texto.substring(indiceRaiz + 1));

            if (indice <= 0) {
                pantalla.setText("Error");
                return;
            }

            double resultado = Math.pow(radicando, 1.0 / indice);
            pantalla.setText(String.valueOf(resultado));
            ultimoResultado = String.valueOf(resultado);
        } catch (Exception e) {
            pantalla.setText("Error");
        }
    }
    /*#### FIN ####*/





    public void ocultarSubmenus() {
        btnSistema2x2.setVisible(false); btnSistema2x2.setManaged(false);
        btnSistema3x3.setVisible(false); btnSistema3x3.setManaged(false);
        ocultarCampos2x2();
        ocultarCampos3x3();
    }
    public void mcalculos() {
        salidadefinirmatrices.setText("");
        modoActual = Modo.calculos;
        modoleyenda.setText("Modo calculos");


    }




    public void mbtnresultado() {
        switch (modoActual) {
            case calculos:
                if (pantalla.getText().contains("-")) {
                    procesarcalR();
                } else if (pantalla.getText().contains("+")) {
                    procesarcalS();
                } else if (pantalla.getText().contains("/")) {
                    procesarcalD();
                } else if (pantalla.getText().contains("*")) {
                    procesarcalM();
                } else if (pantalla.getText().contains("√")) {
                    procesarcalRaiz();
                } else if (pantalla.getText().contains("^")) {
                    procesarcalP();
                }
                break;

            case matrices:
                if (pantalla.getText().contains("-")) {
                    procesarmatR();
                } else if (pantalla.getText().contains("+")) {
                    procesarmatS();
                } else if (pantalla.getText().contains("/")) {
                    procesarmatD();
                } else if (pantalla.getText().contains("*")) {
                    procesarmatM();
                } else if (pantalla.getText().contains("Det(")) {
                    procesarmatDet();
                } else if (pantalla.getText().contains("Inv(")) {
                    procesarmatInv();
                }
                break;

            case ecuaciones:
                if (sis2x2) {
                    resolverSistema2x2();
                } else {
                    resolverSistema3x3();
                }
                break;

            case vectores:
                String texto = pantalla.getText().replace(" ", "");

                if (texto.contains("+")) {
                    procesarcvecSR(texto, '+');
                } else if (texto.contains("-")) {
                    procesarcvecSR(texto, '-');
                } else if (texto.contains("*")) {
                    procesarvecE(texto);
                } else if (texto.contains("·")) {
                    procesarvecPE(texto);
                } else if (texto.contains("x") || texto.contains("×")) {
                    procesarvecPV(texto);
                } else {
                    salidadefinirmatrices.setText("Operación vectorial no reconocida.");
                }
                break;

            default:
                pantalla.setText("Modo no reconocido");
                break;
        }
    }

    private void procesarvecPV(String texto) {
    }

    private void procesarvecPE(String texto) {
    }

    private void procesarvecE(String texto) {
    }

    private void procesarcvecSR(String texto, char c) {
    }


    private void procesarmatDet() {
    }

    public void initialize(){
        mostrarPanelA();
    }


    public void mvectores(ActionEvent actionEvent) {
        salidadefinirmatrices.setText("");
        modoActual = Modo.vectores;
        modoleyenda.setText("Modo vectores");
        ocultarSubmenus();
    }




    /*##Manejo de los paneles##*/
    @FXML
    private StackPane contenedorVistas;

    private Parent panel_a;
    private Parent panel_b;

    // En controlador principal

    @FXML
    private void mostrarPanelA() {
        try {
            if (panel_a == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/panel_a.fxml"));
                panel_a = loader.load();
                Controladorpanela controladorPanelA = loader.getController();
                controladorPanelA.setPantalla(pantalla);
                controladorPanelA.setControladorPadre(this);
            }
            contenedorVistas.getChildren().clear();
            contenedorVistas.getChildren().add(panel_a);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void mostrarPanelB() {
        try {
            if (panel_b == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/panel_b.fxml"));
                panel_b = loader.load();
                Controladorpanelb controladorPanelB = loader.getController();
                controladorPanelB.setPantalla(pantalla);
                controladorPanelB.setControladorPadre(this);
            }
            contenedorVistas.getChildren().clear();
            contenedorVistas.getChildren().add(panel_b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onVolverPanelA(ActionEvent event) {
        mostrarPanelA();
    }}

