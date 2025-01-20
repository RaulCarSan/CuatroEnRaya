package org.iesalandalus.programacion.cuatroenraya.modelo;

import java.util.Objects;

public class Casilla {
    private Ficha Ficha;

    public Casilla() {
        Ficha = null;

    }

    public Ficha getFicha() {
        return Ficha;
    }

    public void setFicha(Ficha ficha) throws CuatroEnRayaExcepcion{
        Objects.requireNonNull(ficha,"No se puede poner una ficha nula.");
        if (estaOcupada()){
            throw new CuatroEnRayaExcepcion("La casilla ya contiene una ficha.");
        }
        this.Ficha = ficha;
    }

    public boolean estaOcupada(){
        return (getFicha() != null);
    }


    @Override
    public String toString() {
        if (estaOcupada()){
            return String.format("%s", Ficha);
        }else {
            return " ";
        }

    }
}

