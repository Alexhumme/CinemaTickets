/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Enum que representa la clasificación por edades de una película.
 * Cada clasificación tiene un código (rate), una descripción y una edad mínima recomendada.
 */
public enum Classification {

    // Definición de clasificaciones estándar con sus respectivas descripciones y edad mínima sugerida
    G("G", "Apto para todos los públicos.", 0),
    PG("PG", "Algunos contenidos pueden no ser apropiados para niños, se recomienda que los padres guíen.", 5),
    PG_13("PG-13", "Algunos contenidos pueden no ser apropiados para niños menores de 13 años, se recomienda que los padres guíen.", 13),
    R("R", "Algunos contenidos pueden no ser apropiados para menores de 17 años, se recomienda que los menores estén acompañados por un adulto.", 15),
    NC_17("NC-17", "No se admiten menores de 17 años.", 17),
    A14("14A", "Recomendada para mayores de 14 años.", 14),
    A18("18A", "Recomendada para mayores de 18 años.", 18);

    // Atributos públicos del enum
    public final String rate;         // Código corto que identifica la clasificación (por ejemplo, "PG-13")
    public final String description;  // Descripción textual de la clasificación
    public int minAge;                // Edad mínima recomendada o requerida para ver la película

    /**
     * Constructor privado del enum.
     * Se utiliza para asignar los valores de cada constante.
     * @param rate Código de clasificación.
     * @param description Descripción detallada de la clasificación.
     * @param minAge Edad mínima sugerida.
     */
    Classification(String rate, String description, int minAge) {
        this.rate = rate;
        this.description = description;
        this.minAge = minAge;
    }

    /**
     * Devuelve el código de clasificación (rate), por ejemplo: "PG-13"
     * @return String con el código de clasificación.
     */
    public String getRate() {
        return rate;
    }

    /**
     * Devuelve el texto que se mostrará al convertir el enum a cadena.
     * En este caso, solo se muestra*
    */
    @Override
    public String toString() {
        return rate;
    }   
}