package com.learn.ThreadEx;

/**
 * Created by Ajay on 04-Apr-16.
 */
public class ThreadDeadLock2 {

    static Object obj1 = new Object();
    static Object obj2 = new Object();

    static Thread1 t1;
    static Thread2 t2;

    public static void main(String[] args) throws Exception{
        t1 = new Thread1();
        t2 = new Thread2();

        t1.start();
        t2.start();

        Thread.sleep(10*1000);

    }

    static class Thread1 extends Thread{

        @Override
        public void run() {
            synchronized (obj1){
                System.out.println("Lock on obj1 by "+ this.getName());
                doWork();
                synchronized(obj2){
                    System.out.println("Lock on obj2 by "+ this.getName());
                    doWork();
                }
                System.out.println("Releasing lock on obj2 by "+ this.getName());
            }
            System.out.println("Releasing lock on obj1 by "+ this.getName());
        }
    }

    static class Thread2 extends Thread{


        @Override
        public void run() {
            doWork();
            //In this case there would be no deadlock as the second thread is invoked after first thread
            // release the lock.
            //doWork();
            synchronized (obj2){
                System.out.println("Lock on obj2 by "+ this.getName());
                doWork();
                synchronized(obj1){
                    System.out.println("Lock on obj1 by "+ this.getName());
                    doWork();
                    System.out.println("Releasing lock on obj1 by "+ this.getName());
                }
            }
            System.out.println("Releasing lock on obj2 by "+ this.getName());
        }
    }

    public static void doWork(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
