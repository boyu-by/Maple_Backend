package com.example.maple.controller;

import com.example.maple.logger.ErrorLogger;
import com.example.maple.model.MindMapData;
import com.example.maple.service.FileService;
import com.example.maple.service.FileSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/mindmap")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private FileSystemService fileSystemService;

    @Autowired
    private ErrorLogger errorLogger;

    @PostMapping("/import")
    public ResponseEntity<MindMapData> importMindMap(@RequestParam("file") MultipartFile file) {
        try {
            MindMapData mindMapData = fileService.importMindMapFromBytes(file.getBytes());
            return ResponseEntity.ok(mindMapData);
        } catch (Exception e) {
            errorLogger.logError("导入思维导图失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/export")
    public ResponseEntity<byte[]> exportMindMap(@RequestBody MindMapData mindMapData) {
        try {
            byte[] data = fileService.exportMindMapToBytes(mindMapData);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=mindmap.mindmap")
                    .header("Content-Type", "application/octet-stream")
                    .body(data);
        } catch (Exception e) {
            errorLogger.logError("导出思维导图失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/files")
    public ResponseEntity<?> listFiles(@RequestParam("path") String path) {
        try {
            return ResponseEntity.ok(fileSystemService.listFiles(path));
        } catch (Exception e) {
            errorLogger.logError("列出文件失败", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "列出文件失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/files/create-directory")
    public ResponseEntity<Void> createDirectory(@RequestParam("path") String path) {
        try {
            fileSystemService.createDirectory(path);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            errorLogger.logError("创建目录失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/files/delete")
    public ResponseEntity<Void> deleteFile(@RequestParam("path") String path) {
        try {
            fileSystemService.deleteFile(path);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            errorLogger.logError("删除文件失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/files/rename")
    public ResponseEntity<Void> renameFile(@RequestParam("oldPath") String oldPath, @RequestParam("newPath") String newPath) {
        try {
            fileSystemService.renameFile(oldPath, newPath);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            errorLogger.logError("重命名文件失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}