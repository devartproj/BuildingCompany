package com.solvd.buildingcompany.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.solvd.buildingcompany.annotations.BuildingOperation;
import com.solvd.buildingcompany.annotations.BuildingOperationInfo;
import com.solvd.buildingcompany.annotations.Priority;
import com.solvd.buildingcompany.services.BuildingOperationService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@BuildingOperation(
    description = "Utility class for counting word occurrences in text files",
    priority = Priority.LOW
)
public class WordCounter {
    private static final Logger LOGGER = LogManager.getLogger(WordCounter.class);

    // Статический блок инициализации для логирования информации об аннотациях при загрузке класса
    static {
        BuildingOperationInfo classInfo = BuildingOperationService.getClassOperationInfo(WordCounter.class);
        if (classInfo != null) {
            LOGGER.info("WordCounter utility initialized - {}", classInfo.getDescription());
            LOGGER.info("Operation priority: {}", classInfo.getPriority());
        }
    }

    @BuildingOperation(
        description = "Counts word frequency in a file and outputs results",
        priority = Priority.MEDIUM,
        estimatedCost = 15.0,
        requiredTools = {"text-analyzer", "file-system-access"}
    )
    public static void countWordsInFile(String inputFilePath, String outputFilePath) {
        // Получаем информацию об аннотации для этого метода
        BuildingOperationInfo methodInfo = BuildingOperationService.getMethodOperationInfo(
                WordCounter.class, "countWordsInFile");

        // Проверяем наличие всех необходимых инструментов
        boolean hasAllTools = checkRequiredTools(methodInfo);
        if (!hasAllTools) {
            LOGGER.error("Cannot proceed - missing required tools");
            System.err.println("ERROR: Cannot proceed due to missing required tools");
            return;
        }

        // Логируем информацию о стоимости операции
        LOGGER.info("Starting word counting operation. Estimated cost: ${}", methodInfo.getEstimatedCost());

        System.out.println("===== Word Counter Tool =====");
        System.out.println("Input file: " + inputFilePath);
        System.out.println("Output file: " + outputFilePath);
        System.out.println("Operation priority: " + methodInfo.getPriority());

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

            // methodInfo уже определена в начале метода, повторно используем её
            // Для операций с высоким приоритетом используем более детальное логирование
            boolean isHighPriority = methodInfo != null && 
                    (methodInfo.getPriority() == Priority.HIGH || 
                     methodInfo.getPriority() == Priority.CRITICAL);

            if (isHighPriority) {
                LOGGER.info("=== HIGH PRIORITY WORD COUNTING OPERATION ===");
                LOGGER.info("Detailed metrics will be collected for this operation");
            }

            LOGGER.info("Searching for: '{}'", searchWord);

            // Read file content
            String content = FileUtils.readFileToString(inputFile, StandardCharsets.UTF_8);

            // Convert to lowercase for case-insensitive search
            String lowerCaseContent = content.toLowerCase();

            // Count occurrences of the search word
            Map<String, Integer> wordCounts = new HashMap<>();

            // Для высокоприоритетных операций измеряем время выполнения
            long startTime = System.currentTimeMillis();

            int count = StringUtils.countMatches(lowerCaseContent, searchWord.toLowerCase());
            wordCounts.put(searchWord, count);

            long endTime = System.currentTimeMillis();

            // Используем ранее определенную переменную isHighPriority
            if (isHighPriority) {
                LOGGER.info("Performance metrics: Word counting completed in {} ms", (endTime - startTime));
                LOGGER.info("Character count in file: {}", content.length());
                LOGGER.info("Words per character ratio: {}", (double) count / content.length());
            }

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

    /**
     * Проверяет наличие всех необходимых инструментов для операции
     * 
     * @param operationInfo информация об операции
     * @return true если все инструменты доступны, иначе false
     */
    private static boolean checkRequiredTools(BuildingOperationInfo operationInfo) {
        if (operationInfo == null) {
            LOGGER.warn("No operation info available");
            return true; // Предполагаем, что операция может выполняться без метаданных
        }

        String[] requiredTools = operationInfo.getRequiredTools();
        if (requiredTools == null || requiredTools.length == 0) {
            return true; // Нет требуемых инструментов
        }

        LOGGER.info("Checking required tools: {}", Arrays.toString(requiredTools));

        // Здесь можно добавить реальную проверку наличия инструментов
        // Например, проверку доступа к файловой системе, наличие библиотек и т.д.
        // Для демонстрации предположим, что все инструменты доступны

        // Проверка доступа к файловой системе
        if (Arrays.asList(requiredTools).contains("file-system-access")) {
            File tempDir = new File(System.getProperty("java.io.tmpdir"));
            if (!tempDir.canWrite()) {
                LOGGER.error("Missing required tool: file-system-access (no write permission)");
                return false;
            }
        }

        // Проверка доступности text-analyzer (StringUtils)
        if (Arrays.asList(requiredTools).contains("text-analyzer")) {
            try {
                // Проверяем, доступен ли StringUtils
                Class.forName("org.apache.commons.lang3.StringUtils");
            } catch (ClassNotFoundException e) {
                LOGGER.error("Missing required tool: text-analyzer (StringUtils not found)");
                return false;
            }
        }

        LOGGER.info("All required tools are available");
        return true;
    }
}
