package org.example.async;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.SettableFuture;
import io.netty.util.HashedWheelTimer;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Async {
    private static final HashedWheelTimer timer = new HashedWheelTimer(Executors.defaultThreadFactory(), 1, TimeUnit.MILLISECONDS);
    static {
        timer.start();
    }
    public static  void doSomething(SettableFuture<Long> future) throws InterruptedException {
       Constants.semaphore.acquire();

        SettableFuture<Long> result = SettableFuture.create();
        Futures.addCallback(result, new FutureCallback<>() {
            @Override
            public void onSuccess(Long result) {
                try {
                    future.set(result);
                } finally {
                    Constants.semaphore.release();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                try {
                    future.setException(t);
                } finally {
                    Constants.semaphore.release();
                }
            }
        }, Constants.executor);

        timer.newTimeout(timeout -> result.set(System.currentTimeMillis()), Constants.cost, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.nanoTime();
        for (int i = 0; i < Constants.count; i++) {
            doSomething(SettableFuture.create());
        }
        Constants.semaphore.acquire(Constants.semaphore_permits);
        System.out.println(Async.class.getSimpleName() + " " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start));
        Constants.executor.shutdown();
        timer.stop();
    }
}
