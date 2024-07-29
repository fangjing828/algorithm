package org.example.async;

import com.google.common.util.concurrent.SettableFuture;


import java.util.concurrent.TimeUnit;

public class Sync {

    public static void doSomething(SettableFuture<Long> future) throws InterruptedException {
        Constants.semaphore.acquire();
        Constants.executor.execute(() ->{
            try {
                TimeUnit.MILLISECONDS.sleep(Constants.cost);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            try {
                future.set(System.currentTimeMillis());
            } finally {
                Constants.semaphore.release();
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.nanoTime();
        for (int i = 0; i < Constants.count; i++) {
            doSomething(SettableFuture.create());
        }
        Constants.semaphore.acquire(Constants.semaphore_permits);
        System.out.println(Sync.class.getSimpleName() + " " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start));
        Constants.executor.shutdown();
    }
}
