package com.example.maple.logger;

import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ErrorLogger {

    private static final String LOG_FILE = "error.log";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void logError(String message, Throwable throwable) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = LocalDateTime.now().format(FORMATTER);
            writer.write("[" + timestamp + "] ERROR: " + message);
            writer.newLine();
            if (throwable != null) {
                writer.write("Exception: " + throwable.getClass().getName());
                writer.newLine();
                writer.write("Message: " + throwable.getMessage());
                writer.newLine();
                for (StackTraceElement element : throwable.getStackTrace()) {
                    writer.write("\tat " + element);
                    writer.newLine();
                }
            }
            writer.write("----------------------------------------");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logInfo(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = LocalDateTime.now().format(FORMATTER);
            writer.write("[" + timestamp + "] INFO: " + message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logWarning(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = LocalDateTime.now().format(FORMATTER);
            writer.write("[" + timestamp + "] WARNING: " + message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}