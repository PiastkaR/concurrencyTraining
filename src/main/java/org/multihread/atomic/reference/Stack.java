package org.multihread.atomic.reference;

public class Stack {
    public static void main(String[] args) {

    }

    public static class StandardStack<T> {
        private StackNode<T> head;
        private int counter = 0;

        public synchronized void push(T value) {
            StackNode<T> newHead = new StackNode<>(value);
            newHead.next = head;
            counter++;
        }

        public synchronized T pop() {
            if (head == null) {
                counter++;
                return null;
            }
            T value = head.value;
            head = head.next;
            counter++;
            return value;
        }
    }

    private static class StackNode<T> {
        public T value;
        public StackNode<T> next;

        public StackNode(T value) {
            this.value = value;
            this.next = next;
        }

    }
}
