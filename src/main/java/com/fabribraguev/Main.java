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

class Service2 implements Callable<String> {
    int i;
    public Service2(int i){
        this.i=i;
    }

    @Override
    public String call() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("In thread: "+i);
        return "From thread: "+i;

    }
}

/*
ExecutorService:

execute() [*]
shutDown() [*]
awaitTermination() [*]
Future--stores data received by submit
submit(new Runnable()) [*] returns null
submit(new Callable()) [*] returns data
invokeAny() returns only single data from  list of callable object
invokeAll() returns list of future objects
 */


public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        invokeAllSample();
        //invokeAnySample();
        //submitCallableSample();
        //submitRunnableSample();
        //executeSample();
    }
    public static void invokeAllSample() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        System.out.println(new Date());
        List<Callable<String>> callableList = new ArrayList<>();
        callableList.add(new Service2(1));
        callableList.add(new Service2(2));
        callableList.add(new Service2(3));
        callableList.add(new Service2(4));
        callableList.add(new Service2(5));
        List<Future<String>> futureList= executorService.invokeAll(callableList);//it returns only one future data
//it can be any from the list of data from callable
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS); //wait till here
        System.out.println("======================");
        for(Future<String> fut:futureList)
            System.out.println(fut.get());
        System.out.println(new Date());
    }
    public static void invokeAnySample() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        System.out.println(new Date());
        List<Callable<String>> futureList = new ArrayList<>();
        futureList.add(new Service2(1));
        futureList.add(new Service2(2));
        futureList.add(new Service2(3));
        futureList.add(new Service2(4));
        futureList.add(new Service2(5));
        String str = executorService.invokeAny(futureList);//it returns only one future data
        //it can be any from the list of data from callable
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS); //wait till here
        System.out.println("======================");
        System.out.println(str);
        System.out.println(new Date());
    }
    public static void submitCallableSample() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        System.out.println(new Date());
        List<Future<String>> futureList = new ArrayList<>();
        for(int i=0;i<10;i++){
            futureList.add(executorService.submit(new Service2(i)));
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS); //wait till here
        System.out.println("======================");
        for(Future<String> fut:futureList)
            System.out.println(fut.get());
        System.out.println(new Date());
    }
    public static void submitRunnableSample() throws InterruptedException, ExecutionException {
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

