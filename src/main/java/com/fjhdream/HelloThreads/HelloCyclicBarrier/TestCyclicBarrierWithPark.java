package com.fjhdream.HelloThreads.HelloCyclicBarrier;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class TestCyclicBarrierWithPark {
    public static volatile Thread waitPark = null;
    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();
        AtomicInteger integer = new AtomicInteger(0);
        ReentrantLock lock = new ReentrantLock();


        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, ()-> {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("****" + Thread.currentThread().getName());
            LockSupport.unpark(waitPark);


        });

        while (longAdder.longValue() < 12) {
            longAdder.increment();
            new Thread(()->{
                lock.lock();
                try {
                    if (integer.get() != 0 && integer.get() %3 ==0){
                        Thread.sleep(1000);
                        waitPark = Thread.currentThread();
//                        LockSupport.park();
                        LockSupport.parkNanos(1000*1000L);
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
