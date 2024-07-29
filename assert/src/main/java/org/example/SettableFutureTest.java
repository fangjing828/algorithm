package org.example;

import com.google.common.util.concurrent.SettableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.*;

public class SettableFutureTest {
    public static void main(String[] args) throws Exception {
        set_thenGetSetResult();
        setException_thenThrowException();
        setBeforeSetException_thenReturnResult();
        setAfterSetException_thenThrowException();
        setBeforeSetNullException_thenReturnResult();
        setAfterSetNullException_thenThrowNPE();
        setNullBeforeSetException_thenReturnNull();
        setNullAfterSetException_thenThrowException();
        timeout_thenThrowTimeoutException();
    }

    static void set_thenGetSetResult() throws Exception {
        SettableFuture<Long> executeResult = SettableFuture.create();
        Long result = System.currentTimeMillis();
        executeResult.set(result);
        assertSame(result, executeResult.get());
    }

    static void setException_thenThrowException() {
        SettableFuture<Long> executeResult = SettableFuture.create();
        executeResult.setException(new IllegalStateException());
        assertThrows(ExecutionException.class, executeResult::get);
    }

    static void setBeforeSetException_thenReturnResult() throws Exception {
        SettableFuture<Long> executeResult = SettableFuture.create();
        Long result = System.currentTimeMillis();
        executeResult.set(result);
        executeResult.setException(new IllegalStateException());
        assertSame(result, executeResult.get());
    }

    static void setAfterSetException_thenThrowException() {
        SettableFuture<Long> executeResult = SettableFuture.create();
        Long result = System.currentTimeMillis();
        executeResult.setException(new IllegalStateException());
        executeResult.set(result);
        assertThrows(ExecutionException.class, executeResult::get);
    }

    static void setNullBeforeSetException_thenReturnNull() throws Exception {
        SettableFuture<Long> executeResult = SettableFuture.create();
        executeResult.set(null);
        executeResult.setException(new IllegalStateException());
        assertNull(executeResult.get());
    }

    static void setNullAfterSetException_thenThrowException() {
        SettableFuture<Long> executeResult = SettableFuture.create();
        executeResult.setException(new IllegalStateException());
        executeResult.set(null);
        assertThrows(ExecutionException.class, executeResult::get);
    }
    static void setAfterSetNullException_thenThrowNPE() {
        SettableFuture<Long> executeResult = SettableFuture.create();
        assertThrows(NullPointerException.class, () -> executeResult.setException(null));
    }

    static void setBeforeSetNullException_thenReturnResult() throws Exception {
        SettableFuture<Long> executeResult = SettableFuture.create();
        long result = System.currentTimeMillis();
        executeResult.set(result);
        assertThrows(NullPointerException.class, () -> executeResult.setException(null));
        assertEquals(result, executeResult.get().longValue());
    }

    static void timeout_thenThrowTimeoutException() {
        SettableFuture<Long> executeResult = SettableFuture.create();
        assertThrows(TimeoutException.class, () -> executeResult.get(1, TimeUnit.MILLISECONDS));
    }

}
