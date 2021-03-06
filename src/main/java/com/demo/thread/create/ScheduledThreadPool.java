package com.demo.thread.create;

import java.util.Random;
import java.util.concurrent.*;

/**
 * newScheduledThreadPool() 创建固定大小的线程池，可以延迟或定时执行任务
 */
public class ScheduledThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
        ScheduledFuture<Integer> result = pool.schedule(() -> {
            int num = new Random().nextInt(100);
            System.out.println(Thread.currentThread().getName() + "\t" + num);
            return num;
        }, 100, TimeUnit.MILLISECONDS);

        System.out.println(result.get());
        pool.shutdown();
    }
}
