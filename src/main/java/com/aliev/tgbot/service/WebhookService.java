package com.aliev.tgbot.service;

import com.aliev.tgbot.telegram.TgBot;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class WebhookService {

    private final TgBot bot;

    public WebhookService(TgBot bot) {
        this.bot = bot;
    }

    public void setWebhook(String webhookUrl) {
        SetWebhook setWebhook = SetWebhook.builder()
                .url(webhookUrl)
                .build();
        try {
            if (bot.getWebhookInfo().getUrl().isEmpty()) {
                bot.setWebhook(setWebhook);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
