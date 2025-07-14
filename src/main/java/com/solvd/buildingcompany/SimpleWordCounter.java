package com.solvd.buildingcompany;

import com.solvd.buildingcompany.utils.WordCounter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleWordCounter {
    private static final Logger LOGGER = LogManager.getLogger(SimpleWordCounter.class);

    public static void main(String[] args) {
        System.out.println("========================================================");
        System.out.println("SIMPLE WORD COUNTER");
        System.out.println("========================================================");

        String inputFilePath = "src/main/resources/article.txt";
        String outputFilePath = "src/main/resources/word_count_results.txt";

        System.out.println("\nSearch for a word in text file");
        System.out.println("Input file: " + inputFilePath);
        System.out.println("Output file: " + outputFilePath);

        WordCounter.countWordsInFile(inputFilePath, outputFilePath);

        System.out.println("\nWORD COUNTER APPLICATION COMPLETED");
    }
}