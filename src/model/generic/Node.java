/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.generic;

/**
 *
 * @author AlexVB
 */
public class Node<T> {
    public T data;
    public Node<T> next;
    public Node<T> prev; // Nuevo

    public Node(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}