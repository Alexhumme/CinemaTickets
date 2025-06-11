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

    private Function function;
    private Client client;
    private Seat seat;

    public Ticket(Function function, Client client, Seat seat) {
        this.function = function;
        this.client = client;
        this.seat = seat;
    }

    // Getters
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

    public void setSeats(List<String> seats) {
        this.seats = seats;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return client.getName() + " - " + function.getMovie().getTitle() +
               " - " + seats.size() + " asiento(s) - $" + totalPrice;
    }
}
