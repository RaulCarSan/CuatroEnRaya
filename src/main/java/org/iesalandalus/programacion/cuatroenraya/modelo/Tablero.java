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

    public boolean estaVacio(){
        boolean ocupado = false;
        for (int columna = 0; columna < COLUMNAS && !ocupado; columna++){
            if (!columnaVacia(columna)){
             ocupado = true;
            }
        }
        return ocupado;
    }

    public boolean estaLleno() {
        boolean vacio = false;
        int columnasLlenas = 0;
        for (int fila = 0; fila < FILAS; fila++){
            for (int columna = 0; columna < COLUMNAS; columna++){
                if (columnaLlena(columna)){
                    columnasLlenas = columnasLlenas + 1;

                }
            }
        }
        return columnasLlenas == COLUMNAS;
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
        return columna;
    }

    private boolean comprobarHorizontal( int fila, Ficha ficha){
        int fichasSeguidas = 0;
        for (; fila < FILAS && objetivoAlcanzado(fichasSeguidas); fila++){
            for (int columna = 0; columna < COLUMNAS; columna++){
                if (tablero[fila][columna].estaOcupada() && tablero[fila][columna].getFicha().equals(ficha)){
                    fichasSeguidas = 1 + fichasSeguidas;
                }
            }
        }
        return fichasSeguidas == FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS;
    }

    private boolean comprobarVertical(int columna, Ficha ficha) {
        int fichasSeguidas = 0;
        for (int fila = 0; fila < FILAS && objetivoAlcanzado(fichasSeguidas); fila++) {
            for (; columna < COLUMNAS && tablero[fila][columna].estaOcupada(); columna++) {
                if (tablero[fila][columna].estaOcupada() && tablero[fila][columna].getFicha().equals(ficha)) {
                    fichasSeguidas = 1 + fichasSeguidas;
                }
            }

        }
        return objetivoAlcanzado(fichasSeguidas);
    }

    private int menor (int fila, int columna){
       return Math.min(fila,columna);
    }

    private boolean comprobarDiagonalNE(int filaActual, int columnaActual, Ficha ficha ){
        int fichasSeguidas = 0;
        int desplazamiento = menor(filaActual,columnaActual);
        int filaInicial = filaActual - desplazamiento;
        int columnaInicial = columnaActual - desplazamiento;
        for (int fila = filaInicial, columna = columnaInicial;columnaActual < COLUMNAS && filaActual < FILAS || objetivoAlcanzado(fichasSeguidas);columnaActual--, filaActual--){
            if (tablero[fila][columna].estaOcupada() && tablero[fila][columna].getFicha().equals(ficha)){
                fichasSeguidas = fichasSeguidas + 1;
            }
        }
        return objetivoAlcanzado(fichasSeguidas);
    }

    private boolean comprobarDiagonalNO(int filaActual, int columnaActual, Ficha ficha ){
        int fichasSeguidas = 0;
        int desplazamiento = menor(filaActual,columnaActual);
        int filaInicial = filaActual - desplazamiento;
        int columnaInicial = columnaActual + desplazamiento;
        for (int fila = filaInicial, columna = columnaInicial;columnaActual < COLUMNAS && filaActual < FILAS || objetivoAlcanzado(fichasSeguidas);columnaActual++, filaActual++){
            if (tablero[fila][columna].estaOcupada() && tablero[fila][columna].getFicha().equals(ficha)){
                fichasSeguidas = fichasSeguidas + 1;
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

    public boolean introducirFicha(int columna,Ficha ficha) throws CuatroEnRayaExcepcion{
        boolean victoria;
        comprobarColumna(columna);
        comprobarFicha(ficha);
        if (columnaLlena(columna)){
            throw new IllegalArgumentException("Columna llena.");
        }

        do {
            int fila = getPrimeraFilaVacia(columna);
            victoria = comprobarTirada(fila,columna,ficha);
            fila = fila + 1;
        }while (!victoria);

        return victoria;
    }

    //@Override
    //public String toString() {
      //  return String.format("[tablero=%s]", Arrays.toString(tablero));
    //}

}
