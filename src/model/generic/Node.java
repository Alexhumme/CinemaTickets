/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.generic;

/**
 * Clase que representa un nodo en una lista doblemente enlazada.
 * @param <T> tipo de dato que contiene el nodo.
 */
public class Node<T> {
    public T data;         // Dato almacenado en el nodo (puede ser cualquier tipo T)
    public Node<T> next;   // Referencia al siguiente nodo en la lista
    public Node<T> prev;   // Referencia al nodo anterior en la lista

    /**
     * Constructor que crea un nuevo nodo con el dato proporcionado.
     * Los punteros next y prev se inicializan como null.
     * 
     * @param data el dato que se almacenará en el nodo.
     */
    public Node(T data) {
        this.data = data;   // Asigna el dato recibido al nodo
        this.next = null;   // Inicialmente no apunta a ningún siguiente
        this.prev = null;   // Inicialmente no apunta a ningún anterior
    }
}
