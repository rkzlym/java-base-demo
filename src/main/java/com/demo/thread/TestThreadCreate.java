package com.demo.thread;

import org.junit.Test;

/**
 * 多线程的创建: 继承于Thread类的方式
 * 1. 创建一个继承与Thread类的子类
 * 2. 重写Thread类的run方法 -- 将此线程执行的操作声明在run方法中
 * 3. 创建Thread类的子类的对象
 * 4. 通过此对象调用start()
 *
 * 例子: 遍历100以内的所有偶数
 */
public class TestThreadCreate {
    static class MyThread extends Thread{
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                if(i % 2 == 0){
                    System.out.println(i);
                }
            }
        }
    }

    public static void main(String[] args) {
        MyThread t1 = new MyThread();

        t1.start();
    }
}
