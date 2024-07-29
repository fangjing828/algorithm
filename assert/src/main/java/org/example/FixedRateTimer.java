package org.example;

import java.util.concurrent.TimeUnit;

public class FixedRateTimer {
    public static void main(String[] args) {
        while (!Thread.currentThread().isInterrupted()) {
            long start = System.nanoTime();
            try {
                // do something
            } catch (Exception e) {
                // error
            } finally {
                try {
                    TimeUnit.NANOSECONDS.sleep(TimeUnit.MILLISECONDS.toNanos(1) + start - System.nanoTime());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
