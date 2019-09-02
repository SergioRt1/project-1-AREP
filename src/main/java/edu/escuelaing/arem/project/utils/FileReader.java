package edu.escuelaing.arem.project.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {

    public static byte[] getBytesOfFile(String requestPath) throws IOException {
        Path filePath = getPath(requestPath);

        return Files.readAllBytes(filePath);
    }

    public static String getFileExtension(String filePath) {
        String fileName = getPath(filePath).getFileName().toString();
        StringBuilder extension = new StringBuilder();
        for (int i = fileName.length() -1; i >= 0; i--) {
            char c = fileName.charAt(i);
            if (c == '.') {
                break;
            }
            extension.append(c);
        }

        return extension.reverse().toString();
    }

    private static Path getPath(String file) {
        String absolutePath = Paths.get("").toAbsolutePath().toString();

        return Paths.get(absolutePath, file);
    }
}
