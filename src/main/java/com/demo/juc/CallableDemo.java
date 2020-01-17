package com.demo.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask(new MyThread());
        new Thread(futureTask, "A").start();
        Integer result = futureTask.get();
        System.out.println("return value: " + result);
    }

    private static class MyThread implements Callable<Integer>{
        @Override
        public Integer call() throws Exception {
            System.out.println("init my thread");
            return 1024;
        }
    }
}