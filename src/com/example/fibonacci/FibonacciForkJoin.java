package com.example.fibonacci;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FibonacciForkJoin extends RecursiveTask<Integer> {
    private final int n;

    public FibonacciForkJoin(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 1) {
            return n;
        }

        // Split the task
        FibonacciForkJoin fib1 = new FibonacciForkJoin(n - 1);
        FibonacciForkJoin fib2 = new FibonacciForkJoin(n - 2);

        // Execute the first subtask asynchronously
        fib1.fork();

        // Compute the second subtask synchronously
        int result2 = fib2.compute();

        // Wait for the first subtask and combine the results
        int result1 = fib1.join();

        return result1 + result2;
    }

    public static void main(String[] args) {
        int number = 10; // Calculate the 10th Fibonacci number

        // Create a ForkJoinPool
        ForkJoinPool pool = new ForkJoinPool();

        // Start the computation
        FibonacciForkJoin task = new FibonacciForkJoin(number);
        int result = pool.invoke(task);

        System.out.println("Fibonacci number at position " + number + " is " + result);
    }
}
