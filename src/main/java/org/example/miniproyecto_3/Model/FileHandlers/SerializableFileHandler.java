package org.example.miniproyecto_3.Model.FileHandlers;

import java.io.*;

/**
 * The {@code SerializableFileHandler} class provides functionality to serialize
 * and deserialize Java objects to and from files using the {@link Serializable} interface.
 * <p>
 * It implements the {@link ISerializableFileHandler} interface and uses Java's built-in
 * object I/O streams to handle the file operations.
 * </p>
 */
public class SerializableFileHandler implements ISerializableFileHandler {

    /**
     * Serializes the given object and writes it to the specified file.
     *
     * @param fileName the name (or path) of the file to which the object will be written
     * @param obj the object to serialize; must implement {@link Serializable}
     */
    @Override
    public void serialize(String fileName, Object obj) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(obj);

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes an object from the specified file.
     *
     * @param fileName the name (or path) of the file to read the object from
     * @return the deserialized object
     * @throws IOException if an I/O error occurs during reading
     * @throws ClassNotFoundException if the class of the serialized object cannot be found
     */
    @Override
    public Object deserialize(String fileName) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(fileName)) {
            ObjectInputStream in = new ObjectInputStream(fis);

            return (Object) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IOException();
        }
    }
}
