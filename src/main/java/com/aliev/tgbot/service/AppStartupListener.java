package com.aliev.tgbot.service;

import com.aliev.tgbot.telegram.TelegramProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AppStartupListener {

    private final WebhookService webhookService;
    private final TelegramProperties telegramProperties;

    public AppStartupListener(WebhookService webhookService, TelegramProperties telegramProperties) {
        this.webhookService = webhookService;
        this.telegramProperties = telegramProperties;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        String webhookUrl = telegramProperties.getUrl(); // Укажите здесь ваш ngrok URL
        webhookService.setWebhook(webhookUrl);
    }
}
