package org.example.miniproyecto_3.Model.FileHandlers;

/**
 * Interface for basic plain text file operations.
 */
public interface IPlaneTextFileReader {
    /**
     * Writes a string of text to the specified file.
     *
     * @param fileName the name (or path) of the file to write to
     * @param text the text content to write
     */
    void writeToFile(String fileName,  String text);
    /**
     * Reads the contents of the specified file, line by line.
     *
     * @param fileName the name (or path) of the file to read from
     * @return an array of strings, each representing a line from the file
     */
    String[] readFromFile(String fileName);
}
