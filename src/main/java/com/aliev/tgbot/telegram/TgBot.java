package com.aliev.tgbot.telegram;

import com.aliev.tgbot.service.UpdateDispatcher;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TgBot extends TelegramWebhookBot {

    TelegramProperties telegramProperties;
    UpdateDispatcher updateDispatcher;

    public TgBot(TelegramProperties telegramProperties, UpdateDispatcher updateDispatcher) {
        super(telegramProperties.getToken());
        this.telegramProperties = telegramProperties;
        this.updateDispatcher = updateDispatcher;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return updateDispatcher.distribute(update, this);
    }

    @Override
    public String getBotPath() {
        return "";
    }

    @Override
    public String getBotUsername() {
        return telegramProperties.getName();
    }


    @PostConstruct
    public void checkProperties() {
        // Логируем значения, чтобы убедиться, что они правильно подставлены
        System.out.println("Bot URL (from application.properties): " + telegramProperties.getUrl());
        System.out.println("Bot Token (from application.properties): " + telegramProperties.getToken());
        System.out.println("Bot Name (from application.properties): " + telegramProperties.getName());
    }
}
