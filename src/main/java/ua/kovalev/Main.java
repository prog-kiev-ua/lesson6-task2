package ua.kovalev;

import java.math.BigInteger;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        int [] array = generateInts(200_000_000, 1, 100);

        /* --- тест с одним потоком --- */
        int sizePackage = 1;
        Thread[] threads = new Thread[sizePackage];
        Runnable [] runnables = new SumElementArrayRunnable[sizePackage];
        createThreadsAndRunnables(threads, runnables, array);

        long startTime = System.currentTimeMillis();
        startThreads(threads);
        long endTime = System.currentTimeMillis();
        BigInteger sum = sumAll(runnables);
        System.out.println(String.format("кол-во потоков: %d, время расчета: %f c., сумма: %s: ",
                threads.length, (endTime-startTime)/1000D,
                sum.toString()));

        /* --- тест с кол-вом потоков равное кол-ву ядер --- */
        sizePackage = Runtime.getRuntime().availableProcessors();
        threads = new Thread[sizePackage];
        runnables = new SumElementArrayRunnable[sizePackage];
        createThreadsAndRunnables(threads, runnables, array);

        startTime = System.currentTimeMillis();
        startThreads(threads);
        endTime = System.currentTimeMillis();
        sum = sumAll(runnables);
        System.out.println(String.format("кол-во потоков: %d, время расчета: %f c., сумма: %s: ",
                threads.length, (endTime-startTime)/1000D,
                sum.toString()));

        /* --- тест с 10 потоками --- */
        sizePackage = 10;
        threads = new Thread[sizePackage];
        runnables = new SumElementArrayRunnable[sizePackage];
        createThreadsAndRunnables(threads, runnables, array);

        startTime = System.currentTimeMillis();
        startThreads(threads);
        endTime = System.currentTimeMillis();
        sum = sumAll(runnables);
        System.out.println(String.format("кол-во потоков: %d, время расчета: %f c., сумма: %s: ",
                threads.length, (endTime-startTime)/1000D,
                sum.toString()));
    }

    static int[] generateInts(int size, int min, int max) {
        Random random = new Random();
        return random.ints(min, max + 1).limit(size).toArray();
    }

    static void createThreadsAndRunnables(Thread[] threads, Runnable [] runnables, int [] array){
        int sizePackage = array.length / threads.length;

        int curBeginIndex;
        int curEndIndex;
        for (int i = 0; i < threads.length; i++) {
            curBeginIndex = sizePackage * i;
            if (i == (threads.length - 1)) {
                curEndIndex = array.length - 1;
            } else {
                curEndIndex = curBeginIndex + (sizePackage - 1);
            }
            Runnable runnable = new SumElementArrayRunnable(array, curBeginIndex, curEndIndex);
            runnables[i] = runnable;
            threads[i] = new Thread(runnable);
        }
    }

    static BigInteger sumAll(Runnable[] runnables){
        BigInteger sum = BigInteger.ZERO;
        for (Runnable r : runnables) {
            sum = sum.add(((SumElementArrayRunnable)r).getResultSum());
        }
        return sum;
    }

    static void startThreads(Thread [] threads){
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
