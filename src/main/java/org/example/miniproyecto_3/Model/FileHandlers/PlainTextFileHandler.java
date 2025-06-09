package org.example.miniproyecto_3.Model.FileHandlers;

import java.io.*;

/**
 * The {@code PlainTextFileHandler} class provides basic file operations for reading from and writing to plain text files.
 * It implements the {@link IPlaneTextFileReader} interface.
 * <p>
 * When reading, it returns each line as a separate element in a {@code String[]} array.
 * When writing, it overwrites the content of the specified file.
 * </p>
 */
public class PlainTextFileHandler implements  IPlaneTextFileReader {

    /**
     * Writes the given text to a file.
     * If the file already exists, its contents will be overwritten.
     *
     * @param fileName the name (or path) of the file to write to
     * @param text the content to write into the file
     */
    @Override
    public void writeToFile(String fileName, String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the contents of a file and returns it as an array of strings, where each element corresponds to a line in the file.
     *
     * @param fileName the name (or path) of the file to read from
     * @return a {@code String[]} containing each line of the file as a separate element,
     *         or an empty array if the file is empty or cannot be read
     */
    @Override
    public String[] readFromFile(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while((line = reader.readLine()) != null){
            stringBuilder.append(line.trim()).append("\n");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return stringBuilder.toString().split("\\R");
    }
}
