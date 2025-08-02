package org.multihread.conditionvariables;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleCountDownLatch {

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    private int count;

    public SimpleCountDownLatch(int count) {
        this.count = count;
        if (count < 0) {
            throw new IllegalArgumentException("count cannot be negative");
        }
    }

    public void await() throws InterruptedException {
        lock.lock();
        try {
            while (this.count > 0) {
                condition.await(); //this.wait();
            }
        } finally {
            lock.unlock();
        }

    }

    public void countDown() {
        lock.lock();
        try{
            if (this.count > 0) {
                this.count--;
                if (this.count == 0) {
                    condition.signalAll();//this.notifyAll()
                }
            }
        }finally {
            lock.unlock();
        }
    }

    public int getCount() {

        return this.count;
    }
}
