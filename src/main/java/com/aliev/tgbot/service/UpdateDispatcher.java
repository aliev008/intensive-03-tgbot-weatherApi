package com.aliev.tgbot.service;

import com.aliev.tgbot.service.handlers.CallbackQueryHandler;
import com.aliev.tgbot.service.handlers.CommandHandler;
import com.aliev.tgbot.service.handlers.MessageHandler;
import com.aliev.tgbot.telegram.TgBot;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UpdateDispatcher {
    CommandHandler commandHandler;
    MessageHandler messageHandler;
    CallbackQueryHandler queryHandler;
    DatabaseService databaseService;

    public BotApiMethod<?> distribute(Update update, TgBot bot) {
        if (update.hasCallbackQuery()) {
            System.out.println("UPDATE HAS A CALLBACK QUERY");
            return queryHandler.answer(update.getCallbackQuery(), bot);
        }
        if (update.hasMessage()) {
            Message message = update.getMessage();

            if (!databaseService.isUserExists(message.getFrom().getId())) {
                User user = message.getFrom();
                Long userId = user.getId();
                String firstName = user.getFirstName();
                String lastName = user.getLastName();
                String username = user.getUserName();
                databaseService.saveOrUpdateUser(userId, firstName, lastName, username);
            }

            if (message.hasText()) {
                String text = message.getText();
                if (text.charAt(0) == '/') {
                    System.out.println("UPDATE HAS MESSAGE AND IT IS A COMMAND");
                    return commandHandler.answer(update.getMessage(), bot);
                }
                System.out.println("UPDATE HAS A MESSAGE AND IT IS A MESSAGE");
                return messageHandler.answer(update.getMessage(), bot);
            }
        }
        log.warn("Unsupported update type: {" + update + "}");
        return null;
    }
}