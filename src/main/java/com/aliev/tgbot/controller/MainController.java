package com.aliev.tgbot.controller;

import com.aliev.tgbot.telegram.TgBot;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MainController {
    TgBot bot;

    @PostMapping("/callback")
    public BotApiMethod<?> updateListener(@RequestBody Update update) {
        return bot.onWebhookUpdateReceived(update);
    }

}
