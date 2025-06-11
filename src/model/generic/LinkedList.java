/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.generic;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author AlexVB
 */
public class LinkedList<T> implements Iterable<T> {
    public Node<T> head;
    public Node<T> tail;
    private int counter;

    public LinkedList() {
        head = null;
        tail = null;
        counter = 0;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);

        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        counter++;
    }

    public void removeAll() {
        head = null;
        tail = null;
        counter = 0;
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
    
    public void fromArray(T[] array) {
        if (head == null) {
            for (T item : array) {
                add(item);
            }
        } else {
            System.out.println("The Linked List is not empty");
        }
    }
    
    @Override
    public Iterator<T> iterator () {
        return new LinkedListIterator<>(head);
    }

    private static class LinkedListIterator<T> implements Iterator<T> {
        private Node<T> current;
        
        public LinkedListIterator(Node<T> start) {
            current = start;
        }
        
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (current == null) throw new NoSuchElementException();
            T data = current.data;
            current = current.next;
            return data;
        }
    }

}

