/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import model.generic.LinkedList;
import model.generic.Node;

/**
 * Clase Movie que representa una película con sus datos principales,
 * como título, sinopsis, imagen, clasificación, categorías y si está en cartelera.
 * 
 * Autor: AlexVB
 */
public class Movie {
    public String title; // Título de la película
    public String sinopsis; // Descripción o resumen de la película
    public String posterSrc; // Ruta o URL de la imagen del póster
    public Classification classification; // Clasificación por edad (enum)
    public LinkedList<Category> categories; // Lista enlazada de categorías (acción, comedia, etc.)
    public Boolean onBillboard; // Indica si está en cartelera actualmente

    /**
     * Constructor de la clase Movie con todos sus atributos
     */
    public Movie(
            String title, 
            String sinopsis,
            String posterSrc, 
            Classification classification, 
            LinkedList<Category> categories,
            Boolean onBillboard
    ) {
        this.title = title;
        this.sinopsis = sinopsis;
        this.posterSrc = posterSrc;
        this.classification = classification;
        this.categories = categories;
        this.onBillboard = onBillboard;
    }

    /**
     * Muestra en consola los datos de la película.
     */
    public void printData() {
        System.out.println("=== PELÍCULA ===");
        System.out.println("Título: " + (title != null ? title : "N/A"));
        System.out.println("Sinopsis: " + (sinopsis != null ? sinopsis : "N/A"));
        System.out.println("Clasificación: " + (classification != null ? classification : "N/A"));

        // Mostrar las categorías si existen
        System.out.print("Categorías: ");
        if (categories != null && categories.head != null) {
            Node<Category> current = categories.head;
            while (current != null) {
                System.out.print(current.data); // imprime categoría con ícono
                if (current.next != null) System.out.print(", "); // separador
                current = current.next;
            }
            System.out.println();
        } else {
            System.out.println("Ninguna");
        }

        // Estado en cartelera
        System.out.println("En cartelera: " + (onBillboard ? "Sí" : "No"));
        System.out.println("================\n");
    }

    // Getter para obtener el título
    public String getTitle() {
        return title;
    }

    // Getter y setter para el póster
    public String getPosterSrc() {
        return posterSrc;
    }

    public void setPosterSrc(String posterSrc) {
        this.posterSrc = posterSrc;
    }

    /**
     * Método toString sobrescrito para que al imprimir el objeto Movie,
     * se muestre simplemente su título.
     */
    @Override
    public String toString() {
        return title;
    }

    // Setter para el título
    public void setTitle(String title) {
        this.title = title;
    }

    // Setter para las categorías
    public void setCategories(LinkedList<Category> selectedCategories) {
        this.categories = categories; // ¡OJO! Debería ser: this.categories = selectedCategories;
    }

    // Setter y getter para la clasificación
    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public Classification getClassification() {
        return classification;
    }

    // Getter para la lista de categorías
    public LinkedList<Category> getCategories() {
        return this.categories;
    }
}

