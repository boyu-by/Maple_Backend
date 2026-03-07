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
                "  \"query\": \"%s\",\n" +
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
            
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "空响应体";
                throw new IOException("API错误: " + response.code() + " - " + errorBody);
            }
            
            // 处理SSE流
            StringBuilder fullAnswer = new StringBuilder();
            try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(response.body().byteStream(), java.nio.charset.StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("data: ")) {
                        String jsonStr = line.substring(6); // 去掉"data: "前缀
                        if (!jsonStr.isEmpty()) {
                            try {
                                com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
                                java.util.Map<String, Object> dataMap = objectMapper.readValue(jsonStr, java.util.Map.class);
                                if (dataMap.containsKey("answer")) {
                                    String answerPart = dataMap.get("answer").toString();
                                    fullAnswer.append(answerPart);
                                }
                            } catch (Exception e) {
                                System.err.println("SSE JSON解析错误: " + e.getMessage());
                            }
                        }
                    }
                }
            }
            
            String result = fullAnswer.toString();
            System.out.println("完整回复: " + result);
            
            if (result.isEmpty()) {
                throw new IOException("API返回空回复");
            }
            
            return result;
        } catch (Exception e) {
            System.err.println("错误: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public Response getMindMapSuggestionStream(String topic) throws IOException {
        // 检查API密钥是否设置
        if (apiKey == null) {
            throw new IOException("DIFY_API_KEY 环境变量未设置，无法调用智能体");
        }
        
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String requestBody = String.format("{\n" +
                "  \"inputs\": {},\n" +
                "  \"query\": \"%s\",\n" +
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

        // 执行请求并返回原始响应
        Response response = client.newCall(request).execute();
        System.out.println("响应状态码: " + response.code());
        System.out.println("响应消息: " + response.message());
        
        return response;
    }
}