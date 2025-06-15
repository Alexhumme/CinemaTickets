/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cinematickets;

import cinematickets.frame.MainFrame;
import model.Client;
import model.Function;
import model.Movie;
import model.Room;
import model.Ticket;
import model.generic.LinkedList;

/**
 * Clase principal de la aplicación de venta de boletos de cine.
 * Se encarga de lanzar la interfaz gráfica (MainFrame) y de almacenar los datos
 * centrales como películas, clientes, salas, funciones y tickets.
 * 
 * Utiliza el patrón Singleton para asegurar que solo exista una instancia global
 * de CinemaTickets durante la ejecución de la aplicación.
 * 
 * Autor: AlexVB
 */
public class CinemaTickets {

    // Instancia única de la clase (Singleton)
    private static CinemaTickets instance;

    /**
     * Método main: punto de entrada del programa.
     * Aquí se inicializa y muestra la interfaz gráfica principal.
     * 
     * @param args argumentos de línea de comandos (no se usan)
     */
    public static void main(String[] args) {
        // Crea e inicializa la ventana principal de la aplicación
        MainFrame frame = new MainFrame();
        frame.setVisible(true); // Muestra la ventana al usuario
    }

    /**
     * Método estático para obtener la instancia única de CinemaTickets.
     * Si aún no existe, la crea.
     * 
     * @return la instancia única de CinemaTickets
     */
    public static CinemaTickets getInstance() {
        if (instance == null) {
            instance = new CinemaTickets();
        }
        return instance;
    }

    // =========================
    // Simulación de base de datos
    // =========================

    // Lista enlazada de todas las películas registradas
    public LinkedList<Movie> movies = new LinkedList<>();

    // Lista enlazada de todos los clientes registrados
    public LinkedList<Client> clients = new LinkedList<>();

    // Lista enlazada de salas disponibles en el cine
    public LinkedList<Room> rooms = new LinkedList<>();

    // Lista enlazada de funciones programadas (película + fecha/hora/sala)
    public LinkedList<Function> functions = new LinkedList<>();

    // Lista enlazada de tickets vendidos
    public LinkedList<Ticket> tickets = new LinkedList<>();

    // Contador global de tickets vendidos (se puede usar como ID incremental)
    public int ticketsCounter = 0;
}
