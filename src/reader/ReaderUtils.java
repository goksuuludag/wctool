package reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class ReaderUtils {

    private ReaderUtils() {}

    public static long getByteCount() throws IOException {
        long count = 0;
        int n;
        byte[] buffer = new byte[8192];
        while((n = System.in.read(buffer)) != -1) {
            count += n;
        }
        return count;
    }

    public static int getWordCount(Scanner scanner) throws IOException {
        int count = 0;
        while (scanner.hasNext()) {
            scanner.next();
            count++;
        }
        return count;
    }

    public static int getLineCount(BufferedReader reader) throws IOException {
        int count = 0;
        while (reader.readLine() != null) {
            count++;
        }
        return count;
    }

    public static long getCharacterCount(BufferedReader reader) throws IOException {
        int count = 0;
        while (reader.read() != -1) {
            count++;
        }
        return count;
    }
}