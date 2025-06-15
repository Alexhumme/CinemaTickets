/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import model.generic.LinkedList;

/**
 * Clase Room que representa una sala de cine.
 * Contiene información como el identificador de la sala, su tamaño (alto y ancho),
 * y la lista de sillas disponibles.
 * 
 * Autor: AlexVB
 */
public class Room {
    private String id; // Identificador único de la sala
    private int height;  // Número de filas de sillas (alto)
    private int width;   // Número de columnas de sillas (ancho)
    private LinkedList<Seat> seats = new LinkedList<Seat>(); // Lista enlazada de sillas en la sala

    /**
     * Constructor de la clase Room.
     * @param id Identificador de la sala
     * @param width Número de columnas de sillas (ancho)
     * @param height Número de filas de sillas (alto)
     */
    public Room(String id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
    }

    // Devuelve el identificador de la sala
    public String getId() {
        return id;
    }

    // Establece un nuevo identificador para la sala
    public void setId(String id) {
        this.id = id;
    }

    // Devuelve el número de columnas de sillas
    public int getWidth() {
        return width;
    }

    // Devuelve el número de filas de sillas
    public int getHeight () {
        return height;
    }

    // Devuelve la lista de sillas
    public LinkedList<Seat> getSeats () {
        return seats;
    }

    // Agrega una silla a la lista de sillas de la sala
    public void addSeat(Seat seat) {
        seats.add(seat);
    }

    /**
     * Método sobrescrito que devuelve una representación en texto de la sala.
     * Ejemplo: "Sala1 (10x5)" donde 10 es ancho (columnas) y 5 es alto (filas)
     */
    @Override
    public String toString () {
        return id + " (" + width + "x" + height + ")";
    }
}
