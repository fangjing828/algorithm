package org.example;

public class FixedDelayTimer {
    public static void main(String[] args) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                // do something
            } catch (Exception e) {
                // error
            } finally {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
