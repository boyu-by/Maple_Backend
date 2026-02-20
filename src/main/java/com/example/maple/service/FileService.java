package com.example.maple.service;

import com.example.maple.model.MindMapData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class FileService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void exportMindMap(String filePath, MindMapData mindMapData) throws IOException {
        objectMapper.writeValue(new File(filePath), mindMapData);
    }

    public MindMapData importMindMap(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), MindMapData.class);
    }

    public byte[] exportMindMapToBytes(MindMapData mindMapData) throws IOException {
        return objectMapper.writeValueAsBytes(mindMapData);
    }

    public MindMapData importMindMapFromBytes(byte[] data) throws IOException {
        return objectMapper.readValue(data, MindMapData.class);
    }
}