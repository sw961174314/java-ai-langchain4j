package com.sw.java.ai.langchain4j.assistant;

import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

// 如果配置了多个模型，需要指定使用特定模型，否则会报错
// chatModel:聊天模型
// chatMemory:聊天记忆体
@AiService(wiringMode = EXPLICIT, chatModel = "qwenChatModel", chatMemory = "chatMemory")
public interface MemoryChatAssistant {

    String chat(String message);
}