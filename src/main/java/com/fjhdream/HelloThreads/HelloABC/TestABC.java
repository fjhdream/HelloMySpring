package com.fjhdream.HelloThreads.HelloABC;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TestABC {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        Condition conditionC = lock.newCondition();

        Thread  threadA = new Thread(()->{
            try {
                lock.lock();
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
        },"thread A");
        Thread  threadB =  new Thread(()->{
            try {
                lock.lock();
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
        },"thread B");

        Thread  threadC =  new Thread(()->{
            try {
                lock.lock();
                for (int i = 0;i<10;i++){

                    conditionA.signal(); //�����л�һ�¾���������
                    conditionC.await();
                    System.out.println("C");

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        },"thread C");

        threadA.start();
        threadB.start();
        threadC.start();
    }
}
