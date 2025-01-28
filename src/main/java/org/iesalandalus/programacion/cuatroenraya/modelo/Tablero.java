package org.iesalandalus.programacion.cuatroenraya.modelo;

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
        return tablero[FILAS + 1][COLUMNAS].estaOcupada();
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

    public boolean estaLleno(){
        boolean vacio = true;
        for (int fila = 0; fila < FILAS && !vacio; fila++){
            for (int columna = 0; columna < COLUMNAS && !vacio; columna++){
                if (tablero[fila][columna].estaOcupada()){
                    vacio = false;
                }
            }
        }
        return true;
    }

    private void comprobarFicha(Ficha ficha){
        Objects.requireNonNull(ficha, "La ficha no puede ser nula.");
    }

    private void comprobarColumna(int columna){
        if (columna <= 0){
            throw new IllegalArgumentException("Columna incorrecta");
        }

        if (columna > 7){
            throw new IllegalArgumentException("Columna incorrecta.");
        }
    }

    private int getPrimeraFilaVacia(int columna){
        boolean vacia = columnaLlena(columna);
        columna = -1;
        for (int fila = 0; fila < FILAS && !vacia; fila++){
            vacia = columnaLlena(columna);
            columna++;
        }
        return columna;
    }

    private boolean comprobarHorizontal( int fila, Ficha ficha){
        int fichasSeguidas = 0;
        for (; fila < FILAS && fichasSeguidas == 4; fila++){
            for (int columna = 0; columna < COLUMNAS; columna++){
                if (tablero[fila][columna].estaOcupada() && ficha.equals(ficha)){
                    fichasSeguidas = 1 + fichasSeguidas;
                }
            }
        }
        return fichasSeguidas == FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS;
    }

    private boolean comprobarVertical(int columna, Ficha ficha) {
        int fichasSeguidas = 0;
        for (int fila = 0; fila < FILAS && fichasSeguidas == 4; fila++) {
            for (; columna < COLUMNAS && tablero[fila][columna].estaOcupada(); columna++) {
                if (tablero[fila][columna].estaOcupada() && ficha.equals(ficha)) {
                    fichasSeguidas = 1 + fichasSeguidas;
                }
            }

        }
        return fichasSeguidas == FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS;
    }

    private int menor (int fila, int columna){
       return Math.min(fila,columna);
    }

    private boolean comprobarDiagonalNE(int filaActual, int columnaActual, Ficha ficha ){

    }
}
