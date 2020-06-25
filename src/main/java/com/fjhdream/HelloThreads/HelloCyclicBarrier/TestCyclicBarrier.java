package com.fjhdream.HelloThreads.HelloCyclicBarrier;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;

public class TestCyclicBarrier {
    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();
        AtomicInteger integer = new AtomicInteger(0);
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();
        ReentrantLock lock = new ReentrantLock();

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, ()-> {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("****" + Thread.currentThread().getName());
            try {
                blockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        while (longAdder.longValue() < 12) {
            longAdder.increment();
            new Thread(()->{
                lock.lock();
                try {
                    if (integer.get() != 0 && integer.get() %3 ==0){

                        blockingQueue.put(".....");
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
