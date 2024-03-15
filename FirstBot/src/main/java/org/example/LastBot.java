package org.example;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class LastBot extends TelegramLongPollingBot {
    final String token = "6893606154:AAH0ot5wSPFc9BW0U7Yk1EGaKVW54kvdjEw";
    final String userName = "Botтян";
    String exePath = "C:\\Users\\VaNo\\IdeaProjects\\FirstBot\\src\\main\\java\\org\\example\\yt-dlp.exe";

    // todo EmojiParser.parseToUnicode( название стикера )
    String wave = EmojiParser.parseToUnicode(":wave:");
    private int counterLike = 0;
    private  int counterDizLike = 0;
    private int pickIndex = 0;
    private String music = "@Gfree_God_Test1234_bot";
    private boolean downloadingByURL = false;
    private boolean downloadingAudio = false;
    @Override
    public String getBotUsername() {
        return userName;
    }
    // todo получение токена бота.
    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            Message massageText = update.getMessage();
            String firstName = update.getMessage().getChat().getFirstName();
            String lastName = update.getMessage().getChat().getLastName();
            String text = massageText.getText();
            // String call_data = update.getCallbackQuery().getData(); // todo TEST
            long user_id = update.getMessage().getChat().getId();
            long chatId = massageText.getChatId();
            String answer = text;
            // todo блок отвечающий за меню команд BotCommand
            List<BotCommand> listCommands = new ArrayList<>();
            listCommands.add(new BotCommand("/start", "Запуск бота " + wave));
            listCommands.add(new BotCommand("/info", "Информация о проекте ℹ\uFE0F"));
            listCommands.add(new BotCommand("/help", "Обратная связь с пользователем \uD83C\uDD98"));
            listCommands.add(new BotCommand("/creator", "Информация о создателе \uD83D\uDEE0"));
            try {
                execute(new SetMyCommands(listCommands, new BotCommandScopeDefault(), null));
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }

            if (text.equals("/start")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("Бот успешно приступил к работе, чтобы вызвать диалоговое окно введите /markup ");
                log(firstName, lastName, String.valueOf(user_id), text, answer);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            if (text.equals("/markup")) {
                //todo ReplyKeyboardMarkup позволяет запросить у пользователя
                // его контакт или локацию. Это те самые два исключения из правила,
                // когда при нажатии кнопки будет отправлено не то, что написано на ней.
                // Их можно отправлять как по одной, так и в составе более сложной клавиатуры.

                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText(text);
                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                List<KeyboardRow> keyboardRowList = new ArrayList<>();
                KeyboardRow row = new KeyboardRow();
                KeyboardRow row2 = new KeyboardRow();
                row.add("Изображения");
                row.add("Музыка");
                row.add("Exit");
                keyboardRowList.add(row);
                row2.add("Местоположение");
                row2.add("Оценка проекта");
                keyboardRowList.add(row2);
                replyKeyboardMarkup.setKeyboard(keyboardRowList);
                replyKeyboardMarkup.setResizeKeyboard(true);
                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            if (text.equals("/info")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("1\uFE0F⃣Данный проект был реализован с целью получения новых навыков и закрепить уже полученный опыт " +
                        "программирования на конкретном примере, а именно: telegram bot. \uD83D\uDC4B\uD83D\uDC7D\n" + "\n" +
                        "2\uFE0F⃣Бот представляет собой облачное хранилище изображений которые пользователь использовал " +
                        "при работе с ботом, в дальнейшем на основе данной информации будет сделана аналитика о " +
                        "более востребованных изображениях и увеличение их количества в хранилище. ✅ \uD83D\uDC9C " + "\n" + "\n" +
                        "3\uFE0F⃣В планах, данный функционал не будет являться основным, " +
                        "он перейдет на второй план и на его место прейдет функционал, " +
                        "позволяющий хранить URL ссылки статей в базе, которые bot будет получать " +
                        "из телеграмм каналов. Спасибо, что выбираете нас \uD83D\uDC9C ");
                sendMessage.setChatId(chatId);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            if (text.equals("/help")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("В дальнейшем тут будет выведен весь возможный перечень команд бота, а пока что ознакомиться с ними можно нажава на /markup");
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            if (text.equals("/creator")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("В случае возникновение проблем ⚠\uFE0F и некоретной работы Botтян ❗\uFE0F по всем вопросам обращаться к создателю данного бота: https://t.me/chuhmancik ❓");
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            // todo можно реализовывать функции, которые будут вызываться с входными параметрами
            // todo а можно в тупую обработать все возможные варианты через if else

            if (text.equals("Изображения")) {
                SendMessage sendMessage = ActiveMessage(chatId); // todo переписать ActiveMessage как калькулятор
                // todo получение изображения
                // todo выводить 2 кнопки нравиться не нравиться, выводить информацию в отчет.
                //todo вот сюда запихну метод
                try {
                    execute(sendMessage);
                }
                catch (TelegramApiException e){
                    e.printStackTrace();
                }
            }
            if(text.equals("Музыка")){
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(chatId);
                sendPhoto.setPhoto(new InputFile("AgACAgIAAxkBAAIE12XaNCik2yCzEGqJN7FAQzzTDpkZAAKE1zEbvFTRSrIKw_NzFJC2AQADAgADeAADNAQ"));
                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> lists = new ArrayList<>();
                List<InlineKeyboardButton> list1 = new ArrayList<>();
                InlineKeyboardButton button1 = new InlineKeyboardButton();
                // todo текст на самой кнопки, тобишь текст который будет отображаться на кнопке
                button1.setText("Скачать Audio");
                // todo call_back это по сути, что получаем при нажатие на данную кнопку если я правильно понимаю
                button1.setCallbackData("Скачать Audio");
                list1.add(button1);
                lists.add(list1);
                inlineKeyboardMarkup.setKeyboard(lists);
                sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
                try {
                    execute(sendPhoto);
                }
                catch (TelegramApiException e){
                    e.printStackTrace();
                }
            }
            // todo попытка обработки кнопок под текстом
            // todo попытка получение gps данных при нажатие на кнопку.
            if (text.equals("Местоположение")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("Отправить местоположение");
                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> lists = new ArrayList<>();
                List<InlineKeyboardButton> buttons = new ArrayList<>();
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText("test");
                inlineKeyboardButton.setCallbackData("test");
                buttons.add(inlineKeyboardButton);
                lists.add(buttons);
                inlineKeyboardMarkup.setKeyboard(lists);
                sendMessage.setReplyMarkup(inlineKeyboardMarkup);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            // todo вот сюда засуну отображение текущего плейлиста по ссылке

            if (text.equals("Оценка проекта")) {
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(chatId);
                sendPhoto.setPhoto(new InputFile("AgACAgIAAxkBAAIClWXLQcyE8G3fDJYb14JO9n8rkSchAALx1TEb_1xZSiHteJSKhCioAQADAgADbQADNAQ"));
                sendPhoto.setCaption("Botян хочет узнать ваше мнение о своей работе \uD83D\uDC9C");
                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> lists = new ArrayList<>();
                List<InlineKeyboardButton> list1 = new ArrayList<>();
                InlineKeyboardButton button1 = new InlineKeyboardButton();
                // todo текст на самой кнопки, тобишь текст который будет отображаться на кнопке
                button1.setText("\uD83D\uDC4D");
                // todo call_back это по сути, что получаем при нажатие на данную кнопку если я правильно понимаю
                button1.setCallbackData("Лайк");
                InlineKeyboardButton button2 = new InlineKeyboardButton();
                button2.setText("\uD83D\uDC4E");
                button2.setCallbackData("Дизлайк");
                list1.add(button1);
                list1.add(button2);
                lists.add(list1);
                inlineKeyboardMarkup.setKeyboard(lists);
                sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
                log(firstName, lastName, String.valueOf(user_id), text, answer);
                try {
                    execute(sendPhoto);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            if (text.equals("Exit")) {
               System.exit(0);
            }

        }

        // todo вся обработка для нажимов на кнокпи вызванные при помощи InlineKeyboardMarkup
        //todo при случае когда я использую InlineKeyboardMarkup будет обработка проходить в этой части блоков
        else if (update.hasCallbackQuery()) {
         // todo проверка Call_back вместо text
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String call_data = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            if(call_data.equals("Скачать Audio")){ // TODO часть обработки скачивания аудио записи
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Введите URL \uD83D\uDCB9");
                sendMessage.setChatId(chatId);
                try {
                    execute(sendMessage);
                    downloadingAudio = true;
                }
                catch (TelegramApiException e){
                    e.printStackTrace();
                }
            }
            if(call_data.equals("Лайк")) {
                editKeyboardLike(callbackQuery);
                //todo понять как обновлять значение counter при нажатие
            }
            if(call_data.equals("Дизлайк")) {
                editKeyboardDizLike(callbackQuery);
                //todo понять как обновлять значение counter при нажатие
            }
            if (call_data.equals("test")) {
                // Отправка запроса пользователю на предоставление местоположения
                SendMessage request = new SendMessage();
                request.setChatId(callbackQuery.getMessage().getChatId());
                request.setText("Пожалуйста, отправьте свое местоположение");
                // Создаем кнопку для запроса местоположения
                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                keyboardMarkup.setOneTimeKeyboard(true);
                keyboardMarkup.setResizeKeyboard(true);
                KeyboardRow row = new KeyboardRow();
                KeyboardButton button = new KeyboardButton("Отправить местоположение");
                button.setRequestLocation(true); // Устанавливаем этой кнопке запрос местоположения
                row.add(button);
                List<KeyboardRow> keyboard = new ArrayList<>();
                keyboard.add(row);
                keyboardMarkup.setKeyboard(keyboard);
                keyboardMarkup.setOneTimeKeyboard(true); // todo клавиатура появиться единоразово
                request.setReplyMarkup(keyboardMarkup);
                try {
                    execute(request);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
           // todo блок кода, который будет отвечать за скачивание изображения по ссылке:
            if(call_data.equals("Скачать по URL")){
                downloadingByURL = true;
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("Введите ссылку изображения, которое вы хотите скачать: ");
                try {
                    execute(sendMessage);
                }
                catch (TelegramApiException e){
                    e.printStackTrace();
                }
            }
            if(call_data.equals("ALL")){  // todo можно к данному пункту добавить работу с sql и хранить данные там
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("В данный момент данная функция недоступа, простите");
                try {
                    execute(sendMessage);
                }
                catch (TelegramApiException e)
                {
                    e.printStackTrace();
                }
            }
        }
        // todo если пользователь отпровляет гео данные, бот их считывает
        if (update.hasMessage() && update.getMessage().hasLocation()) {
            long chatId = update.getMessage().getChatId();
            Location location = update.getMessage().getLocation();
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(latitude + " " + longitude);
            try {
                execute(sendMessage);
                // todo допилить функционал, чтобы он после единоразового вывода окна, вот тут вызывал /markup
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            // Теперь у вас есть координаты местоположения пользователя, которые вы можете использовать для нужных действий
        }
        // Обработка сообщения с местоположением


        //todo функция подсчета нажатия на кнопку
        // todo как работать с URL
//        InputStream stream = new URL(myUrl).openStream();
//        SendPhoto sendPhoto = new SendPhoto();
//        sendPhoto.setChatId(chatId);
//        sendPhoto.setPhoto(new InputFile(stream, myUrl));
        // TODO обработка URL + скачивания для начала изображения в file

        else if(downloadingByURL && update.hasMessage() && update.getMessage().hasText()) {
            String url = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String fileName = "C:\\Users\\VaNo\\Desktop\\Tg bot photo\\painterNumber_" + pickIndex + ".jpg";
            try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
                 FileOutputStream out = new FileOutputStream(fileName)) {
                byte[] data = new byte[1024];
                int count;
                while ((count = in.read(data, 0, 1024)) != -1) {
                    out.write(data, 0, count);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            java.io.File file = new File(fileName);
            InputFile inputFile = new InputFile(file);
            SendPhoto photo = new SendPhoto();
            photo.setChatId(chatId);
            photo.setPhoto(inputFile);
            photo.setCaption("Скаченное изображение по URL ✨✨✨");
            try {
                execute(photo);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            pickIndex++;
            downloadingByURL = false;
        }
        else if(downloadingAudio && update.hasMessage() && update.getMessage().hasText()) {
            Download download = new Download();
            String url = update.getMessage().getText();
            String fileName = null;
            String delete = null;
            long chatId = update.getMessage().getChatId();
            try {
                fileName = download.downloadAndReturnFileName(url);// Вызов метода для загрузки и конвертации аудио
                // todo вот тут нужно поменять формат с webm на mp3
                delete = fileName;
                //todo по рассположению файла поменять его формат на mp3
                File convertedFile = download.convertToMp3(fileName);
                if (convertedFile != null) {
                    SendAudio sendAudio = new SendAudio();
                    sendAudio.setChatId(chatId);
                    sendAudio.setAudio(new InputFile(convertedFile));
                    execute(sendAudio);
                    convertedFile.delete();
                } else {
                    System.out.println("Не удалось сконвертировать файл в формат mp3.");
                }
            } catch (IOException | InterruptedException | TelegramApiException e) {
                throw new RuntimeException(e);
            }
            File deleteFile = new File(delete);
            deleteFile.delete();
            downloadingByURL = false;
        }

        //todo обработка при получение изображения ботом
        else if(update.hasMessage() && update.getMessage().hasPhoto()){
        long chatId = update.getMessage().getChatId();
            List<PhotoSize> photos = update.getMessage().getPhoto();
            String photoId = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getFileId();
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chatId);
            sendPhoto.setPhoto(new InputFile(photoId)); // todo попробуем через URL
            sendPhoto.setCaption(photoId); // todo по сути получаем id изображения в самом телеграмме
            try {
                execute(sendPhoto);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // todo по сути является функцией отчета по работе с ботом, кто когда и какие действия совершал в боте.
    private void log(String firstName,String lastName, String user_id, String text, String answer){
        System.out.println("-------------------Информация о пользователе--------------------");
        DateFormat dataFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dataFormat.format(date));
        System.out.println("Message from: " + firstName + " " + lastName + " "
                + "( id: " + user_id + " ) " +  "\n" + "text: " + text + " ");
        System.out.println("Bot answer: " + answer);
    }
    // todo сделать так, чтобы весь отчет о работе ботта загружался в SQL
    // todo метод который возвращает sendMessage: будет возвращать значение text + Лист Листов содержащий кнопки
    // todo в данном случае реализую для URL ссылок на различные статьи

    private SendMessage ActiveMessage(long chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Работа с изображениями");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> lists = new ArrayList<>();
        //todo список кнопок для первого ряда
        List<InlineKeyboardButton> list1 = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        // todo текст на самой кнопки, тобишь текст который будет отображаться на кнопке
        button1.setText("Скачать по URL ✅");
        // todo call_back это по сути, что получаем при нажатие на данную кнопку если я правильно понимаю
        button1.setCallbackData("Скачать по URL");
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("ALL \uD83D\uDDBC");
        button2.setCallbackData("ALL");
        list1.add(button1);
        list1.add(button2);
        lists.add(list1);
        inlineKeyboardMarkup.setKeyboard(lists);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }
    // todo методы по сути обновляют клавиатуру к изоображению с увелечением счетчика на ней
    private void editKeyboardLike(CallbackQuery callbackQuery){
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(callbackQuery.getMessage().getChatId().toString());
        editMessageReplyMarkup.setMessageId(callbackQuery.getMessage().getMessageId());
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> lists = new ArrayList<>();
        List<InlineKeyboardButton> list1 = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("\uD83D\uDC4D" + countValueLike());
        // todo call_back это по сути, что получаем при нажатие на данную кнопку если я правильно понимаю
        button1.setCallbackData("Лайк");
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("\uD83D\uDC4E" + counterDizLike);
        button2.setCallbackData("Дизлайк");
        list1.add(button1);
        list1.add(button2);
        lists.add(list1);
        inlineKeyboardMarkup.setKeyboard(lists);
        editMessageReplyMarkup.setReplyMarkup(inlineKeyboardMarkup);
        try {
            execute(editMessageReplyMarkup);
        }
        catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
    private void editKeyboardDizLike(CallbackQuery callbackQuery){
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(callbackQuery.getMessage().getChatId().toString());
        editMessageReplyMarkup.setMessageId(callbackQuery.getMessage().getMessageId());
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> lists = new ArrayList<>();
        List<InlineKeyboardButton> list1 = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("\uD83D\uDC4D" + counterLike);
        // todo call_back это по сути, что получаем при нажатие на данную кнопку если я правильно понимаю
        button1.setCallbackData("Лайк");
        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText("\uD83D\uDC4E" + countValueDizLike());
        button2.setCallbackData("Дизлайк");
        list1.add(button1);
        list1.add(button2);
        lists.add(list1);
        inlineKeyboardMarkup.setKeyboard(lists);
        editMessageReplyMarkup.setReplyMarkup(inlineKeyboardMarkup);
        try {
            execute(editMessageReplyMarkup);
        }
        catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
    //todo некий счетчик нажатия на кнопку
    private int countValueLike(){
        counterLike++;
        return counterLike;
    }
    private int countValueDizLike(){
        counterDizLike++;
        return  counterDizLike;
    }
}
