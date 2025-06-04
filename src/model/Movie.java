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
public class Movie {
    public String title;
    public String posterSrc;
    public Classification classification;
    public LinkedList<Category> categories;

    public Movie(
            String title, 
            String posterSrc, 
            Classification classification, 
            LinkedList<Category> categories
    ) {
        this.title = title;
        this.posterSrc = posterSrc;
        this.classification = classification;
        this.categories = categories;
    }

}
