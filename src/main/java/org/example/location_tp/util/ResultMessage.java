package org.example.location_tp.util;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.*;
// Пока пишу код пусть висит перед глазами
//public enum ResultMessage {
//    SILENT_SUCCESS(""),
//    SUCCESS(ChatColor.GREEN + "Успех!"),
//    NOTHING(ChatColor.BLUE + "Пусто!"),
//    NAME_OCCUPIED(ChatColor.YELLOW + "Указанное имя уже занято!"),
//    NO_NAME(ChatColor.YELLOW + "Отсутствует или указанно пустое имя!"),
//    INVALID_ARGUMENTS(ChatColor.RED + "Указан неверный набор аргументов!"),
//    FAIL(ChatColor.RED + "Не удалось совершить операцию!");
//
//    public final String resultMessage;
//
//    ResultMessage(String text) {
//        this.resultMessage = text;
//    }
//}

/*
    $success:
        color: "GREEN"
        ru: "Успех!"
        en: "Success!"
 */

// в местах применения временно вставлю "ru", не всё и сразу + голые геты ПОКА ЧТО
// для определения локации вероятно нужен свой сервис
public class ResultMessage {
    private static final List<String> localeFallbackQueue = List.of("ru", "en");
    public static HashMap<String, ResultMessage> registeredLocales = new HashMap<>();

    protected HashMap<String, String> messageLocales; // "en" -> "Fuck off!", "ru" -> "Пшелнах!"
    private ChatColor color;
    private String identifier; // "$success", "$invalid_arguments"

    private ResultMessage(ChatColor color, String identifier) {
        this.color = color;
        this.identifier = identifier;
        this.messageLocales = new HashMap<>();
    }

    private ResultMessage(String identifier) {
        this(null, identifier);
    }

    public static Optional<ResultMessage> of(String identifier) {
        return Optional.ofNullable(registeredLocales.get(identifier));
    }

    public static void loadFrom(YamlConfiguration file) {
        for (String identifier : file.getKeys(false)) {
            ConfigurationSection section = file.getConfigurationSection(identifier);
            var nextMessage = new ResultMessage(identifier);

            for (String key : section.getKeys(false)) {
                if ("color".equals(key)) {
                    try {
                        nextMessage.color = ChatColor.valueOf(
                                section.getString(key).toUpperCase(Locale.ROOT)
                        );
                    } catch (IllegalArgumentException e) {
                        nextMessage.color = ChatColor.RESET;
                    }
                } else {
                    nextMessage.messageLocales.put(key, section.getString(key));
                }
            }
            registeredLocales.put(identifier, nextMessage);
        }
    }

    public ChatColor getColor() {
        return this.color;
    }

    public String getLocale(String regionId) {
        if (messageLocales.isEmpty()) return this.identifier;

        String result = messageLocales.get(regionId);
        if (result != null) return result;

        for (var fallback : localeFallbackQueue) {
            result = messageLocales.get(fallback);
            if (result != null) return result;
        }

        // руками проверил реализации ещё через стримы и массивы,
        // это самое производительное для нашей цели высунуть хоть что-то
        return messageLocales.entrySet().iterator().next().getValue();
    }
}
