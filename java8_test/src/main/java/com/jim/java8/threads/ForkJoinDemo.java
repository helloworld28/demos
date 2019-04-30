package com.jim.java8.threads;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author Jim
 * @date 2019/2/25
 */
public class ForkJoinDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> submit = forkJoinPool.submit(new CountTask(1, 10));
        System.out.println(submit.get());
    }

    static class CountTask extends RecursiveTask<Integer> {

        public static final int THRESHOLD = 2;
        private int start;
        private int end;

        public CountTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            boolean canCompute = end - start > THRESHOLD;
            int sum = 0;
            if (canCompute) {
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
            } else {
                int middle = (end - start) / 2 + start;
                CountTask leftTask = new CountTask(start, middle);
                CountTask rightTask = new CountTask(middle, end);

                leftTask.fork();
                rightTask.fork();

                int leftResult = leftTask.join();
                Integer rightReult = leftTask.join();
                sum = leftResult + rightReult;
            }

            return sum;
        }
    }
}
