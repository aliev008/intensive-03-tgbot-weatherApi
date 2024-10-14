package com.aliev.tgbot.service.handlers;

import com.aliev.tgbot.data.QueryData;
import com.aliev.tgbot.managers.ForecastManager;
import com.aliev.tgbot.managers.MainManager;
import com.aliev.tgbot.telegram.TgBot;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.Arrays;


@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CallbackQueryHandler {
    MainManager mainManager;
    ForecastManager forecastManager;

    public BotApiMethod<?> answer(CallbackQuery query, TgBot bot) {
        String[] data = query.getData().split("_");
        System.out.println("INSIDE Callback query handler");
        System.out.println("String[] data: " + Arrays.toString(data));
        QueryData queryData;
        try {
            queryData = QueryData.valueOf(data[0]);
        } catch (Exception e) {
            log.error("Unsupported query data received: " + query.getData());
            return null;
        }

        switch (queryData) {
            case main -> {
                return mainManager.answerQuery(query, data, bot);
            }
            case fc -> {
                return forecastManager.answerQuery(query, data, bot);
            }
            case empty -> {
                return AnswerCallbackQuery.builder()
                        .callbackQueryId(query.getId())
                        .text("Эта кнопка ничего не делает")
                        .build();
            }
        }
        throw new UnsupportedOperationException();
    }
}