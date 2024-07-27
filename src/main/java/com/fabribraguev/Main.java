package com.fabribraguev;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Service implements Runnable {
    int i;
    public Service(int i){
        this.i=i;
    }

    @Override
    public void run() {
        System.out.println(i + " ");
        try {
            Thread.sleep(1000); //1 sec
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/*
ExecutorService:

execute() [*]
shutDown() [*]
awaitTermination() [*]
submit(new Runnable())
submit(new Callable())
invokeAny()
invokeAll()
 */


public class Main {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        System.out.println(new Date());
        for(int i=0;i<25;i++){
            executorService.execute(new Service(i));
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS); //wait till here
        System.out.println(new Date());
    }
}

