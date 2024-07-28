package com.fabribraguev;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

class Service implements Runnable {
    int i;
    public Service(int i){
        this.i=i;
    }

    @Override
    public void run() {
        System.out.println("In thread: "+i);
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
Future--stores data received by submit
submit(new Runnable()) [*] returns null
submit(new Callable())
invokeAny()
invokeAll()
 */


public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        submitSample();

        //executeSample();
    }
    public static void submitSample() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        System.out.println(new Date());
        List<Future<String>> futureList = new ArrayList<>();
        for(int i=0;i<25;i++){
            futureList.add((Future<String>) executorService.submit(new Service(i)));
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS); //wait till here
        System.out.println("======================");
        for(Future<String> fut:futureList)
            System.out.println(fut.get());
        System.out.println(new Date());

    }
    public static void executeSample(){
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        System.out.println(new Date());
        for(int i=0;i<25;i++){
            executorService.execute(new Service(i));
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS); //wait till here
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(new Date());
    }
}

