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
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail; // Nueva referencia
            tail = newNode;
        }
        counter++;
    }

    public void removeAll() {
        head = null;
        tail = null;
        counter = 0;
    }

    public boolean removeByData(T data) {
        Node<T> current = head;

        while (current != null) {
            if (current.data.equals(data)) {
                if (current == head) {
                    head = current.next;
                    if (head != null) head.prev = null;
                } else {
                    current.prev.next = current.next;
                }

                if (current == tail) {
                    tail = current.prev;
                    if (tail != null) tail.next = null;
                } else if (current.next != null) {
                    current.next.prev = current.prev;
                }

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

    public Boolean isEmpty() {
        return head == null;
    }

    @Override
    public Iterator<T> iterator() {
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
    
    private static class DescendingIterator<T> implements Iterator<T> {
        private Node<T> current;

        public DescendingIterator(Node<T> start) {
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
            current = current.prev;
            return data;
        }
    }

    
    public Iterator<T> descendingIterator() {
        return new DescendingIterator<>(tail);
    }
}