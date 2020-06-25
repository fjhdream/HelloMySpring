package com.fjhdream.HelloThreads.HelloABC;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class TestABCWithPark {
    static Thread threadA = null, threadB = null, threadC = null;
    public static void main(String[] args) {
        threadA = new Thread(()->{
            try {
                for (int i = 0;i<10;i++){
                    LockSupport.park();
                    System.out.println("A");
                    LockSupport.unpark(threadB);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                LockSupport.unpark(threadB);
            }
        },"thread A");
        threadB =  new Thread(()->{
            try {
                for (int i = 0;i<10;i++){
                    LockSupport.park();
                    System.out.println("B");
                    LockSupport.unpark(threadC);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                LockSupport.unpark(threadC);
            }
        },"thread B");

       threadC =  new Thread(()->{
            try {
                for (int i = 0;i<10;i++){

                    LockSupport.park();
                    System.out.println("C");
                    LockSupport.unpark(threadA);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                LockSupport.unpark(threadA);
            }
        },"thread C");



        threadA.start();
        threadC.start();
        threadB.start();
        LockSupport.unpark(threadA);

    }
}
