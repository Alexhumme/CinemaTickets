/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Objects;

/**
 *
 * @author AlexVB
 */
public class Seat {

    private String asiento;

    public Seat(String asiento) {
        this.asiento = asiento;
    }

    public String getAsiento() {
        return asiento;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Seat seat = (Seat) obj;
        return Objects.equals(asiento, seat.asiento); // Usa tu identificador único
    }

    @Override
    public int hashCode() {
        return Objects.hash(asiento);
    }
}
