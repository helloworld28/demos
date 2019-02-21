package com.jim.java8.threads;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jim
 * @date 2019/2/16
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {

    private static final int MAX_WORKER_NUMBERS = 10;

    private static final int DEFAULT_WORKER_NUMBERS = 5;

    private static final int MIN_WORKDER_NUMBERS = 1;

    private List<Worker> workers = Collections.synchronizedList(new LinkedList<>());

    private List<Job> jobs = new LinkedList<>();
    private AtomicInteger workerNum = new AtomicInteger(0);


    public DefaultThreadPool() {
        initialWorkers(DEFAULT_WORKER_NUMBERS);
    }

    private void initialWorkers(int num) {
        for (int i = 0; i < num; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            new Thread(worker, "Worker-thread-" + workerNum.incrementAndGet()).start();
        }
    }

    @Override
    public void execute(Job runnable) {
        if (runnable != null) {
            synchronized (jobs) {
                jobs.add(runnable);
                jobs.notify();
            }
        }
    }

    @Override
    public void shutdown() throws InterruptedException {
        synchronized (jobs) {
            workers.forEach(worker -> worker.shutdown());
            workers.clear();
        }

    }

    @Override
    public void addWorkers(int num) {
        if (num + workers.size() <= MAX_WORKER_NUMBERS) {

            initialWorkers(num);
        }
    }

    @Override
    public void removeWorkders(int num) {
        if (workers.size() > num) {
            List<Worker> threads = workers.subList(0, num);
            threads.forEach(worker -> {
                worker.shutdown();
                workers.remove(worker);
            });
        } else {
            throw new IllegalArgumentException("num beyond workers num!");
        }

    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }

    class Worker implements Runnable {
        private boolean running = true;

        @Override
        public void run() {
            while (running && !Thread.currentThread().isInterrupted()) {
                Job job = null;
                synchronized (jobs) {
                    if (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {

                        job = jobs.remove(0);
                    }
                }
                if (job != null) {
                    try {
                        job.run();
                    } catch (Exception e) {

                    }
                }

            }
        }

        void shutdown() {
            running = false;
        }
    }
}
