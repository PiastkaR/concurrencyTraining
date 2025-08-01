package org.multihread.conditionVariables;

public class SimpleCountDownLatch {
    private int count;

    public SimpleCountDownLatch(int count) {
        this.count = count;
        if (count < 0) {
            throw new IllegalArgumentException("count cannot be negative");
        }
    }

    public void await() throws InterruptedException {
        if (this.count == 0) {
            return;
        }
        synchronized (this) {
            while (this.count > 0) {
                this.wait();
            }
        }
    }

    public void countDown() {
        synchronized (this) {
            if (this.count > 0) {
                this.count--;
                if (this.count == 0) {
                    this.notifyAll();
                }
            }
        }
    }

    public int getCount() {

        return this.count;
    }
}
