package com.fjhdream.HelloThreads.HelloABC;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TestABCLiMing {
    static volatile AtomicInteger integer= new AtomicInteger(0);
    public static void main(String[] args) {
        testABCcycle();
    }
    public static  void testABCcycle(){

        ReentrantLock lock = new ReentrantLock();
        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        Condition conditionC = lock.newCondition();
        new Thread(()->{

            try {

                while (true) {

                    if (integer.get() == 0){
                        lock.lock();
                        integer.incrementAndGet();
                        break;
                    }
                }


                for (int i = 0;i<10;i++){

                    conditionA.await();
                    System.out.println("A");
                    conditionB.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        },"thread A").start();

        new Thread(()->{
            try {
                while (true) {
                    if (integer.get() != 0){
                        Thread.sleep(100);
                        lock.lock();
                        break;
                    }
                }

                for (int i = 0;i<10;i++){

                    conditionB.await();
                    System.out.println("B");
                    conditionC.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        },"thread B").start();

        new Thread(()->{
            try {
                while (true) {
                    if (integer.get() != 0){

                        lock.lock();
                        break;
                    }
                }

                for (int i = 0;i<10;i++){

                    conditionA.signal(); //这两行换一下就有问题了
                    conditionC.await();
                    System.out.println("C");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        },"thread C").start();
    }
}
