package org.example;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controlador {

    // Modo seleccionado
    boolean modo_calculo = true;
    boolean modo_vectores = false;
    boolean modo_matrices = false;
    boolean modo_ecuacion = false;


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


    /*#### Sistema de VECTORES ####*/
    //Variables de vectores
    private String vectorSelec;
    private int[] vector;
    private int indiceVector = 0;
    @FXML
    private Map<String, int[]> vectores = new HashMap<>();

    public void mvectores() {
        modoleyenda.setText("Modo vectores");
        subMenu.setText(
                "Definir Vector\n" +
                        "1: VectorA\n" +
                        "2: VectorB\n" +
                        "3: VectorC\n" +
                        "4: VectorD\n"
        );
        subMenu.setVisible(true);
        subMenu.setManaged(true);
    }
    void seleccionarVector(Scene scene){
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case DIGIT1 -> definirVector("A");
                case DIGIT2 -> definirVector("B");
                case DIGIT3 -> definirVector("C");
                case DIGIT4 -> definirVector("D");
            }
        });

        btn1.setOnAction(e -> definirVector("A"));
        btn2.setOnAction(e -> definirVector("B"));
        btn3.setOnAction(e -> definirVector("C"));
        btn4.setOnAction(e -> definirVector("D"));
    }
    private void definirVector(String vectorId) {
        vectorSelec = vectorId;
        salidadefinirmatrices.setText("Definir Vector " + vectorId + "\nIngrese la cantidad de elementos:");
        pantalla.setOnAction(e -> {
            try {
                int dimension = Integer.parseInt(pantalla.getText());
                pantalla.clear();
                crearVector(vectorId, dimension);
            } catch (NumberFormatException ex) {
                salidadefinirmatrices.setText("Error");
                pantalla.clear();
            }
        });
    }
    private void crearVector(String vectorId, int dimension) {
        vector = new int[dimension];
        indiceVector = 0;

        salidadefinirmatrices.setText("Ingrese los elementos del vector " + vectorId + " (" + dimension + " valores):");

        pantalla.setOnAction(e -> {
            try {
                int valor = Integer.parseInt(pantalla.getText());
                pantalla.clear();

                vector[indiceVector++] = valor;

                if (indiceVector == dimension) {
                    vectores.put(vectorId, vector);
                    pantalla.setOnAction(null);
                    mostrarVector(vector);
                }
            } catch (NumberFormatException ex) {
                salidadefinirmatrices.setText("Error");
                pantalla.clear();
            }
        });
    }
    private void mostrarVector(int[] v) {
        StringBuilder sb = new StringBuilder("Vector ingresado:\n");
        for (int i = 0; i < v.length; i++) {
            sb.append(v[i]).append(" ");
        }
        salidadefinirmatrices.setText(sb.toString());
    }
    /*#### FIN de VECTORES####*/

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
        modoleyenda.setText("Modo matrices");
        subMenu.setText(
                "Definir Matriz\n" +
                        "1: MatrizA\n" +
                        "2: MatrizB\n" +
                        "3: MatrizC\n" +
                        "4: MatrizD\n"
        );
        subMenu.setVisible(true);
        subMenu.setManaged(true);
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
        btn1.setOnAction(e -> definirMatriz("A"));
        btn2.setOnAction(e -> definirMatriz("B"));
        btn3.setOnAction(e -> definirMatriz("C"));
        btn4.setOnAction(e -> definirMatriz("D"));
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


    private void procesarmatS() {
        String expresion  = pantalla.getText();
        String[] mat= expresion.split("\\+"); //Convierte la expresion en una array con las matrices ingresadas

        List<int[][]> matricesasumar = new ArrayList<>();

        for(String matri:mat){
            String id = matri.replace("Matriz", ""); //Elimina matriz de la expresion, dejando solamente los id
            matricesasumar.add(matrices.get(id));
        }
        int filas = matricesasumar.get(0).length; //Obtenemos las filas
        int columnas = matricesasumar.get(0)[0].length;//Obtenemos las columnas
        for (int[][] m : matricesasumar) {
            if (m.length != filas || m[0].length != columnas) {
                salidadefinirmatrices.setText("Las matrices deben tener las mismas dimensiones.");
                return;
            }
        }
        int[][] resultado = new int[filas][columnas];

        for (int[][] m : matricesasumar) {
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    resultado[i][j] += m[i][j];
                }
            }
        }
        mostrarMatriz(resultado);

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

    private void procesarmatM() {
        String expresion = pantalla.getText();
        String[] mat = expresion.split("\\*");


        String id1 = mat[0].trim().replace("Matriz", "");
        String id2 = mat[1].trim().replace("Matriz", "");


        int[][] m1 = matrices.get(id1);
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
        modoleyenda.setText("Modo ecuaciones");

        modo_ecuacion = true;
        modo_calculo = false;
        modo_matrices = false;
        modo_vectores = false;

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
        } catch (Exception e) {
            pantalla.setText("Error");
        }
    }
    /*#### FIN ####*/


    public void ocultarSubmenus() {
        // Ocultar submenús de matrices, ecuaciones, calculos y vectores.

        // CALCULOS

        // VECTORES

        // MATRICES

        // ECUACIONES
        btnSistema2x2.setVisible(false); btnSistema2x2.setManaged(false);
        btnSistema3x3.setVisible(false); btnSistema3x3.setManaged(false);
        ocultarCampos2x2();
        ocultarCampos3x3();
    }
    public void mcalculos() {
        modoleyenda.setText("Modo calculos");

        modo_ecuacion = false;
        modo_calculo = true;
        modo_matrices = false;
        modo_vectores = false;

        ocultarSubmenus();
    }

    public void mbtn4() {
        pantalla.setText(pantalla.getText() + 4);
    }

    public void mbtn2() {
        pantalla.setText(pantalla.getText() + 2);
    }

    public void mbtn5() {
        pantalla.setText(pantalla.getText() + 5);
    }

    public void mbtn7() {
        pantalla.setText(pantalla.getText() + 7);
    }

    public void mbtn0() {
        pantalla.setText(pantalla.getText() + 0);
    }

    public void mbtn8() {
        pantalla.setText(pantalla.getText() + 8);
    }

    public void mbtncoma() {
        pantalla.setText(pantalla.getText() + ",");
    }

    public void mbtn3() {
        pantalla.setText(pantalla.getText() + 3);
    }

    public void mbtn6() {
        pantalla.setText(pantalla.getText() + 6);
    }

    public void mbtndel() {
        String texto = pantalla.getText();
        if (!texto.isEmpty()) {
            pantalla.setText(texto.substring(0, texto.length() - 1));
        }
    }

    public void mbtnmult() {
        pantalla.setText(pantalla.getText() + "*");
    }

    public void mbtn9() {
        pantalla.setText(pantalla.getText() + 9);
    }


    public void mbtnresultado() {


        if(modo_calculo){
            if(pantalla.getText().contains("-")){
            procesarcalR();
        }else if(pantalla.getText().contains("+")){
            procesarcalS();
        }else if(pantalla.getText().contains("/")){
            procesarcalD();
        }else if(pantalla.getText().contains("*")){
            procesarcalM();
        }else if(pantalla.getText().contains("√")){
            procesarcalRaiz();
        }else if(pantalla.getText().contains("^")){
            procesarcalP();
        }

        }else if(modo_vectores){

        } else if(modo_matrices){
            if(pantalla.getText().contains("-")){
                procesarmatR();
            }else if(pantalla.getText().contains("+")){
                procesarmatS();
            }else if(pantalla.getText().contains("/")){
                procesarmatD();
            }else if(pantalla.getText().contains("*")){
                procesarmatM();
            }else if(pantalla.getText().contains("Det(")){
                procesarmatDet();
            }else if(pantalla.getText().contains("Inv(")){
                procesarmatInv();
            }

        }else if(modo_ecuacion){

            if(sis2x2){
                resolverSistema2x2();
            } else {
                resolverSistema3x3();
            }

        }
    }


    private void procesarmatInv() {
    }

    private void procesarmatDet() {
    }

    private void procesarmatD() {
    }

    public void mbtnsuma() {
        pantalla.setText(pantalla.getText() + "+");
    }

    public void mbtnac() {
        pantalla.clear();
    }

    public void mbtndiv() {
        pantalla.setText(pantalla.getText() + "/");
    }

    public void mbtnmenos() {
        pantalla.setText(pantalla.getText() + "-");
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

                    }
                    ;
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
