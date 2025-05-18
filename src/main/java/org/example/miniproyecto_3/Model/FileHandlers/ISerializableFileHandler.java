package org.example.miniproyecto_3.Model.FileHandlers;

public interface ISerializableFileHandler {
    void serialize(String fileName, Object obj);
    Object deserialize(String fileName);
}
