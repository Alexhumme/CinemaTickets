/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import model.generic.LinkedList;

/**
 *
 * @author AlexVB
 */
public class Function {

    private Movie movie;
    private LocalDate date;
    private LocalTime time;
    private Room room; // puedes cambiarlo por una clase `Room` si deseas más detalle
    private Boolean is3D;
    private LinkedList<Seat> occupiedSeats;
    private int duration;

    public Function(Movie movie, LocalDate date, LocalTime time, int duration, Room room, Boolean is3D) {
        this.movie = movie;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.room = room;
        this.is3D = is3D;
        this.occupiedSeats = new LinkedList<>();
    }
    
        public Function(Movie movie, LocalDate date, int duration, Room room, Boolean is3D) {
        this.movie = movie;
        this.date = date;
        this.duration = duration;
        this.room = room;
        this.is3D = is3D;
        this.occupiedSeats = new LinkedList<>();
    }

    // Getters y setters
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Boolean getIs3D() {
        return is3D;
    }

    public void setIs3D(Boolean is3D) {
        this.is3D = is3D;
    }

    public LinkedList<Seat> getOccupiedSeats() {
        return occupiedSeats;
    }

    public void setOccupiedSeats(LinkedList<Seat> occupiedSeats) {
        this.occupiedSeats = occupiedSeats;
    }

    // Método para mostrar información
    public void printData() {
        System.out.println("Función de: " + movie.getTitle());
        System.out.println("Fecha: " + date);
        System.out.println("Hora: " + time);
        System.out.println("Sala: " + room);
    }

    public String getDateFormatted() {
        LocalDate localDate = date;
        Date dates = java.sql.Date.valueOf(localDate); // convierte LocalDate a Date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(dates);
    }

    public String getTimeFormatted() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(dtf);
    }

    public String getDateTimeFormatted() {
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateTime.format(formatter);
    }

    @Override
    public String toString() {
        return movie.title + "(" + date + " - " + time + ")";
    }

    public void addOccupiedSeat(Seat seat) {
        occupiedSeats.add(seat);
    }
}
