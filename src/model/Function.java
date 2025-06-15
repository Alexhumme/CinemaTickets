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
    private LocalDate date; // Fecha de la función
    private LocalTime time; // Hora de la función
    private Room room; // Sala donde se proyectará
    private Boolean is3D; // Indica si es una función 3D
    private LinkedList<Seat> occupiedSeats; // Lista de asientos ocupados
    private int duration; // Duración de la función en minutos

    /**
     * Constructor completo que incluye hora.
     */
    public Function(Movie movie, LocalDate date, LocalTime time, int duration, Room room, Boolean is3D) {
        this.movie = movie;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.room = room;
        this.is3D = is3D;
        this.occupiedSeats = new LinkedList<>();
    }
    
    /**
     * Constructor alternativo sin hora (puede ser útil para funciones con hora por definir).
     */
    public Function(Movie movie, LocalDate date, int duration, Room room, Boolean is3D) {
        this.movie = movie;
        this.date = date;
        this.duration = duration;
        this.room = room;
        this.is3D = is3D;
        this.occupiedSeats = new LinkedList<>();
    }

    // Getters y setters para acceder y modificar los atributos privados

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

    /**
     * Método para mostrar en consola los datos principales de la función.
     */
    public void printData() {
        System.out.println("Función de: " + movie.getTitle());
        System.out.println("Fecha: " + date);
        System.out.println("Hora: " + time);
        System.out.println("Sala: " + room);
    }

    /**
     * Devuelve la fecha de la función con formato "dd/MM/yyyy"
     * Esto es útil para mostrar al usuario o en interfaces gráficas.
     */
    public String getDateFormatted() {
        Date dates = java.sql.Date.valueOf(date); // Convierte LocalDate a java.util.Date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(dates);
    }

    /**
     * Devuelve la hora con formato "HH:mm" (hora en 24h)
     */
    public String getTimeFormatted() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(dtf);
    }

    /**
     * Devuelve fecha y hora juntas en formato "dd/MM/yyyy HH:mm"
     */
    public String getDateTimeFormatted() {
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateTime.format(formatter);
    }

    /**
     * Devuelve un String con formato personalizado para identificar la función.
     * Ejemplo: "Titanic (2025-06-21 - 20:00)"
     */
    @Override
    public String toString() {
        return movie.title + " (" + date + " - " + time + ")";
    }

    /**
     * Agrega un asiento a la lista de asientos ocupados.
     */
    public void addOccupiedSeat(Seat seat) {
        occupiedSeats.add(seat);
    }
}
