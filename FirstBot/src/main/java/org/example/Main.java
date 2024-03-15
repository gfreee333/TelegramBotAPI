package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws TelegramApiException {
        // todo создаем сессию для бота.
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            // todo регестрируем нашего созданного бота в зарегестрированной сессии
            telegramBotsApi.registerBot(new LastBot());
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }
}
