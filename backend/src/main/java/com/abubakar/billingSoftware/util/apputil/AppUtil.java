package com.abubakar.billingSoftware.util.apputil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AppUtil {

    public static String get_photo_upload_path(String filename) throws IOException {
        // Define path: backend/uploads
        Path uploadDir = Paths.get("backend", "uploads");

        // Create directory if it does not exist
        Files.createDirectories(uploadDir);

        // Resolve full file path
        return uploadDir.resolve(filename).toAbsolutePath().toString();
    }
}
