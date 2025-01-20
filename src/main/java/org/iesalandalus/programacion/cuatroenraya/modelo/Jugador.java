package org.iesalandalus.programacion.cuatroenraya.modelo;

import java.util.Objects;

public record Jugador(String nombre, Ficha colorFicha) {

    public Jugador{
    }

    private void validarNombre(String nombre) throws CuatroEnRayaExcepcion{
        Objects.requireNonNull(nombre,"El nombre no puede ser nulo.");
        String validar = "\\d[A-Z]{1}[a-záéíóú]*";
        if (!nombre.matches(validar)){
            throw new CuatroEnRayaExcepcion("El nombre no es valido.");
        }
    }

    private void validarCalorFichas(Ficha colorFichas){
        Objects.requireNonNull(colorFichas,"El color de las fichas no puede ser nulo.");
    }

    @Override
    public String toString() {
        return String.format("[nombre=%s, colorFicha=%s]", nombre, colorFicha);
    }


}
