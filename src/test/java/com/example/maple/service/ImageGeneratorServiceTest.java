package com.example.maple.service;

import com.example.maple.model.MindMapData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ImageGeneratorServiceTest {

    @Autowired
    private ImageGeneratorService imageGeneratorService;

    @Test
    public void testGeneratePNG() throws Exception {
        // 创建测试数据
        MindMapData mindMapData = new MindMapData();
        List<MindMapData.Node> nodes = new ArrayList<>();

        MindMapData.Node rootNode = new MindMapData.Node();
        rootNode.setId("1");
        rootNode.setText("Root Node");
        nodes.add(rootNode);

        MindMapData.Node childNode = new MindMapData.Node();
        childNode.setId("2");
        childNode.setText("Child Node");
        childNode.setParentId("1");
        nodes.add(childNode);

        mindMapData.setNodes(nodes);
        List<String> rootIds = new ArrayList<>();
        rootIds.add("1");
        mindMapData.setRootIds(rootIds);

        // 测试生成PNG
        String pngDataUrl = imageGeneratorService.generatePNG(mindMapData);
        assertNotNull(pngDataUrl);
        assertTrue(pngDataUrl.startsWith("data:image/png;base64,"));
    }
}