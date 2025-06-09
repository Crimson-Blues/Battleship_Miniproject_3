package org.example.miniproyecto_3.Model.FileHandlers;

import java.io.IOException;

/**
 * The {@code ISerializableFileHandler} interface defines methods for serializing
 * and deserializing objects to and from files.
 * <p>
 * Implementations of this interface should handle the process of saving an object
 * to disk and restoring it later.
 * </p>
 */
public interface ISerializableFileHandler {
    /**
     * Serializes the given object and writes it to a file.
     *
     * @param fileName the name (or path) of the file to which the object will be saved
     * @param obj      the object to serialize; must implement {@link java.io.Serializable}
     */
    void serialize(String fileName, Object obj);
    /**
     * Deserializes an object from the specified file.
     *
     * @param fileName the name (or path) of the file to read from
     * @return the deserialized object
     * @throws IOException if an I/O error occurs during reading
     * @throws ClassNotFoundException if the class of the serialized object cannot be found
     */
    Object deserialize (String fileName) throws IOException, ClassNotFoundException;
}
