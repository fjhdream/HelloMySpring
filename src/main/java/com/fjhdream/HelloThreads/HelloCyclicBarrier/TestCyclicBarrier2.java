package com.fjhdream.HelloThreads.HelloCyclicBarrier;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TestCyclicBarrier2 {
    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();
        AtomicInteger integer = new AtomicInteger(0);
//        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();

        ReentrantLock lock = new ReentrantLock();
        Condition cyclicLock = lock.newCondition();

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, ()-> {
            System.out.println("****" + Thread.currentThread().getName());
//            try {
//                cyclicLock.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        });

        while (longAdder.longValue() < 12) {
            longAdder.increment();
            new Thread(()->{
                lock.lock();
                try {
                    if (integer.get() != 0 && integer.get() %3 ==0){
                        Thread.sleep(100);
                      //  cyclicLock.signal();
                        integer.set(0);
                    }
                    integer.incrementAndGet();
                    System.out.println( Thread.currentThread().getName() +"get the" + integer.get()+"");
                    lock.unlock();
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } finally {

                }
            }).start();
        }



    }
}
