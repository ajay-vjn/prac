package com.learn.ThreadEx;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ajay on 02-Apr-16.
 */
public class ThreadStatus {

    static Thread t1;
    static Thread t2;
    static SharedResource sr = new SharedResource();

    public static void main(String[] args) throws Exception{
        t1 = new Thread(new RunnableThread1());
        t2 = new Thread(new RunnableThread2());

        Timer t = new Timer(true);
        t.scheduleAtFixedRate(new MyTimerTask(t1), 0, 1000);

        t2.start();
        Thread.sleep(2*1000);
        t1.start();
        Thread.sleep(8*1000);
    }

    static class MyTimerTask extends TimerTask{

        Thread t;

        MyTimerTask(Thread t){
            this.t = t;
        }

        @Override
        public void run() {
            System.out.println("Called - "+ new Date() + ", Thread state = "+ t.getState());
        }
    }

    static class RunnableThread1 implements Runnable{

        public void run() {
            sr.m1();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class RunnableThread2 implements Runnable{

        public void run() {
            sr.m1();
        }
    }

    static class SharedResource {
        public synchronized void m1(){
            long time = System.currentTimeMillis() + 5 * 1000;
            while(System.currentTimeMillis() < time){

            }
        }
    }
}
