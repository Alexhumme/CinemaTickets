/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author AlexVB
 */
public enum Classification {
    G("G","Apto para todos los públicos.", 0),
    PG("PG","Algunos contenidos pueden no ser apropiados para niños, se recomienda que los padres guíen.", 5),
    PG_13("PG-13","Algunos contenidos pueden no ser apropiados para niños menores de 13 años, se recomienda que los padres guíen.", 13),
    R("R","Algunos contenidos pueden no ser apropiados para menores de 17 años, se recomienda que los menores estén acompañados por un adulto.", 15),
    NC_17("NC-17","No se admiten menores de 17 años.", 17),
    A14("14A","Recomendada para mayores de 14 años.", 14),
    A18("18A","Recomendada para mayores de 18 años.", 18);
    
    public final String rate;
    public final String description;
    public int minAge;
        
    Classification(String rate, String description, int minAge) {
        this.rate = rate;
        this.description= description;
        this.minAge = minAge;
    }
    
    public String getRate () {
        return rate;
    }
    
    @Override
    public String toString() {
        return rate;
    }   
}