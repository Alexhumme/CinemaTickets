/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import model.generic.LinkedList;

/**
 *
 * @author AlexVB
 */
public class Room {
    private String id;
    private int height;  
    private int width;
    private LinkedList<Seat> seats = new LinkedList<Seat>();
    public Room(String id, int width,int height) {
        this.id = id;
        this.width = width;
        this.height = height;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight () {
        return height;
    }
    
    public LinkedList<Seat> getSeats () {
        return seats;
    }
    
    public void addSeat(Seat seat) {
        seats.add(seat);
    }
    
    @Override
    public String toString () {
        return id + " (" + width + "x" + height +")";
    }
    
    
}
