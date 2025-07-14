package com.solvd.buildingcompany.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WordCounter {
    private static final Logger LOGGER = LogManager.getLogger(WordCounter.class);

    public static void countWordsInFile(String inputFilePath, String outputFilePath) {
        System.out.println("===== Word Counter Tool =====");
        System.out.println("Input file: " + inputFilePath);
        System.out.println("Output file: " + outputFilePath);

        // Verify input file exists
        File inputFile = new File(inputFilePath);
        if (!inputFile.exists()) {
            System.err.println("ERROR: Input file does not exist: " + inputFilePath);
            LOGGER.error("Input file does not exist: {}", inputFilePath);
            return;
        }

        System.out.println("\nFile exists. Ready to search.");

        try {
            // Ask user for word to search
            System.out.println("\n*** PLEASE ENTER A WORD OR PHRASE TO SEARCH BELOW ***");
            System.out.print("Enter word or phrase to search in the file: ");
            System.out.flush(); // Ensure prompt is displayed

            Scanner scanner = new Scanner(System.in);
            String searchWord = scanner.nextLine().trim();

            if (searchWord.isEmpty()) {
                LOGGER.error("Search word cannot be empty");
                return;
            }

            LOGGER.info("Searching for: '{}'", searchWord);

            // Read file content
            String content = FileUtils.readFileToString(inputFile, StandardCharsets.UTF_8);

            // Convert to lowercase for case-insensitive search
            String lowerCaseContent = content.toLowerCase();

            // Count occurrences of the search word
            Map<String, Integer> wordCounts = new HashMap<>();
            int count = StringUtils.countMatches(lowerCaseContent, searchWord.toLowerCase());
            wordCounts.put(searchWord, count);

            LOGGER.info("Found {} occurrences of '{}'", count, searchWord);

            // Format results
            StringBuilder resultBuilder = new StringBuilder();
            resultBuilder.append("Word count results from ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append(":\n");
            resultBuilder.append(String.format("File analyzed: %s\n", inputFilePath));

            wordCounts.forEach((word, occurrences) -> {
                resultBuilder.append(String.format("'%s': %d occurrences\n", word, occurrences));
            });

            resultBuilder.append("\n");

            // Write results to output file (append to existing content)
            File outputFile = new File(outputFilePath);
            String existingContent = "";

            if (outputFile.exists()) {
                existingContent = FileUtils.readFileToString(outputFile, StandardCharsets.UTF_8);
            }

            FileUtils.writeStringToFile(outputFile, resultBuilder.toString() + existingContent, StandardCharsets.UTF_8);

            System.out.println("\n========================================================");
            System.out.println("SEARCH COMPLETED!");
            System.out.println("Found " + count + " occurrences of '" + searchWord + "'");
            System.out.println("Results saved to file: " + outputFilePath);
            System.out.println("========================================================");

            LOGGER.info("Word counting completed. Results saved to file: {}", outputFilePath);

        } catch (IOException e) {
            LOGGER.error("Error processing file: {}", e.getMessage(), e);
        }
    }
}
