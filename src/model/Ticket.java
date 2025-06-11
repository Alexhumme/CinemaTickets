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

    private int id;
    private Function function;
    private Client client;
    private Seat seat;

    public Ticket(int id, Function function, Client client, Seat seat) {
        this.id = id;
        this.function = function;
        this.client = client;
        this.seat = seat;
    }

    // Getters
    public int getId() {
        return id;
    }
    
    public Function getFunction() {
        return function;
    }

    public Client getClient() {
        return client;
    }

    public Seat getSeat() {
        return seat;
    }


    // Setters
    public void setFunction(Function function) {
        this.function = function;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    @Override
    public String toString() {
        return client.getName() + " - " + function.getMovie().getTitle() +
               " - " + seat.toString() + " asiento ";
    }
}
