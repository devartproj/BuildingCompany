package com.solvd.buildingcompany.collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomLinkedList<T> {
    private static final Logger LOGGER = LogManager.getLogger(CustomLinkedList.class);

    private Node<T> head;
    private int size;

    public Node<T> getHead() {
        return head;
    }

    public void setHead(Node<T> head) {
        this.head = head;
    }

    private static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }

    public CustomLinkedList() {
        this.head = null;
        this.size = 0;
        LOGGER.debug("Created new custom linked list");
    }

    // Add an element to the end of the list
    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
        size++;
        LOGGER.debug("Added element to linked list, new size: {}", size);
    }

    // Add an element at specified index
    public void add(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<T> newNode = new Node<>(data);

        if (index == 0) {
            newNode.setNext(head);
            head = newNode;
        } else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            newNode.setNext(current.getNext());
            current.setNext(newNode);
        }
        size++;
        LOGGER.debug("Added element at index {}, new size: {}", index, size);
    }

    // Get element at specified index
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        LOGGER.debug("Retrieved element at index {}", index);
        return current.getData();
    }

    // Remove element at specified index
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        T removedData;
        if (index == 0) {
            removedData = head.getData();
            head = head.getNext();
        } else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            removedData = current.getNext().getData();
            current.setNext(current.getNext().getNext());
        }
        size--;
        LOGGER.debug("Removed element at index {}, new size: {}", index, size);
        return removedData;
    }

    // Remove first occurrence of element
    public boolean remove(T element) {
        if (head == null) {
            return false;
        }

        if (head.getData().equals(element)) {
            head = head.getNext();
            size--;
            LOGGER.debug("Removed element from start of list, new size: {}", size);
            return true;
        }

        Node<T> current = head;
        while (current.getNext() != null && !current.getNext().getData().equals(element)) {
            current = current.getNext();
        }

        if (current.getNext() != null) {
            current.setNext(current.getNext().getNext());
            size--;
            LOGGER.debug("Removed element from list, new size: {}", size);
            return true;
        }

        return false;
    }

    // Get list size
    public int size() {
        return size;
    }

    // Check if list is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Clear the list
    public void clear() {
        head = null;
        size = 0;
        LOGGER.debug("Cleared linked list");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = head;

        while (current != null) {
            sb.append(current.getData());
            if (current.getNext() != null) {
                sb.append(", ");
            }
            current = current.getNext();
        }

        sb.append("]");
        return sb.toString();
    }
}
