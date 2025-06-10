package com.sw.java.ai.langchain4j.assistant;

import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

// 如果配置了多个模型，需要指定使用特定模型，否则会报错
@AiService(wiringMode = EXPLICIT,chatModel = "qwenChatModel")
public interface Assistant {

    String chat(String userMessage);
}