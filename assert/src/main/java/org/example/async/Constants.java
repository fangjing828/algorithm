package org.example.async;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Constants {
    public static final ThreadPoolExecutor executor = new ThreadPoolExecutor(512, 1024,
            60 * 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());


    static {
        executor.prestartAllCoreThreads();
    }


    public static final int semaphore_permits = 10000;

    public static final Semaphore semaphore = new Semaphore(semaphore_permits);

    public static final long cost = 1000;

    public static final long count = 10000;


}
