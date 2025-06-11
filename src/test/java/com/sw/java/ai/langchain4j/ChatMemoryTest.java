package com.sw.java.ai.langchain4j;

import com.sw.java.ai.langchain4j.assistant.Assistant;
import com.sw.java.ai.langchain4j.assistant.MemoryChatAssistant;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.service.AiServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class ChatMemoryTest {

    @Autowired
    private Assistant assistant;

    @Autowired
    private MemoryChatAssistant memoryChatAssistant;

    @Autowired
    private QwenChatModel qwenChatModel;

    /**
     * 测试ai对话是否存在记忆(发现没有记忆)
     */
    @Test
    public void testChatMemory1() {
        String answer1 = assistant.chat("我是小明");
        System.out.println(answer1);

        String answer2 = assistant.chat("我是谁");
        System.out.println(answer2);
    }

    /**
     * 简单实现聊天记忆
     */
    @Test
    public void testChatMemory2() {
        // 第1轮对话
        // 封装对话
        UserMessage userMessage1 = UserMessage.userMessage("我是小明");
        // 发起请求
        ChatResponse chatResponse1 = qwenChatModel.chat(userMessage1);
        // 获取ai回答
        AiMessage aiMessage1 = chatResponse1.aiMessage();
        // 输出
        System.out.println(aiMessage1.text());

        // 第2轮对话
        // 封装对话
        UserMessage userMessage2 = UserMessage.userMessage("我是谁");
        // 发起请求(想要ai回答有记忆，需要带上上一次回答)
        ChatResponse chatResponse2 = qwenChatModel.chat(Arrays.asList(userMessage1, aiMessage1, userMessage2));
        // 获取ai回答
        AiMessage aiMessage2 = chatResponse2.aiMessage();
        // 输出
        System.out.println(aiMessage2.text());
    }

    /**
     * 使用chatMessage实现聊天记忆
     */
    @Test
    public void testChatMemory3() {
        // 创建chatMessage
        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        // 创建AiService
        Assistant assistant = AiServices
                .builder(Assistant.class)
                .chatLanguageModel(qwenChatModel)
                .chatMemory(chatMemory)
                .build();
        // 调用service的接口
        String answer1 = assistant.chat("我是小明");
        System.out.println(answer1);
        String answer2 = assistant.chat("我是谁");
        System.out.println(answer2);
    }

    /**
     * 使用AiService实现聊天记忆
     */
    @Test
    public void testChatMemory4() {
        String answer1 = memoryChatAssistant.chat("我是小明");
        System.out.println(answer1);

        String answer2 = memoryChatAssistant.chat("我是谁");
        System.out.println(answer2);
    }
}