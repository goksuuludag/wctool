import file.FileUtils;
import reader.ReaderUtils;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Set<String> commands = new HashSet<>();
        String output = null;

        commands.add("-c");
        commands.add("-w");
        commands.add("-l");
        commands.add("-m");

        if (args.length == 1 && commands.contains(args[0])) {
            //no file name, using standard input
            output = getCountResultFromStandardInput(args);
        } else if (args.length != 0){
            //file name or file name with a command
            output = getCountResultFromFileName(args);
        } else {
            printInvalidArgsInfo(args);
        }
        if (output == null) {
            return;
        }
        System.out.println(output);
    }

    private static long getCountResultForCommand(String command, String fileName) throws IOException {
        long count;
        switch (command) {
            case "-c" -> { //byte count
                count = FileUtils.getByteCount(fileName);
            }
            case "-l" -> { //line count
                count = FileUtils.getLineCount(fileName);
            }
            case "-w" -> { //word count
                count = FileUtils.getWordCount(fileName);
            }
            case "-m" -> { //character count
                count = FileUtils.getCharacterCount(fileName);
            }
            default -> { //command is invalid
                count = -1;
            }
        }
        return count;
    }

    private static String getCountResultFromStandardInput(String[] args) {
        long count;
        switch (args[0]) {
            case "-c" -> { //byte count
                try {
                    count = ReaderUtils.getByteCount();
                } catch (IOException e) {
                    count = -1;
                }
            }
            case "-l" -> { //line count
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                    count = ReaderUtils.getLineCount(reader);
                } catch (IOException e) {
                    count = -1;
                }
            }
            case "-w" -> { //word count
                try (Scanner scanner = new Scanner(System.in)) {
                    count = ReaderUtils.getWordCount(scanner);
                } catch (IOException e) {
                    count = -1;
                }
            }
            case "-m" -> { //character count
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                    count = ReaderUtils.getCharacterCount(reader);
                } catch (IOException e) {
                    count = -1;
                }
            }
            default -> { //command is invalid
                count = -1;
            }
        }
        return count == -1 ? null : String.valueOf(count);
    }

    private static String getCountResultFromFileName(String[] args) {
        File file;
        String fileName;
        String output = null;
        if (args[0].startsWith("-")) { //first arg is a command
            String command = args[0];
            file = new File(args[1]);
            fileName = args[1];
            if (file.exists() && !file.isDirectory()) {
                //file name is valid
                try {
                    long count = getCountResultForCommand(command, fileName);
                    if (count == -1L) {
                        // command is invalid
                        printInvalidArgsInfo(args);
                        return null;
                    }
                    output = count + " " + fileName;
                } catch (IOException e) {
                    printErrorWhileReadingFileInfo(fileName);
                    return null;
                }
            }
        } else {
            //first arg is not a command, could be file name
            file = new File(args[0]);
            if (file.exists() && !file.isDirectory()) {
                //file name is valid
                fileName = args[0];
                StringBuilder countStringBuilder = new StringBuilder();
                try {
                    countStringBuilder.append(FileUtils.getLineCount(fileName));
                    countStringBuilder.append(" ");
                    countStringBuilder.append(FileUtils.getWordCount(fileName));
                    countStringBuilder.append(" ");
                    countStringBuilder.append(FileUtils.getByteCount(fileName));
                    output = countStringBuilder.toString();
                } catch (IOException e) {
                    printErrorWhileReadingFileInfo(fileName);
                    return null;
                }
            } else {
                //file name is not valid
                printInvalidFileNameInfo(args[0]);
                return null;
            }
        }
        return output;
    }

    private static void printInvalidArgsInfo(String[] args) {
        System.out.println("Invalid arguments: " + Arrays.toString(args));
    }

    private static void printInvalidFileNameInfo(String fileName) {
        System.out.println("Invalid file name: " + fileName);
    }

    private static void printErrorWhileReadingFileInfo(String fileName) {
        System.out.println("Error while reading file: " + fileName);
    }
}