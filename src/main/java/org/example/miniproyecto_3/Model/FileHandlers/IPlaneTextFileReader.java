package org.example.miniproyecto_3.Model.FileHandlers;

public interface IPlaneTextFileReader {
    void writeToFile(String fileName,  String text);
    String[] readFromFile(String fileName);
}
