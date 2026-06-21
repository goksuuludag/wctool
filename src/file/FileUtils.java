package file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class FileUtils {

    private FileUtils() {}

    public static long getByteCount(String fileName) throws IOException {
        return Files.size(Paths.get(fileName));
    }

    public static int getWordCount(String fileName) throws IOException {
        int wordCount = 0;
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNext()) {
                scanner.next();
                wordCount++;
            }
            return wordCount;
        }
    }

    public static int getLineCount(String fileName) throws IOException {
        int[] lineCount = {0};
        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            lines.forEach((l) -> lineCount[0] = lineCount[0] + 1);
            return lineCount[0];
        }
    }

    public static long getCharacterCount(String fileName) throws IOException {
        int lineSeparatorLength = System.lineSeparator().length();
        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            return lines.mapToLong(line -> line.length() + lineSeparatorLength).sum();
        }
    }
}