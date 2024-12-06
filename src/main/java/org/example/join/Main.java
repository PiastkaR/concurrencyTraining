package org.example.join;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNos = Arrays.asList(10000000L, 3435L, 35435L, 2324L, 4656L, 23L, 5566L);
        List<FactorialThread> threads = new ArrayList<>();
        for (long inputNumber : inputNos) {
            threads.add(new FactorialThread(inputNumber));
        }
        for (Thread thread : threads) {
            thread.start();
            thread.setDaemon(true);
        }
        for (Thread thread: threads) {
            thread.join(2000);
        }
        for (int i = 0; i < inputNos.size(); i++) {
            FactorialThread factorialThread = threads.get(i);
            if (factorialThread.isFinished()) {
                System.out.println("Factorial of " + inputNos.get(i) + " is " + factorialThread.getResult());
            } else {
                System.out.println("Calculation of " + inputNos.get(i) + " is " + factorialThread.getResult() +"in progress");
            }
        }
    }

    private static class FactorialThread extends Thread {
        private long inputNo;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNo) {
            this.inputNo = inputNo;
        }

        @Override
        public void run() {
            this.result = factorial(inputNo);
            this.isFinished = true;
        }

        public BigInteger factorial(long n) {
            BigInteger temp = BigInteger.ONE;
            for (long i = n; i > 0; i--) {
                temp = temp.multiply(new BigInteger(Long.toString(i)));
            }
            return temp;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public BigInteger getResult() {
            return result;
        }
    }
}
