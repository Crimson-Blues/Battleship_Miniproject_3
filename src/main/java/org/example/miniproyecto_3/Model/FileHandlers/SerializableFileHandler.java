package org.example.miniproyecto_3.Model.FileHandlers;

import java.io.*;

public class SerializableFileHandler implements ISerializableFileHandler {

    @Override
    public void serialize(String fileName, Object obj) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(obj);

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object deserialize(String fileName) {
        try (FileInputStream fis = new FileInputStream(fileName)) {
            ObjectInputStream in = new ObjectInputStream(fis);

            return (Object) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
