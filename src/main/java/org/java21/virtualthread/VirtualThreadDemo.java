package org.java21.virtualthread;

import java.util.ArrayList;
import java.util.List;

public class VirtualThreadDemo {
    private static int NUMBER_OF_VIRTUAL_THREADS = 100;

    public static void main(String[] args) throws InterruptedException {
//        Thread platformThread = Thread.ofPlatform().unstarted(runnable);//old platform thread
//        Thread virtualthread = Thread.ofVirtual().unstarted(runnable);//new virtual thread
//        virtualthread.start();
//        virtualthread.join();
        List<Thread> virtualThreads = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_VIRTUAL_THREADS; i++) {
            Thread virtualThread = Thread.ofVirtual().unstarted(new BlockingTask());
            virtualThreads.add(virtualThread);
        }
        for (Thread virtualThread : virtualThreads) {
            virtualThread.start();
        }

        for (Thread virtualThread : virtualThreads) {
            virtualThread.join();
        }
    }

    private static class BlockingTask implements Runnable {
        @Override
        public void run() {

           System.out.println("Inside thread: " + Thread.currentThread() + "before blocking call");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            System.out.println("Inside thread: " + Thread.currentThread() + "after blocking call");

        }
    }
}
