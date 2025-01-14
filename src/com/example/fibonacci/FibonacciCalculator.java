package com.example.fibonacci;

import java.util.concurrent.*;

public class FibonacciCalculator {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Define the number of threads in the thread pool
        int numThreads = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        // Define the numbers for which Fibonacci should be computed
        int[] numbers = {10, 15, 20, 25, 30};

        // Submit tasks to compute Fibonacci numbers concurrently
        for (int number : numbers) {
            Future<Long> future = executorService.submit(new FibonacciTask(number));
            System.out.printf("Fibonacci of %d is %d (computed by %s)%n",
                    number, future.get(), Thread.currentThread().getName());
        }

        // Shut down the executor service
        executorService.shutdown();
    }
}

// Define a Callable task to compute Fibonacci
class FibonacciTask implements Callable<Long> {
    private final int number;

    public FibonacciTask(int number) {
        this.number = number;
    }

    @Override
    public Long call() {
        return computeFibonacci(number);
    }

    // Recursive method to compute Fibonacci
    private long computeFibonacci(int n) {
        if (n <= 1) return n;
        return computeFibonacci(n - 1) + computeFibonacci(n - 2);
    }
}
