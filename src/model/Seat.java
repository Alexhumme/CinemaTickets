/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Objects;

/**
 * Clase Seat que representa un asiento dentro de una sala de cine.
 * Cada asiento es identificado por un código único (por ejemplo: "A1", "B5", etc.)
 * 
 * Autor: AlexVB
 */
public class Seat {

    // Identificador único del asiento, por ejemplo: "A1", "B2"
    private String asiento;

    /**
     * Constructor de la clase Seat
     * @param asiento Identificador del asiento
     */
    public Seat(String asiento) {
        this.asiento = asiento;
    }

    /**
     * Devuelve el identificador del asiento
     * @return Código del asiento (ej: "A1")
     */
    public String getAsiento() {
        return asiento;
    }

    /**
     * Sobrescribe el método equals para comparar si dos objetos Seat son iguales
     * basándose en su identificador de asiento.
     * @param obj Objeto a comparar
     * @return true si los asientos son iguales, false si no
     */
    @Override
    public boolean equals(Object obj) {
        // Si se comparan con sí mismo, son iguales
        if (this == obj) {
            return true;
        }

        // Si el objeto es null o no es de la misma clase, no son iguales
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        // Se hace downcasting y se compara el identificador del asiento
        Seat seat = (Seat) obj;
        return Objects.equals(asiento, seat.asiento);
    }

    /**
     * Sobrescribe el método hashCode, necesario si usas esta clase en colecciones
     * como HashSet, HashMap, etc. Se basa en el identificador del asiento.
     * @return Código hash del asiento
     */
    @Override
    public int hashCode() {
        return Objects.hash(asiento);
    }
}

