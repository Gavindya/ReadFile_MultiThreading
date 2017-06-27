package com.company;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yasith on 6/22/17.
 */
public class CalculationThreadPool {
    private ExecutorService countservice;

    public CalculationThreadPool(int threadCount) {
        this.countservice = Executors.newFixedThreadPool(threadCount);
    }

    public ExecutorService getCountservice() {
        return countservice;
    }

    public void addWork(Runnable runnable){
        countservice.submit(runnable);
    }

    public void shutdown(){
        countservice.shutdown();
    }
}
