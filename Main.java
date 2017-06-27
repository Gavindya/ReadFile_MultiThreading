package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

public class Main {
    private static String inputFilePath = "src/test.txt";
    public static ConcurrentMap<String, Long> result = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception {
	// write your code here
        IO io = new IO();
        CalculationThreadPool threadPool = new CalculationThreadPool(4);
//        io.writeFileTest(inputFilePath);
        Long start = System.currentTimeMillis();
        io.readFile(inputFilePath, 1024, threadPool);
        Long end = System.currentTimeMillis();
        printAll();
        System.out.println((end - start)/1000);
    }

    public static void printAll(){
        for(Map.Entry<String,Long> entry : result.entrySet()){
            System.out.print(entry.getKey()+":"+entry.getValue() + ", ");
        }
        System.out.println("");
    }
}
