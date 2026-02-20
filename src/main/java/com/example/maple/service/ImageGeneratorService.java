package com.example.maple.service;

import com.example.maple.model.MindMapData;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageGeneratorService {

    public String generatePNG(MindMapData mindMapData) throws IOException {
        // 创建一个简单的图像
        int width = 800;
        int height = 600;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        
        // 设置背景
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, width, height);
        
        // 设置字体
        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        g2.setColor(Color.BLACK);
        
        // 绘制思维导图（简单实现）
        if (mindMapData != null && mindMapData.getNodes() != null) {
            Map<String, Point> nodePositions = new HashMap<>();
            int y = 50;
            
            for (MindMapData.Node node : mindMapData.getNodes()) {
                if (node.getParentId() == null) {
                    // 根节点
                    Point rootPoint = new Point(400, y);
                    nodePositions.put(node.getId(), rootPoint);
                    drawNode(g2, node.getText(), rootPoint.x, rootPoint.y);
                    y += 100;
                } else {
                    // 子节点
                    Point parentPoint = nodePositions.get(node.getParentId());
                    if (parentPoint != null) {
                        Point childPoint = new Point(parentPoint.x + 150, y);
                        nodePositions.put(node.getId(), childPoint);
                        // 绘制连接线
                        g2.drawLine(parentPoint.x + 50, parentPoint.y + 15, childPoint.x - 50, childPoint.y + 15);
                        // 绘制节点
                        drawNode(g2, node.getText(), childPoint.x, childPoint.y);
                        y += 60;
                    }
                }
            }
        }
        
        g2.dispose();
        
        // 转换为Base64编码的Data URL
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        baos.flush();
        byte[] imageBytes = baos.toByteArray();
        baos.close();
        
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        return "data:image/png;base64," + base64Image;
    }
    
    private void drawNode(Graphics2D g2, String text, int x, int y) {
        // 绘制节点矩形
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(x - 50, y, 100, 30);
        g2.setColor(Color.BLACK);
        g2.drawRect(x - 50, y, 100, 30);
        
        // 绘制文本
        FontMetrics metrics = g2.getFontMetrics();
        int textWidth = metrics.stringWidth(text);
        int textX = x - textWidth / 2;
        int textY = y + 20;
        g2.drawString(text, textX, textY);
    }
}