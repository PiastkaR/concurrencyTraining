package org.multihread.deadlock;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Intersection intersection = new Intersection();
        Thread trainA = new Thread(new TrainA(intersection), "Train A");
        Thread trainB = new Thread(new TrainB(intersection), "Train B");

        trainA.start();
        trainB.start();
    }

    public static class TrainA implements Runnable {
        private Intersection intersection;
        private Random random = new Random();

        public TrainA(Intersection intersection) {
            this.intersection = intersection;
        }

        @Override
        public void run() {
            while (true) {
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                intersection.takeAroad();
            }
        }
    }

    public static class TrainB implements Runnable {
        private Intersection intersection;
        private Random random = new Random();

        public TrainB(Intersection intersection) {
            this.intersection = intersection;
        }

        @Override
        public void run() {
            while (true) {
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                intersection.takeBroad();
            }
        }
    }


    public static class Intersection {
        private Object roadA = new Object();
        private Object roadB = new Object();

        public void takeAroad() {
            synchronized (roadA) {
                System.out.println("Road A is locked by thread " + Thread.currentThread().getName());
                synchronized (roadB) {
                    System.out.println("Train is passing by road A");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }

        public void takeBroad() {
            synchronized (roadA) {
                System.out.println("Road A is locked by thread " + Thread.currentThread().getName());
                synchronized (roadB) {
                    System.out.println("Train is passing by road B");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {

                    }
                }
            }
        }
    }
}
