package org.iesalandalus.programacion.cuatroenraya.modelo;

import java.util.Arrays;
import java.util.Objects;

public class Tablero {
    public static final int FILAS = 6;
    public static final int COLUMNAS = 7;
    public static final int FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS = 4;
    private Casilla[][] tablero = new Casilla[FILAS][COLUMNAS];

    public Tablero(){
        for (int fila = 0; fila < FILAS; fila++){
            for (int columna = 0; columna < COLUMNAS; columna++){
                tablero[fila][columna] = new Casilla();
            }
        }
    }

    private boolean columnaVacia(int columna){
         return !tablero[0][columna].estaOcupada();
    }

    private boolean columnaLlena(int columna){
        return tablero[5][columna].estaOcupada();
    }

    public boolean estaVacio() {
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                if (tablero[fila][columna].estaOcupada()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean estaLleno() {
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                if (!tablero[fila][columna].estaOcupada()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void comprobarFicha(Ficha ficha){
        Objects.requireNonNull(ficha, "La ficha no puede ser nula.");
    }

    private void comprobarColumna(int columna){
        if (columna < 0){
            throw new IllegalArgumentException("Columna incorrecta.");
        }

        if (columna >= 7){
            throw new IllegalArgumentException("Columna incorrecta.");
        }
    }

    private int getPrimeraFilaVacia(int columna) throws CuatroEnRayaExcepcion{
        boolean vacia = false;
        int filaInicial = -1;
        for (int fila = 0; fila < FILAS && !vacia; fila++){
            if (!tablero[fila][columna].estaOcupada()){
             filaInicial = fila;
             vacia = true;
            }

        }
        if (filaInicial == -1){
            throw new CuatroEnRayaExcepcion("La fila esta llena.");
        }
        return filaInicial;
    }

    private boolean comprobarHorizontal(int fila, Ficha ficha) {
        int fichasSeguidas = 0;
        for (int columna = 0; columna < COLUMNAS; columna++) {
            if (tablero[fila][columna].estaOcupada() && tablero[fila][columna].getFicha().equals(ficha)) {
                fichasSeguidas++;
                if (objetivoAlcanzado(fichasSeguidas)) {
                    return true;
                }
            } else {
                fichasSeguidas = 0;
            }
        }
        return false;
    }

    private boolean comprobarVertical(int columna, Ficha ficha) {
        int fichasSeguidas = 0;
        for (int fila = 0; fila < FILAS; fila++) {
            if (tablero[fila][columna].estaOcupada() && tablero[fila][columna].getFicha().equals(ficha)) {
                fichasSeguidas++;
                if (objetivoAlcanzado(fichasSeguidas)) {
                    return true;
                }
            } else {
                fichasSeguidas = 0;
            }
        }
        return false;
    }

    private int menor (int fila, int columna){
       return Math.min(fila,columna);
    }

    private boolean comprobarDiagonalNE(int filaActual, int columnaActual, Ficha ficha) {
        int fichasSeguidas = 0;
        int desplazamiento = menor(filaActual, columnaActual);
        int filaInicial = filaActual - desplazamiento;
        int columnaInicial = columnaActual - desplazamiento;

        for (int fila = filaInicial, columna = columnaInicial; fila < FILAS && columna < COLUMNAS; fila++, columna++) {

            if (tablero[fila][columna].estaOcupada() && tablero[fila][columna].getFicha().equals(ficha)) {
                fichasSeguidas++;
            }
        }
        return fichasSeguidas == FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS;
    }

    private boolean comprobarDiagonalNO(int filaActual, int columnaActual, Ficha ficha) {
        int fichasSeguidas = 0;
        int desplazamiento = menor(filaActual, COLUMNAS - columnaActual - 1);
        int filaInicial = filaActual - desplazamiento;
        int columnaInicial = columnaActual + desplazamiento;

        for (int fila = filaInicial, columna = columnaInicial; fila < FILAS && columna >= 0; fila++, columna--) {
            if (tablero[fila][columna].estaOcupada() && tablero[fila][columna].getFicha().equals(ficha)) {
                fichasSeguidas++;
            }
        }

        return fichasSeguidas == FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS;
    }

    private boolean objetivoAlcanzado(int fichasIgualesConsecutivas){
        return fichasIgualesConsecutivas == FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS;
    }

    private boolean comprobarTirada(int fila,int columna,Ficha ficha){
        boolean victoria = false;
        if (comprobarHorizontal(fila,ficha)){
            victoria = true;
        }

        if (comprobarVertical(columna,ficha)){
            victoria = true;
        }

        if (comprobarDiagonalNE(fila,columna,ficha)){
            victoria = true;
        }

        if (comprobarDiagonalNO(fila,columna,ficha)){
            victoria = true;
        }

        return victoria;
    }

    public boolean introducirFicha(int columna, Ficha ficha) throws CuatroEnRayaExcepcion {
        comprobarColumna(columna);
        comprobarFicha(ficha);

        if (columnaLlena(columna)) {
            throw new CuatroEnRayaExcepcion("Columna llena.");
        }

        int fila = getPrimeraFilaVacia(columna);
        tablero[fila][columna].setFicha(ficha);

        return comprobarTirada(fila, columna, ficha);
    }

    //@Override
    //public String toString() {return String.format("[tablero=%s]", Arrays.toString(tablero));}

}
