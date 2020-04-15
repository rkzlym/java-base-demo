package com.demo.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程之间按顺序调用，实现 A -> B -> C
 * 三个线程启动，要求如下：
 * A打印5次，B打印10次，C打印15次
 * 接着
 * A打印5次，B打印10次，C打印15次
 * 来10轮
 */
public class ConditionDemo {
    private static class ShareData{
        private int num = 1;    // A 1, B 2, C 3
        private Lock lock = new ReentrantLock();
        private Condition c1 = lock.newCondition();
        private Condition c2 = lock.newCondition();
        private Condition c3 = lock.newCondition();

        public void print(int flag, int loopCount){
            lock.lock();
            try {
                while(num != flag){
                    switch (flag){
                        case 1:
                            c1.await();break;
                        case 2:
                            c2.await();break;
                        case 3:
                            c3.await();break;
                    }
                }
                for(int i = 1; i <= loopCount; i++){
                    System.out.println(Thread.currentThread().getName() + "\t" + i);
                }
                num = flag + 1;
                if (num == 4)
                    num = 1;
                switch (flag){
                    case 1:
                        c2.signal();break;
                    case 2:
                        c3.signal();break;
                    case 3:
                        c1.signal();break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void print5(){
            lock.lock();
            try {
                if (num != 1){
                    c1.await();
                }
                for (int i = 1; i <= 5; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t" + i);
                }
                num = 2;
                c2.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void print10(){
            lock.lock();
            try {
                if (num != 2){
                    c2.await();
                }
                for (int i = 1; i <= 10; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t" + i);
                }
                num = 3;
                c3.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void print15(){
            lock.lock();
            try {
                if (num != 3){
                    c3.await();
                }
                for (int i = 0; i <= 15; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t" + i);
                }
                num = 1;
                c1.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ShareData shareData = new ShareData();
//        new Thread(() -> {
//            for(int i = 1; i <= 10; i++){
//                shareData.print5();
//            }
//        }, "A").start();
//        new Thread(() -> {
//            for(int i = 1; i <= 10; i++){
//                shareData.print10();
//            }
//        }, "B").start();
//        new Thread(() -> {
//            for(int i = 1; i <= 10; i++){
//                shareData.print15();
//            }
//        }, "C").start();

        new Thread(() -> {
            for(int i = 1; i <= 10; i++){
                shareData.print(1, 5);
            }
        }, "A").start();
        new Thread(() -> {
            for(int i = 1; i <= 10; i++){
                shareData.print(2, 10);
            }
        }, "B").start();
        new Thread(() -> {
            for(int i = 1; i <= 10; i++){
                shareData.print(3, 15);
            }
        }, "C").start();

    }
}
