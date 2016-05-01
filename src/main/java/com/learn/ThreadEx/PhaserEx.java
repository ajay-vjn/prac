package com.learn.ThreadEx;

import java.util.concurrent.Phaser;

/**
 * Created by Ajay on 04-Apr-16.
 */
public class PhaserEx {
    public static void main(String[] args) {

        Phaser phaser=new Phaser(1);
        System.out.println("new phaser with 1 registered unArrived parties"
                + " created and initial phase  number is 0 ");

        Thread thread1=new Thread(new MyRunnable(phaser,"first"),"Thread-1");
        Thread thread2=new Thread(new MyRunnable(phaser,"second"),"Thread-2");

        Thread thread3=new Thread(new MyRunnable(phaser,"third"),"Thread-3");

        System.out.println("\n--------Phaser has started---------------");
        int currentPhase=phaser.getPhase();

        //Start 3 threads
        thread1.start();
        thread2.start();
        thread3.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        phaser.arriveAndAwaitAdvance();
        System.out.println("------Phase-"+currentPhase+" has been COMPLETED----------");

        currentPhase=phaser.getPhase();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        phaser.arriveAndAwaitAdvance();
        System.out.println("------Phase-"+currentPhase+" has been COMPLETED----------");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        phaser.arriveAndDeregister();

        if(phaser.isTerminated())
            System.out.println("\nPhaser has been terminated");

    }
}





class MyRunnable implements Runnable {

    Phaser phaser;

    MyRunnable(Phaser phaser, String name) {
        this.phaser = phaser;
        this.phaser.register(); //Registers/Add a new unArrived party to this phaser.
        System.out.println(name + " - New unarrived party has "
                + "been registered with phaser");
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() +
                " - party has arrived and is working in "
                + "Phase-" + phaser.getPhase());
        phaser.arriveAndAwaitAdvance();

        //Sleep has been used for formatting output
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //------NEXT PHASE BEGINS------

        System.out.println(Thread.currentThread().getName() +
                " - party has arrived and is working in "
                + "Phase-" + phaser.getPhase());
        phaser.arriveAndAwaitAdvance();

        phaser.arriveAndDeregister();
    }
}
