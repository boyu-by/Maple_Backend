package com.example.maple.service;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DifyService {
    private final OkHttpClient client = new OkHttpClient();
    private final String apiKey;
    private final String apiUrl = "https://api.dify.ai/v1/chat-messages";

    public DifyService() {
        // 从环境变量获取API密钥
        this.apiKey = System.getenv("DIFY_API_KEY");
        
        // 不再在构造函数中抛出异常，而是在实际调用API时处理
        if (apiKey == null) {
            System.out.println("警告: DIFY_API_KEY 环境变量未设置，智能体功能将不可用");
        } else {
            System.out.println("API密钥已设置");
        }
    }

    public String getMindMapSuggestion(String topic) throws IOException {
        // 检查API密钥是否设置
        if (apiKey == null) {
            throw new IOException("DIFY_API_KEY 环境变量未设置，无法调用智能体");
        }
        
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String requestBody = String.format("{\n" +
                "  \"inputs\": {},\n" +
                "  \"query\": \"我想创建一个关于'%s'的思维导图，请提供建议\",\n" +
                "  \"response_mode\": \"streaming\",\n" +
                "  \"incremental_output\": true,\n" +
                "  \"user\": \"user_123\"\n" +
                "}", topic);

        System.out.println("API URL: " + apiUrl);
        System.out.println("请求体: " + requestBody);

        Request request = new Request.Builder()
                .url(apiUrl)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .post(RequestBody.create(requestBody, JSON))
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("响应状态码: " + response.code());
            System.out.println("响应消息: " + response.message());
            
            String responseBody = response.body() != null ? response.body().string() : "空响应体";
            System.out.println("响应体: " + responseBody);
            
            if (!response.isSuccessful()) {
                throw new IOException("API错误: " + response.code() + " - " + responseBody);
            }
            return responseBody;
        } catch (Exception e) {
            System.err.println("错误: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}