/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author B8
 */
public class Ticket {
// Atributos privados del ticket
    private int id;                 // Identificador único del ticket
    private Function function;     // Función (película + sala + hora) asociada al ticket
    private Client client;         // Cliente que compró el ticket
    private Seat seat;             // Asiento reservado en la sala

    public Ticket(int id, Function function, Client client, Seat seat) {
        this.id = id;
        this.function = function;
        this.client = client;
        this.seat = seat;
    }

    // ===========================
    // Getters (Métodos de acceso)
    // ===========================

    /**
     * Devuelve el ID del ticket.
     * @return ID numérico del ticket
     */
    public int getId() {
        return id;
    }

    /**
     * Devuelve la función (película + sala + horario) asociada al ticket.
     * @return Objeto Function
     */
    public Function getFunction() {
        return function;
    }

    /**
     * Devuelve el cliente que compró el ticket.
     * @return Objeto Client
     */
    public Client getClient() {
        return client;
    }

    /**
     * Devuelve el asiento reservado.
     * @return Objeto Seat
     */
    public Seat getSeat() {
        return seat;
    }

    // ===========================
    // Setters (Métodos modificadores)
    // ===========================

    /**
     * Permite cambiar la función asociada al ticket.
     * @param function Nueva función
     */
    public void setFunction(Function function) {
        this.function = function;
    }

    /**
     * Permite cambiar el cliente asociado al ticket.
     * @param client Nuevo cliente
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Permite cambiar el asiento reservado.
     * @param seat Nuevo asiento
     */
    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    /**
     * Sobrescribe el método toString para devolver una representación en texto del ticket.
     * Esto es útil para mostrar los datos del ticket en listas o interfaces gráficas.
     * 
     * El formato devuelto es:
     * [Nombre del cliente] - [Título de la película] - [Identificador del asiento] asiento
     *
     * Ejemplo: "Ana Gómez - Barbie - B5 asiento"
     *
     * @return Cadena de texto descriptiva del ticket
     */
    @Override
    public String toString() {
        return client.getName() + " - "                       // Nombre del cliente
             + function.getMovie().getTitle() + " - "         // Título de la película
             + seat.toString() + " asiento ";                 // Asiento reservado (ej: "A1 asiento")
    }
}
