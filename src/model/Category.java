/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Enum que representa las distintas categorías que puede tener una película.
 * Cada categoría tiene un nombre descriptivo (label) y un ícono representativo (icon).
 */
public enum Category {
    // Definición de las constantes del enum con sus etiquetas y emojis representativos
    ACTION("Acción", "🎬"),
    COMEDY("Comedia", "😂"),
    DRAMA("Drama", "🎭"),
    HORROR("Horror", "😱"),
    FAMILY("Familiar", "👪"),
    ROMANTIC("Romántica", "♥"),
    SCI_FI("Ciencia Ficción", "👽"),
    ADVENTURE("Aventura", "🌄"),
    FANTASY("Fantasía", "🔮"),
    SUSPENSE("Suspenso", "🕶"),
    DOCUMENTAL("Documental", "📖");

    // Atributos que almacenan la etiqueta y el ícono de cada categoría
    public final String label; // Nombre legible para mostrar en la interfaz (en español)
    public final String icon;  // Emoji decorativo que representa visualmente la categoría

    /**
     * Constructor privado que se usa internamente para crear las constantes del enum.
     * @param label Nombre o descripción de la categoría.
     * @param icon Emoji representativo.
     */
    Category(String label, String icon) {
        this.label = label;
        this.icon = icon;
    }

    /**
     * Devuelve la etiqueta o nombre de la categoría.
     * @return String con el nombre.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Devuelve el emoji que representa visualmente la categoría.
     * @return String con el emoji.
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Método sobrescrito que retorna una representación amigable de la categoría.
     * Esto es útil para mostrar en comboboxes, tablas o logs.
     * @return String en el formato: "Nombre Emoji"
     */
    @Override
    public String toString(){
        return getLabel() + " " + getIcon();
  
    }
}
