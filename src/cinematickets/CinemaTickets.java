/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cinematickets;

import cinematickets.frame.MainFrame;
import model.Movie;
import model.generic.LinkedList;

/**
 *
 * @author AlexVB
 */
public class CinemaTickets {

    private static CinemaTickets instance;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }
    
    public static CinemaTickets getInstance() {
        if (instance == null) {
            instance = new CinemaTickets();
        }
        return instance;
    }
    
    // Otras variables públicas
    public LinkedList<Movie> movies = new LinkedList<>();
}
