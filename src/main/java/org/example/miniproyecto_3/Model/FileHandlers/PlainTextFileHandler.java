package org.example.miniproyecto_3.Model.FileHandlers;

import java.io.*;

public class PlainTextFileHandler implements  IPlaneTextFileReader {

    @Override
    public void writeToFile(String fileName, String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

    @Override
    public String[] readFromFile(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while((line = reader.readLine()) != null){
            stringBuilder.append(line.trim()).append(",");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return stringBuilder.toString().split(",");
    }
}
