package com.learn.ThreadEx;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class PhaserExample {
    public static void main(String[] args) throws Exception {
        Phaser phaser = new Phaser(3);
        phaser.register();

        ExecutorService manager = Executors.newFixedThreadPool(3);
        manager.submit(new PhaserTask(phaser, 8, 1));
        Thread.sleep(5000);
        manager.submit(new PhaserTask(phaser, 6, 3));
        Thread.sleep(5000);
        manager.submit(new PhaserTask(phaser, 4, 5));
        manager.shutdown();

        phaser.arriveAndDeregister();
    }
}

class PhaserTask implements Runnable {
    Phaser phaser = null;
    private int waitTimeOne;
    private int waitTimeTwo;

    public PhaserTask(Phaser phaser, int waitTimeOne, int waitTimeTwo) {
        this.phaser = phaser;
        this.waitTimeOne = waitTimeOne;
        this.waitTimeTwo = waitTimeTwo;
    }

    public void run() {
        try {
            ThreadUtil.printStr("Barrier: 1 starts, waiting for " + waitTimeOne + " seconds");
            Thread.sleep(1000 * waitTimeOne);
            phaser.arriveAndAwaitAdvance();
            ThreadUtil.printStr("Barrier: 1 ends");

            ThreadUtil.printStr("Barrier: 2 starts, waiting for " + waitTimeTwo + " seconds");
            Thread.sleep(1000 * waitTimeTwo);
            phaser.arriveAndAwaitAdvance();
            ThreadUtil.printStr("Barrier: 2 ends");

            phaser.arriveAndAwaitAdvance();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

