package com.example.fibonacci;

import java.util.concurrent.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class FibonacciCalculator {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Define the number of threads in the thread pool
        int numThreads = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        // Shared map to store precomputed Fibonacci results
        Map<Integer, Long> sharedCache = new HashMap<>();
        ReentrantLock lock = new ReentrantLock();

        // Define the numbers for which Fibonacci should be computed
        int[] numbers = {10, 15, 20, 25, 30};

        // Submit tasks
        for (int number : numbers) {
            Future<Long> future = executorService.submit(new FibonacciTask(number, sharedCache, lock));
            System.out.printf("Fibonacci of %d is %d%n", number, future.get());
        }

        // Shut down the executor service
        executorService.shutdown();
    }
}

// Callable task with synchronization
class FibonacciTask implements Callable<Long> {
    private final int number;
    private final Map<Integer, Long> cache;
    private final ReentrantLock lock;

    public FibonacciTask(int number, Map<Integer, Long> cache, ReentrantLock lock) {
        this.number = number;
        this.cache = cache;
        this.lock = lock;
    }

    @Override
    public Long call() {
        lock.lock();
        try {
            // Check cache first
            if (cache.containsKey(number)) {
                return cache.get(number);
            }
        } finally {
            lock.unlock();
        }

        // Compute Fibonacci if not in cache
        long result = computeFibonacci(number);

        // Store result in cache
        lock.lock();
        try {
            cache.put(number, result);
        } finally {
            lock.unlock();
        }
        return result;
    }

    private long computeFibonacci(int n) {
        if (n <= 1) return n;
        return computeFibonacci(n - 1) + computeFibonacci(n - 2);
    }
}
