package com.example.fibonacci;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FibonacciCalculator {
    public static void main(String[] args) {
        // Define the number of threads in the thread pool
        int numThreads = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        // Define the numbers for which Fibonacci should be computed
        int[] numbers = {10, 15, 20, 25, 30};

        // Submit tasks to compute Fibonacci numbers concurrently
        for (int number : numbers) {
            executorService.submit(new FibonacciTask(number));
        }

        // Shut down the executor service
        executorService.shutdown();
    }
}

// Define a Runnable task to compute Fibonacci
class FibonacciTask implements Runnable {
    private final int number;

    public FibonacciTask(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        long result = computeFibonacci(number);
        System.out.printf("Fibonacci of %d is %d (computed by %s)%n",
                number, result, Thread.currentThread().getName());
    }

    // Recursive method to compute Fibonacci
    private long computeFibonacci(int n) {
        if (n <= 1) return n;
        return computeFibonacci(n - 1) + computeFibonacci(n - 2);
    }
}
