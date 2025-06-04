/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.generic;

/**
 *
 * @author AlexVB
 */
public class LinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int counter;

    public LinkedList() {
        head = null;
        tail = null;
        counter = 0;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = tail = newNode;
        } else {
            head = newNode;
            head.next = newNode;
        }
        counter++;
    }

    public boolean removeByData(T data) {
        if (head == null) return false;

        if (head.data.equals(data)) {
            head = head.next;
            counter--;
            return true;
        }

        Node<T> current = head;
        while (current.next != null) {
            if (current.next.data.equals(data)) {
                current.next = current.next.next;
                counter--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void show() {
        Node<T> temp = head;
        while (temp != null) {
            System.out.println(temp.data.toString());
            temp = temp.next;
        }
    }

    public boolean contains(T value) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(value)) return true;
            current = current.next;
        }
        return false;
    }

    public int size() {
        return counter;
    }

    public Node<T> getHead() {
        return head;
    }
}

