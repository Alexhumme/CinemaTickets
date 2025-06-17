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
 * Clase Function que representa una función o proyección de una película.
 * Contiene información como película, fecha, hora, duración, sala, si es 3D, y asientos ocupados.
 * 
 * Autor: AlexVB
 */
public class Function {

    private Movie movie; // Película asociada a la función
    
    private int dateDay;    
    private int dateMonth;  
    private int dateYear;

    private LocalTime time; // Hora de la función
    private int duration; // Duración de la función en minutos
    private Room room; // Sala donde se proyectará
    private Boolean is3D; // Indica si es una función 3D
    private LinkedList<Seat> occupiedSeats; // Lista de asientos ocupados

    // Constructor completo
    public Function(Movie movie, int dateDay, int dateMonth, int dateYear, LocalTime time, int duration, Room room, Boolean is3D) {
        this.movie = movie;
        this.dateDay = dateDay;
        this.dateMonth = dateMonth;
        this.dateYear = dateYear;
        this.time = time;
        this.duration = duration;
        this.room = room;
        this.is3D = is3D;
        this.occupiedSeats = new LinkedList<>();
    }

    // Getters y Setters
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getDateDay() {
        return dateDay;
    }

    public void setDateDay(int dateDay) {
        this.dateDay = dateDay;
    }

    public int getDateMonth() {
        return dateMonth;
    }

    public void setDateMonth(int dateMonth) {
        this.dateMonth = dateMonth;
    }

    public int getDateYear() {
        return dateYear;
    }

    public void setDateYear(int dateYear) {
        this.dateYear = dateYear;
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

    // Métodos de formato

    /**
     * Devuelve la fecha con formato dd/MM/yyyy
     */
    public String getDateFormatted() {
        return String.format("%02d/%02d/%04d", dateDay, dateMonth, dateYear);
    }

    /**
     * Devuelve la hora con formato HH:mm
     */
    public String getTimeFormatted() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(dtf);
    }

    /**
     * Devuelve fecha y hora juntas en formato dd/MM/yyyy HH:mm
     */
    public String getDateTimeFormatted() {
        return getDateFormatted() + " " + getTimeFormatted();
    }

    /**
     * toString personalizado
     */
    @Override
    public String toString() {
        return movie.getTitle() + " (" + getDateFormatted() + " - " + getTimeFormatted() + ")";
    }

    /**
     * Agrega un asiento ocupado
     */
    public void addOccupiedSeat(Seat seat) {
        occupiedSeats.add(seat);
    }

    /**
     * Imprime los datos de la función
     */
    public void printData() {
        System.out.println("Función de: " + movie.getTitle());
        System.out.println("Fecha: " + getDateFormatted());
        System.out.println("Hora: " + getTimeFormatted());
        System.out.println("Sala: " + room);
    }
}
