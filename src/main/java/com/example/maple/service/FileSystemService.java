package com.example.maple.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileSystemService {

    public List<FileInfo> listFiles(String directoryPath) throws IOException {
        Path dirPath = Paths.get(directoryPath);
        List<FileInfo> fileInfos = new ArrayList<>();

        if (!Files.exists(dirPath) || !Files.isDirectory(dirPath)) {
            throw new IOException("Directory does not exist: " + directoryPath);
        }

        try (var stream = Files.newDirectoryStream(dirPath)) {
            for (Path path : stream) {
                FileInfo fileInfo = new FileInfo();
                fileInfo.setName(path.getFileName().toString());
                fileInfo.setPath(path.toString());
                fileInfo.setDirectory(Files.isDirectory(path));
                fileInfo.setSize(Files.size(path));
                fileInfo.setLastModified(Files.getLastModifiedTime(path).toMillis());
                fileInfos.add(fileInfo);
            }
        }

        return fileInfos;
    }

    public void createDirectory(String directoryPath) throws IOException {
        Path dirPath = Paths.get(directoryPath);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
    }

    public void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }

    public void renameFile(String oldPath, String newPath) throws IOException {
        Path oldFilePath = Paths.get(oldPath);
        Path newFilePath = Paths.get(newPath);
        if (Files.exists(oldFilePath)) {
            Files.move(oldFilePath, newFilePath);
        }
    }

    public boolean exists(String path) {
        return Files.exists(Paths.get(path));
    }

    public static class FileInfo {
        private String name;
        private String path;
        private boolean isDirectory;
        private long size;
        private long lastModified;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public boolean isDirectory() {
            return isDirectory;
        }

        public void setDirectory(boolean directory) {
            isDirectory = directory;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public long getLastModified() {
            return lastModified;
        }

        public void setLastModified(long lastModified) {
            this.lastModified = lastModified;
        }
    }
}