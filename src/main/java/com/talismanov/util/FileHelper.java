package com.talismanov.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileHelper {
    public static String getInputToWorkWithFromFile(String fileName) {

        if (fileName == null) {
            throw new IllegalArgumentException("fileName shouldn't be null");
        }

        StringBuilder inputFromFile = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null)
                inputFromFile.append(line).append("\n");
        } catch (FileNotFoundException e) {
            System.err.println("named file does not exist,\n" +
                    " is a directory rather than a regular file,\n" +
                    " or for some other reason cannot be opened for\n" +
                    "reading. Make sure you use / on both Windows and UNIX systems. \n" +
                    "You wrote this path = " + fileName);
            System.exit(2);
        } catch (IOException e) {
            System.err.println("something went wrong with file.\n" +
                    "Make sure you use / on both Windows and UNIX  systems. \n" +
                    "You wrote this path = " + fileName);
            System.exit(3);
        }

        return inputFromFile.toString();
    }
}
