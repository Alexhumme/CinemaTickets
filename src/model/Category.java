/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public enum Category {
    ACTION("Acción", "🎬"),
    COMEDY("Comedia", "😂"),
    DRAMA("Drama", "🎭"),
    HORROR("Horror", "😱"),
    FAMILY("Familiar", "👪"),
    ROMANTIC("Romantica", "♥"),
    SCI_FI("Ciencia Ficcion", "👽"),
    ADVENTURE("Aventura", "🌄"),    
    FANTASY("Fantasia", "🔮"),
    SUSPENSE("Suspenso", "🕶"),
    DOCUMENTAL("Documental", "📖");

    public final String label;
    public final String icon;

    Category(String label, String icon) {
        this.label = label;
        this.icon = icon;
    }

}
