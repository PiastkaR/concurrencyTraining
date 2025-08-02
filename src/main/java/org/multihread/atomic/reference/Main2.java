package org.multihread.atomic.reference;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class Main2 {
    public static class LockFreeStack<T> {
        private AtomicReference<StackNode<T>> head = new AtomicReference<>();
        private AtomicInteger counter = new AtomicInteger(0);

        public static void main(String[] args) throws InterruptedException {
//        StandardStack<Integer> stack = new StandardStack<>();
            LockFreeStack<Integer> stack = new LockFreeStack<>();
            Random random = new Random();
            for (int i = 0; i < 1000; i++) {
                stack.push(random.nextInt(1000));
            }

            List<Thread> threads = new ArrayList<>();
            int pushingThreads = 2;
            int poppingThreads = 2;
            for (int i = 0; i < pushingThreads; i++) {
                Thread thread = new Thread(() -> {
                    while (true) {
                        stack.push(random.nextInt());
                    }
                });
                thread.setDaemon(true);
                threads.add(thread);
            }
            for (int i = 0; i < poppingThreads; i++) {
                Thread thread = new Thread(() -> {
                    while (true) {
                        stack.pop();
                    }
                });
                thread.setDaemon(true);
                threads.add(thread);
            }

            for (Thread thread : threads) {
                thread.start();
            }
            Thread.sleep(1000);
            System.out.println(String.format("%,d operations were performed", stack.getCounter()));

        }

        public void push(T value) {
            StackNode<T> newHead = new StackNode<>(value);
            while (true) {
                StackNode<T> currentHead = head.get();
                newHead.next = currentHead;
                if (head.compareAndSet(currentHead, newHead)) {
                    break; // Successfully pushed the new head
                } else {
                    LockSupport.parkNanos(1);
                }
            }
            counter.incrementAndGet();
        }

        public T pop() {
            StackNode<T> currentHeadNode = head.get();
            StackNode<T> newHeadNode;

            while (currentHeadNode != null) {

                while (currentHeadNode != null) {
                    newHeadNode = currentHeadNode.next;
                    if (head.compareAndSet(currentHeadNode, newHeadNode)) {
                        break;
                    } else {
                        LockSupport.parkNanos(1);
                        currentHeadNode = head.get();
                    }
                }

            }
            counter.incrementAndGet();
            return currentHeadNode != null ? currentHeadNode.value : null;

        }

        public int getCounter() {
            return counter.get();
        }

        public static class StandardStack<T> {
            private StackNode<T> head;
            private int counter = 0;

            public synchronized void push(T value) {
                StackNode<T> newHead = new StackNode<>(value);
                newHead.next = head;
                head = newHead;
                counter++;
            }

            public synchronized T pop() {
                if (head == null) {
                    return null;
                }
                T value = head.value;
                head = head.next;
                counter++;
                return value;
            }

            public int getCounter() {
                return counter;
            }
        }

        public static class StackNode<T> {
            public T value;
            public StackNode<T> next;

            public StackNode(T value) {
                this.value = value;
                this.next = next;
            }
        }
    }
}
