/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.generic;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase que implementa una lista doblemente enlazada genérica.
 * @param <T> tipo de datos que almacenará la lista.
 */
public class LinkedList<T> implements Iterable<T> {
    public Node<T> head; // Referencia al primer nodo de la lista
    public Node<T> tail; // Referencia al último nodo de la lista
    private int counter; // Contador de elementos en la lista

    // Constructor: inicializa una lista vacía
    public LinkedList() {
        head = null;
        tail = null;
        counter = 0;
    }

    /**
     * Agrega un nuevo nodo al final de la lista.
     * @param data dato que se quiere almacenar
     */
    public void add(T data) {
        Node<T> newNode = new Node<>(data); // Se crea un nuevo nodo

        if (head == null) {
            // Si la lista está vacía, el nuevo nodo es cabeza y cola
            head = tail = newNode;
        } else {
            // Si la lista ya tiene elementos:
            tail.next = newNode;     // Se enlaza la cola actual al nuevo nodo
            newNode.prev = tail;     // El nuevo nodo apunta hacia atrás a la antigua cola
            tail = newNode;          // El nuevo nodo se convierte en la nueva cola
        }
        counter++; // Se incrementa el contador
    }

    /**
     * Elimina todos los nodos de la lista.
     */
    public void removeAll() {
        head = null;
        tail = null;
        counter = 0;
    }

    /**
     * Elimina un nodo que contenga el valor especificado.
     * @param data valor a eliminar
     * @return true si se eliminó exitosamente, false si no se encontró
     */
    public boolean removeByData(T data) {
        Node<T> current = head;

        while (current != null) {
            if (current.data.equals(data)) {
                if (current == head) {
                    // Si es el primer nodo, se actualiza la cabeza
                    head = current.next;
                    if (head != null) head.prev = null;
                } else {
                    // Se actualiza el anterior del siguiente
                    current.prev.next = current.next;
                }

                if (current == tail) {
                    // Si es el último nodo, se actualiza la cola
                    tail = current.prev;
                    if (tail != null) tail.next = null;
                } else if (current.next != null) {
                    // Se actualiza el siguiente del anterior
                    current.next.prev = current.prev;
                }

                counter--; // Se reduce el contador
                return true;
            }
            current = current.next;
        }

        return false; // Si no se encontró el dato
    }

    /**
     * Muestra todos los elementos de la lista por consola.
     */
    public void show() {
        Node<T> temp = head;
        while (temp != null) {
            System.out.println(temp.data.toString());
            temp = temp.next;
        }
    }

    /**
     * Verifica si un valor está presente en la lista.
     * @param value valor a buscar
     * @return true si se encuentra, false si no
     */
    public boolean contains(T value) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(value)) return true;
            current = current.next;
        }
        return false;
    }

    /**
     * Retorna la cantidad de elementos en la lista.
     */
    public int size() {
        return counter;
    }

    /**
     * Retorna el nodo cabeza (inicio) de la lista.
     */
    public Node<T> getHead() {
        return head;
    }

    /**
     * Carga la lista con los elementos de un arreglo.
     * Solo si la lista está vacía.
     */
    public void fromArray(T[] array) {
        if (head == null) {
            for (T item : array) {
                add(item);
            }
        } else {
            System.out.println("The Linked List is not empty");
        }
    }

    /**
     * Verifica si la lista está vacía.
     */
    public Boolean isEmpty() {
        return head == null;
    }

    /**
     * Retorna un iterador para recorrer la lista hacia adelante.
     */
    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator<>(head);
    }

    /**
     * Clase interna para recorrer la lista de forma ascendente (desde head hasta tail).
     */
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

    /**
     * Clase interna para recorrer la lista de forma descendente (desde tail hasta head).
     */
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

    /**
     * Retorna un iterador descendente desde el final hacia el principio.
     */
    public Iterator<T> descendingIterator() {
        return new DescendingIterator<>(tail);
    }
}
