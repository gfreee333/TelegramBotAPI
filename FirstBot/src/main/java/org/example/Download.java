package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Download {

public String downloadAndReturnFileName(String url) throws IOException, InterruptedException {
    // Указываем путь к исполняемому файлу yt-dlp.exe
    String exePath = "C:\\Users\\VaNo\\IdeaProjects\\FirstBot\\src\\main\\java\\org\\example\\yt-dlp.exe";

    // Указываем путь, куда будет сохранен файл
    String outputPath = "C:\\Users\\VaNo\\IdeaProjects\\FirstBot\\src"; // Измените на ваш желаемый путь

    // Формируем команду для скачивания аудио с указанием пути сохранения и формата mp3
    String command = exePath + " --prefer-ffmpeg --extract-audio --audio-format mp3 --postprocessor-args \"-acodec libmp3lame\" --output " + outputPath + "\\%(title)s.%(ext)s " + url;

    Process downloadProcess = Runtime.getRuntime().exec(command);

    // Ждем завершения процесса скачивания
    downloadProcess.waitFor();

    // Читаем вывод процесса с помощью BufferedReader с указанием кодировки UTF-8
    BufferedReader reader = new BufferedReader(new InputStreamReader(downloadProcess.getInputStream(), "Cp1251"));

    String line;
    String fileName = null;
    while ((line = reader.readLine()) != null) {
        if (line.startsWith("[download] Destination:")) {
            fileName = line.substring("[download] Destination:".length()).trim();
            break;
        }
    }
    reader.close();

    // Возвращаем только имя скачанного файла
    return fileName;
}
    public File convertToMp3(String sourceFilePath) {
        // Проверяем, что исходный файл существует
        File sourceFile = new File(sourceFilePath);
        if (!sourceFile.exists()) {
            System.out.println("Исходный файл не существует.");
            return null;
        }

        // Создаем новый путь с замененным расширением на .mp3
        String destinationFilePath = sourceFilePath.replaceAll("\\.\\w+$", ".mp3");

        // Создаем объект File для нового файла
        File destinationFile = new File(destinationFilePath);

        try {
            // Копируем содержимое исходного файла в новый файл с новым расширением
            Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Файл успешно скопирован и переименован в формат mp3.");
            return destinationFile; // Возвращаем объект нового файла
        } catch (IOException e) {
            System.out.println("Не удалось скопировать и переименовать файл: " + e.getMessage());
            return null;
        }
    }
}

