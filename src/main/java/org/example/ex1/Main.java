package org.example.ex1;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("we are in thread: " + Thread.currentThread().getName());
                System.out.println("Current priority is: " + Thread.currentThread().getPriority());
                throw new RuntimeException("Internal Error");
            }
        });
        thread.setName("misbehaving thread");
        thread.setPriority(Thread.MAX_PRIORITY);
        System.out.println("we are in thread: " + Thread.currentThread().getName() + " before a new thread");
        thread.start();
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("A critical error in thread" + thread.getName() + e.getMessage());
            }
        });
        System.out.println("we are in thread: " + Thread.currentThread().getName() + " after a new thread");
        Thread.sleep(1000);
        NewThread newThread = new NewThread();
        newThread.getPriority();
    }
    private static class NewThread extends Thread {
        @Override
        public void run() {
            System.out.println("Hello " + this.getName());
            this.getPriority();
        }
    }
}