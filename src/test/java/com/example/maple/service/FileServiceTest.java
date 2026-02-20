package com.example.maple.service;

import com.example.maple.model.MindMapData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class FileServiceTest {

    @Autowired
    private FileService fileService;

    @Test
    public void testExportAndImportMindMap() throws Exception {
        // 创建测试数据
        MindMapData mindMapData = new MindMapData();
        List<MindMapData.Node> nodes = new ArrayList<>();

        MindMapData.Node rootNode = new MindMapData.Node();
        rootNode.setId("1");
        rootNode.setText("Root Node");
        nodes.add(rootNode);

        mindMapData.setNodes(nodes);
        List<String> rootIds = new ArrayList<>();
        rootIds.add("1");
        mindMapData.setRootIds(rootIds);

        // 测试导出为字节数组
        byte[] data = fileService.exportMindMapToBytes(mindMapData);
        assertNotNull(data);
        assertTrue(data.length > 0);

        // 测试从字节数组导入
        MindMapData importedData = fileService.importMindMapFromBytes(data);
        assertNotNull(importedData);
        assertNotNull(importedData.getNodes());
        assertTrue(importedData.getNodes().size() > 0);
    }

    @Test
    public void testFileOperations() throws Exception {
        // 创建测试数据
        MindMapData mindMapData = new MindMapData();
        List<MindMapData.Node> nodes = new ArrayList<>();

        MindMapData.Node rootNode = new MindMapData.Node();
        rootNode.setId("1");
        rootNode.setText("Root Node");
        nodes.add(rootNode);

        mindMapData.setNodes(nodes);
        List<String> rootIds = new ArrayList<>();
        rootIds.add("1");
        mindMapData.setRootIds(rootIds);

        // 测试文件导出
        String testFilePath = "test.mindmap";
        fileService.exportMindMap(testFilePath, mindMapData);

        // 验证文件存在
        File testFile = new File(testFilePath);
        assertTrue(testFile.exists());

        // 测试文件导入
        MindMapData importedData = fileService.importMindMap(testFilePath);
        assertNotNull(importedData);

        // 清理测试文件
        testFile.delete();
    }
}