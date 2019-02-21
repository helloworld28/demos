package com.jim.java8.threads;

/**
 * @author Jim
 * @date 2019/2/16
 */
public interface ThreadPool<Job extends Runnable> {

    void execute(Job job);

    void shutdown() throws InterruptedException;

    void addWorkers(int num);

    void removeWorkders(int num);

    int getJobSize();

}
