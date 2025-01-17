package com.skyproton.backend.tools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EnvLoad {
    public static String GetEnv(String key) throws RuntimeException {
        if (key.contains("_FILE")) {
            try {
                return new String(Files.readAllBytes(Paths.get(key)));
            } catch (IOException e) {
                throw new RuntimeException("Failed to read env file, error:", e);
            }
        }
        return System.getenv(key);
    }
}
