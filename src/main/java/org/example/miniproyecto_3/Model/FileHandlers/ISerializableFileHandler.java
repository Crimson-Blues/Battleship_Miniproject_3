package org.example.miniproyecto_3.Model.FileHandlers;

import java.io.IOException;

public interface ISerializableFileHandler {
    void serialize(String fileName, Object obj);
    Object deserialize (String fileName) throws IOException;
}
