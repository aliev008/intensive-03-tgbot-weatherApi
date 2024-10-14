package com.aliev.tgbot.managers;

import com.aliev.tgbot.service.factories.KeyboardFactory;
import com.aliev.tgbot.telegram.TgBot;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

import static com.aliev.tgbot.data.QueryData.fc;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MainManager {
    KeyboardFactory keyboardFactory;

    public BotApiMethod<?> answerCommand(Message message, TgBot bot) {
        System.out.println("INSIDE MainManager answerCommand");
        return SendMessage.builder()
                .text(
                        """
                                Добро пожаловать в бот прогноза погоды! 👋
                                
                                ---
                                
                                - Это учебный бот в рамках интесива в ASTONE
                                - В качестве API используется WeatherBit
                                - Бэкенд часть написана на Java
                                
                                ---
                                Нажмите на кнопку "Узнать Прогноз" чтобы начать!
                                """
                )
                .chatId(message.getChatId())
                .replyMarkup(
                        keyboardFactory.getInlineKeyboard(
                                List.of("⛅ Узнать Прогноз ⛅"),
                                List.of(1),
                                List.of(fc.name())
                        )
                )
                .build();
    }

    public BotApiMethod<?> answerQuery(CallbackQuery query, String[] data, TgBot bot) {
        System.out.println("INSIDE MainManager answerQuery");
        return EditMessageText.builder()
                .text(
                        """
                                Добро пожаловать в бот прогноза погоды! 👋
                                
                                ---
                                
                                - Это учебный бот в рамках интесива в ASTONE
                                - В качестве API используется WeatherBit
                                - Бэкенд часть написана на Java
                                
                                ---
                                Нажмите на кнопку "Узнать Прогноз" чтобы начать!
                                """
                )
                .chatId(query.getMessage().getChatId())
                .messageId(query.getMessage().getMessageId())
                .replyMarkup(
                        keyboardFactory.getInlineKeyboard(
                                List.of("⛅ Узнать Прогноз ⛅"),
                                List.of(1),
                                List.of(fc.name())
                        )
                )
                .build();
    }
}