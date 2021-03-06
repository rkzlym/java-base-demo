package com.demo.thread;

/**
 * 死锁问题演示
 */
public class TestDeadLock {
    public static void main(String[] args) {
        StringBuffer s1 = new StringBuffer();
        StringBuffer s2 = new StringBuffer();

        new Thread(() -> {
            synchronized (s1){
                s1.append("a");
                s2.append("1");
                try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
                synchronized (s2){
                    s1.append("b");
                    s2.append("2");
                    System.out.println(s1);
                    System.out.println(s2);
                }
            }
        },"t1").start();

        new Thread(() -> {
            synchronized (s2){
                s1.append("c");
                s2.append("3");
                try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
                synchronized (s1){
                    s1.append("d");
                    s2.append("4");
                    System.out.println(s1);
                    System.out.println(s2);
                }
            }
        }, "t2").start();
    }
}
