package com.company;

import javafx.concurrent.Task;
import sun.nio.ch.ThreadPool;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;

/**
 * Created by yasith on 6/22/17.
 */
public class IO {

    public void readFile(String filepath, int numOfWords, CalculationThreadPool threadPool) throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        String[] words = new String[numOfWords];
        int currentIndex = 0;
        int currentChar;
        StringWriter writer = new StringWriter();

        // terminate when eof reached
        while((currentChar = reader.read()) != -1) {
            if (currentChar != ' ' && currentChar != ',' && currentChar != '.' && currentChar != ';'
                    && currentChar != ':' && currentChar != '-') {
                        writer.append((char)currentChar);
            } else {
                if(!writer.toString().trim().isEmpty()) {
                    words[currentIndex] = writer.toString().trim();
                    currentIndex++;
                    writer = new StringWriter(); //always clear writer once added to words string array

                    if(currentIndex == numOfWords){
                        // Send array to thread pool to count n update hashMap
                        threadPool.addWork(new Counter(words));

                        words = new String[numOfWords];
                        currentIndex = 0;
                    }
                }
            }
        }
        // Send remaining words to thread pool as well
        if(!writer.toString().trim().isEmpty()) {
            words[currentIndex] = writer.toString().trim();
            currentIndex++;
        }
        if(currentIndex > 0){
            threadPool.addWork(new Counter(Arrays.copyOfRange(words, 0, currentIndex)));
        }

        System.out.println("File Read Completed");
        threadPool.shutdown();
        while(!threadPool.getCountservice().isTerminated()){}
    }

    public void writeFileTest(String filepath) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(filepath),8192);
        String content;
        do {
            content = reader.readLine();
        } while (content == null);

        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));
        long i = 0;
        while(i < 1000000){
            i++;
            writer.append(content + "\n");
        }
        writer.flush();
    }
}
