package com.fjhdream.HelloThreads;

public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello" );
        System.out.println(Thread.currentThread().getName());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable:" + Thread.currentThread().getName());
            }
        };
        runnable.run();
    }
}
