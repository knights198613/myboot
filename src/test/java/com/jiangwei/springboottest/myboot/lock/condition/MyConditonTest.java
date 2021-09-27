package com.jiangwei.springboottest.myboot.lock.condition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: weijiang
 * @date: 2021/9/16
 * @description: 锁条件测试
 **/
public class MyConditonTest {

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    private volatile boolean isRun = true;

    public static void main(String[] args) {
        MyConditonTest myConditonTest = new MyConditonTest();
        myConditonTest.doStart();



    }

    public void doStart() {
        Thread producer = new Thread(new MyConditonTest.Producer(), "producer-1");
        producer.start();
        Thread consumer = new Thread(new MyConditonTest.Consumer(), "consumer-1");
        consumer.start();
        try {
            TimeUnit.SECONDS.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isRun = false;
    }

    /**
     * 生产者
     */
    class Producer implements Runnable {

        @Override
        public void run() {
            while(isRun) {
                doProduce();
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 生产方法
     */
    private void doProduce() {
        lock.lock();
        System.out.println("ThreadName:" + Thread.currentThread().getName() + ", 我拿到了锁，开始生产，发出生产的信号量");
        condition.signalAll();
        System.out.println("ThreadName:" + Thread.currentThread().getName() + ", 我生产完了，信号量发出了，开始释放锁");
        lock.unlock();
    }



    /**
     * 消费者
     */
    class Consumer implements Runnable {
        @Override
        public void run() {
            while (isRun)  {
                doConsume();
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 消费方法
     */
    private void doConsume() {
        lock.lock();
        try {
            System.out.println("ThreadName:"+Thread.currentThread().getName()+", 我拿到了锁，开始消费, 消费完成进入等待");
            condition.await();
            System.out.println("ThreadName:"+Thread.currentThread().getName()+", 我被唤醒，进入消费状态，开始释放锁");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}
