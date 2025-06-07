/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import model.generic.LinkedList;
import model.generic.Node;

/**
 *
 * @author AlexVB
 */
public class Movie {
    public String title;
    public String sinopsis;
    public String posterSrc;
    public String classification;
    public LinkedList<String> categories;
    public Boolean onBillboard;
    
    public Movie(
            String title, 
            String sinopsis,
            String posterSrc, 
            String classification, 
            LinkedList<String> categories,
            Boolean onBillboard
    ) {
        this.title = title;
        this.sinopsis = sinopsis;
        this.posterSrc = posterSrc;
        this.classification = classification;
        this.categories = categories;
        this.onBillboard = onBillboard;
    }

    public void printData() {
        System.out.println("=== PELÍCULA ===");
        System.out.println("Título: " + (title !=null? title : "N/A"));
        System.out.println("Sinopsis: " + (sinopsis != null ? sinopsis : "N/A"));
        System.out.println("Clasificación: " + (classification != null ? classification : "N/A"));

        System.out.print("Categorías: ");
        if (categories != null && categories.head != null) {
            Node<String> current = categories.head;
            while (current != null) {
                System.out.print(current.data);
                if (current.next != null) System.out.print(", ");
                current = current.next;
            }
            System.out.println();
        } else {
            System.out.println("Ninguna");
        }

        System.out.println("En cartelera: " + (onBillboard ? "Sí" : "No"));
        System.out.println("================\n");
    }

}
